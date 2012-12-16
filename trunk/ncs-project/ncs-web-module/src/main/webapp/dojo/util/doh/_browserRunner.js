if(window["dojo"]){
	dojo.provide("doh._browserRunner");
}

// FIXME: need to add prompting for monkey-do testing
// FIXME: need to implement progress bar
// FIXME: need to implement errors in progress bar

(function(){
	if(window.parent == window){
		// we're the top-dog window.

		// borrowed from Dojo, etc.
		var byId = function(id){
			return document.getElementById(id);
		};

		var _addOnEvt = function(	type,		// string
									refOrName,	// function or string
									scope){		// object, defaults is window

			if(!scope){ scope = window; }

			var funcRef = refOrName;
			if(typeof refOrName == "string"){
				funcRef = scope[refOrName];
			}
			var enclosedFunc = function(){ return funcRef.apply(scope, arguments); };

			if((window["dojo"])&&(type == "load")){
				dojo.addOnLoad(enclosedFunc);
			}else{
				if(window["attachEvent"]){
					window.attachEvent("on"+type, enclosedFunc);
				}else if(window["addEventListener"]){
					window.addEventListener(type, enclosedFunc, false);
				}else if(document["addEventListener"]){
					document.addEventListener(type, enclosedFunc, false);
				}
			}
		};

		//
		// Over-ride or implement base runner.js-provided methods
		//
		var escapeXml = function(str){
			//summary:
			//		Adds escape sequences for special characters in XML: &<>"'
			//		Optionally skips escapes for single quotes
			return str.replace(/&/gm, "&amp;").replace(/</gm, "&lt;").replace(/>/gm, "&gt;").replace(/"/gm, "&quot;"); // string
		};

		var _logBacklog = [], _loggedMsgLen = 0;
		var sendToLogPane = function(args, skip){
			var msg = "";
			for(var x=0; x<args.length; x++){
				msg += " "+args[x];
			}

			msg = escapeXml(msg);

			// workarounds for IE. Wheeee!!!
			msg = msg.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
				.replace(" ", "&nbsp;")
				.replace("\n", "<br>&nbsp;");
			if(!byId("logBody")){
				_logBacklog.push(msg);
				return;
			}else if(_logBacklog.length && !skip){
				var tm;
				while((tm=_logBacklog.shift())){
					sendToLogPane(tm, true);
				}
			}
			var logBody=byId("logBody");
			var tn = document.createElement("div");
			tn.innerHTML = msg;
			//tn.id="logmsg_"+logBody.childNodes.length;
			logBody.appendChild(tn);
			_loggedMsgLen++;
		}

		var findTarget = function(n){
			while(n && !n.getAttribute('_target')){
				n=n.parentNode;
				if(!n.getAttribute){
					n=null;
				}
			}
			return n;
		}
		
		doh._jumpToLog = function(e){
			//console.log(e);
			
			var node = findTarget(e?e.target:window.event.srcElement);
			if(!node){
				return;
			}
			var _t = Number(node.getAttribute('_target'));
			var lb = byId("logBody");
			if(_t>=lb.childNodes.length){
				return;
			}
			var t = lb.childNodes[_t];
			t.scrollIntoView();
			if(window.dojo){
				//t.parentNode.parentNode is <div class="tabBody">, only it has a explicitly set background-color,
				//all children of it are transparent
				var bgColor = dojo.style(t.parentNode.parentNode,'backgroundColor');
				//node.parentNode is the tr which has background-color set explicitly
				var hicolor = dojo.style(node.parentNode,'backgroundColor');
				var unhilight = dojo.animateProperty({
					node: t,
					duration: 500,
					properties:
					{
						backgroundColor: { start:hicolor, end: bgColor }
					},
					onEnd: function(){
						t.style.backgroundColor="";
					}
				});
				var hilight = dojo.animateProperty({
					node: t,
					duration: 500,
					properties:
					{
						backgroundColor: { start:bgColor, end: hicolor }
					},
					onEnd: function(){
						unhilight.play();
					}
				});
				hilight.play();
			}
		};

		doh._jumpToSuite = function(e){
			var node = findTarget(e ? e.target : window.event.srcElement);
			if(!node){
				return;
			}
			var _g = node.getAttribute('_target');
			var gn = getGroupNode(_g);
			if(!gn){
				return;
			}
			gn.scrollIntoView();
		};

		doh._init = (function(oi){
			return function(){
				var lb = byId("logBody");
				if(lb){
					// clear the console before each run
					while(lb.firstChild){
						lb.removeChild(lb.firstChild);
					}
					_loggedMsgLen = 0;
				}
				this._totalTime = 0;
				this._suiteCount = 0;
				oi.apply(doh, arguments);
			}
		})(doh._init);

		doh._setupGroupForRun = (function(os){
			//overload _setupGroupForRun to record which log line to jump to when a suite is clicked
			return function(groupName){
				var tg = doh._groups[groupName];
				doh._curTestCount = tg.length;
				doh._curGroupCount = 1;
				var gn = getGroupNode(groupName);
				if(gn){
					//two lines will be added, scroll the second line into view
					gn.getElementsByTagName("td")[2].setAttribute('_target',_loggedMsgLen+1);
				}
				os.apply(doh,arguments);
			}
		})(doh._setupGroupForRun);
		
		doh._report = (function(or){
			//overload _report to insert a tfoot
			return function(){
				var tb = byId("testList");
				if(tb){
					var tfoots=tb.getElementsByTagName('tfoot');
					if(tfoots.length){
						tb.removeChild(tfoots[0]);
					}
					var foot = tb.createTFoot();
					var row = foot.insertRow(-1);
					row.className = 'inProgress';
					var cell=row.insertCell(-1);
					cell.colSpan=2;
					cell.innerHTML="Result";
					cell = row.insertCell(-1);
					cell.innerHTML=this._testCount+" tests in "+this._groupCount+" groups /<span class='failure'>"+this._errorCount+"</span> errors, <span class='failure'>"+this._failureCount+"</span> failures";
					cell.setAttribute('_target',_loggedMsgLen+1);
					row.insertCell(-1).innerHTML=doh._totalTime+"ms";
				}
				or.apply(doh,arguments);
			}
		})(doh._report);
		
		if(this["opera"] && opera.postError){
			doh.debug = function(){
				var msg = "";
				for(var x=0; x<arguments.length; x++){
					msg += " "+arguments[x];
				}
				sendToLogPane([msg]);
				opera.postError("DEBUG:"+msg);
			}
		}else if(window["console"]){
			if(console.debug){
				doh.debug = function(){
					sendToLogPane.call(window, arguments);
					console.debug.apply(console, arguments);
				};
			}else if(console.info){
				doh.debug = function(){
					sendToLogPane.call(window, arguments);
					console.info.apply(console, arguments);
				};
			}else{
				doh.debug = function(){
					var msg = "";
					for(var x=0; x<arguments.length; x++){
						msg += " "+arguments[x];
					}
					sendToLogPane([msg]);
					console.log("DEBUG:"+msg);
				};
			}
		}else{
			doh.debug = function(){
				sendToLogPane.call(window, arguments);
			}
		}

		var loaded = false;
		var groupTemplate = null;
		var testTemplate = null;

		var groupNodes = {};

		var _groupTogglers = {};

		var _getGroupToggler = function(group, toggle){
			if(_groupTogglers[group]){ return _groupTogglers[group]; }
			var rolledUp = true;
			return (_groupTogglers[group] = function(evt, forceOpen){
				var nodes = groupNodes[group].__items;
				var x;
				if(rolledUp||forceOpen){
					rolledUp = false;
					for(x=0; x<nodes.length; x++){
						nodes[x].style.display = "";
					}
					toggle.innerHTML = "&#9660;";
				}else{
					rolledUp = true;
					for(x=0; x<nodes.length; x++){
						nodes[x].style.display = "none";
					}
					toggle.innerHTML = "&#9658;";
				}
			});
		};

		var addGroupToList = function(group){
			if(!byId("testList")){ return; }
			var tb = byId("testList").tBodies[0];
			var tg = groupTemplate.cloneNode(true);
			var tds = tg.getElementsByTagName("td");
			var toggle = tds[0];
			toggle.onclick = _getGroupToggler(group, toggle);
			var cb = tds[1].getElementsByTagName("input")[0];
			cb.group = group;
			cb.onclick = function(evt){
				doh._groups[group].skip = (!this.checked);
			}
			tds[2].innerHTML = "<div class='testGroupName'>"+group+"</div><div style='width:0;'>&nbsp;</div>";
			tds[3].innerHTML = "";

			tb.appendChild(tg);
			return tg;
		}

		var addFixtureToList = function(group, fixture){
			if(!testTemplate){ return; }
			var cgn = groupNodes[group];
			if(!cgn["__items"]){ cgn.__items = []; }
			var tn = testTemplate.cloneNode(true);
			var tds = tn.getElementsByTagName("td");

			tds[2].innerHTML = fixture.name;
			tds[3].innerHTML = "";

			var nn = (cgn.__lastFixture||cgn.__groupNode).nextSibling;
			if(nn){
				nn.parentNode.insertBefore(tn, nn);
			}else{
				cgn.__groupNode.parentNode.appendChild(tn);
			}
			// FIXME: need to make group display toggleable!!
			tn.style.display = "none";
			cgn.__items.push(tn);
			return (cgn.__lastFixture = tn);
		}

		var getFixtureNode = function(group, fixture){
			if(groupNodes[group]){
				return groupNodes[group][fixture.name];
			}
			return null;
		}

		var getGroupNode = function(group){
			if(groupNodes[group]){
				return groupNodes[group].__groupNode;
			}
			return null;
		}

		var updateBacklog = [];
		doh._updateTestList = function(group, fixture, unwindingBacklog){
			if(!loaded){
				if(group && fixture){
					updateBacklog.push([group, fixture]);
				}
				return;
			}else if(updateBacklog.length && !unwindingBacklog){
				var tr;
				while((tr=updateBacklog.shift())){
					doh._updateTestList(tr[0], tr[1], true);
				}
			}
			if(group && fixture){
				if(!groupNodes[group]){
					groupNodes[group] = {
						"__groupNode": addGroupToList(group)
					};
				}
				if(!groupNodes[group][fixture.name]){
					groupNodes[group][fixture.name] = addFixtureToList(group, fixture)
				}
			}
		}

		doh._testRegistered = doh._updateTestList;

		doh._groupStarted = function(group){
			if(this._suiteCount == 0){
				this._runedSuite = 0;
				this._currentGlobalProgressBarWidth = 0;
				this._suiteCount = this._testCount;
			}
			// console.debug("_groupStarted", group);
			if(doh._inGroup != group){
				doh._groupTotalTime = 0;
				doh._runed = 0;
				doh._inGroup = group;
				this._runedSuite++;
			}
			var gn = getGroupNode(group);
			if(gn){
				gn.className = "inProgress";
			}
		}

		doh._groupFinished = function(group, success){
			// console.debug("_groupFinished", group);
			var gn = getGroupNode(group);
			if(gn && doh._inGroup == group){
				doh._totalTime += doh._groupTotalTime;
				gn.getElementsByTagName("td")[3].innerHTML = doh._groupTotalTime+"ms";
				gn.getElementsByTagName("td")[2].lastChild.className = "";
				doh._inGroup = null;
				//doh._runedSuite++;
				var failure = doh._updateGlobalProgressBar(this._runedSuite/this._groupCount,success,group);
				gn.className = failure ? "failure" : "success";
				//doh._runedSuite--;
				doh._currentGlobalProgressBarWidth = parseInt(this._runedSuite/this._groupCount*10000)/100;
				//byId("progressOuter").style.width = parseInt(this._runedSuite/this._suiteCount*100)+"%";
			}
			if(doh._inGroup == group){
				this.debug("Total time for GROUP \"",group,"\" is ",doh._groupTotalTime,"ms");
			}
		}

		doh._testStarted = function(group, fixture){
			// console.debug("_testStarted", group, fixture.name);
			var fn = getFixtureNode(group, fixture);
			if(fn){
				fn.className = "inProgress";
			}
		}

		var _nameTimes = {};
		var _playSound = function(name){
			if(byId("hiddenAudio") && byId("audio") && byId("audio").checked){
				// console.debug("playing:", name);
				var nt = _nameTimes[name];
				// only play sounds once every second or so
				if((!nt)||(((new Date)-nt) > 700)){
					_nameTimes[name] = new Date();
					var tc = document.createElement("span");
					byId("hiddenAudio").appendChild(tc);
					tc.innerHTML = '<embed src="_sounds/'+name+'.wav" autostart="true" loop="false" hidden="true" width="1" height="1"></embed>';
				}
			}
		}

		doh._updateGlobalProgressBar = function(p,success,group){
			var outerContainer=byId("progressOuter");
					
			var gdiv=outerContainer.childNodes[doh._runedSuite-1];
			if(!gdiv){
				gdiv=document.createElement('div');
				outerContainer.appendChild(gdiv);
				gdiv.className='success';
				gdiv.setAttribute('_target',group);
			}
			if(!success && !gdiv._failure){
				gdiv._failure=true;
				gdiv.className='failure';
				if(group){
					gdiv.setAttribute('title','failed group '+group);
				}
			}
			var tp=parseInt(p*10000)/100;
			gdiv.style.width = (tp-doh._currentGlobalProgressBarWidth)+"%";
			return gdiv._failure;
		}
		doh._testFinished = function(group, fixture, success){
			var fn = getFixtureNode(group, fixture);
			var elapsed = fixture.endTime-fixture.startTime;
			if(fn){
				fn.getElementsByTagName("td")[3].innerHTML = elapsed+"ms";
				fn.className = (success) ? "success" : "failure";
				fn.getElementsByTagName("td")[2].setAttribute('_target', _loggedMsgLen);
				if(!success){
					_playSound("doh");
					var gn = getGroupNode(group);
					if(gn){
						gn.className = "failure";
						_getGroupToggler(group)(null, true);
					}
				}
			}
			if(doh._inGroup == group){
				var gn = getGroupNode(group);
				doh._runed++;
				if(gn && doh._curTestCount){
					var p = doh._runed/doh._curTestCount;
					var groupfail = this._updateGlobalProgressBar((doh._runedSuite+p-1)/doh._groupCount,success,group);
					
					var pbar = gn.getElementsByTagName("td")[2].lastChild;
					pbar.className = groupfail?"failure":"success";
					pbar.style.width = parseInt(p*100)+"%";
					gn.getElementsByTagName("td")[3].innerHTML = parseInt(p*10000)/100+"%";
				}
			}
			this._groupTotalTime += elapsed;
			this.debug((success ? "PASSED" : "FAILED"), "test:", fixture.name, elapsed, 'ms');
		}

		// FIXME: move implementation to _browserRunner?
		doh.registerUrl = function(	/*String*/ group, 
										/*String*/ url, 
										/*Integer*/ timeout){
			var tg = new String(group);
			this.register(group, {
				name: url,
				setUp: function(){
					doh.currentGroupName = tg;
					doh.currentGroup = this;
					doh.currentUrl = url;
					this.d = new doh.Deferred();
					doh.currentTestDeferred = this.d;
					doh.showTestPage();
					byId("testBody").src = url;
				},
				timeout: timeout||10000, // 10s
				// timeout: timeout||1000, // 10s
				runTest: function(){
					// FIXME: implement calling into the url's groups here!!
					return this.d;
				},
				tearDown: function(){
					doh.currentGroupName = null;
					doh.currentGroup = null;
					doh.currentTestDeferred = null;
					doh.currentUrl = null;
					// this.d.errback(false);
					// byId("testBody").src = "about:blank";
					doh.showLogPage();
				}
			});
		}

		// 
		// Utility code for runner.html
		//
		// var isSafari = navigator.appVersion.indexOf("Safari") >= 0;
		var tabzidx = 1;
		var _showTab = function(toShow, toHide){
			// FIXME: I don't like hiding things this way.
			byId(toHide).style.display = "none";
			with(byId(toShow).style){
				display = "";
				zIndex = ++tabzidx;
			}
		}

		doh.showTestPage = function(){
			_showTab("testBody", "logBody");
		}

		doh.showLogPage = function(){
			_showTab("logBody", "testBody");
		}

		var runAll = true;
		doh.toggleRunAll = function(){
			// would be easier w/ query...sigh
			runAll = !runAll;
			if(!byId("testList")){ return; }
			var tb = byId("testList").tBodies[0];
			var inputs = tb.getElementsByTagName("input");
			var x=0; var tn;
			while((tn=inputs[x++])){
				tn.checked = runAll;
				doh._groups[tn.group].skip = (!runAll);
			}
		}

		var listHeightTimer = null;
		var setListHeight = function(){
			if(listHeightTimer){
				clearTimeout(listHeightTimer);
			}
			var tl = byId("testList");
			if(!tl){ return; }
			listHeightTimer = setTimeout(function(){
				tl.style.display = "none";
				tl.style.display = "";

			}, 10);
		}

		_addOnEvt("resize", setListHeight);
		_addOnEvt("load", setListHeight);
		_addOnEvt("load", function(){
			if(loaded){ return; }
			loaded = true;
			groupTemplate = byId("groupTemplate");
			if(!groupTemplate){ 
				// make sure we've got an ammenable DOM structure
				return;
			}
			groupTemplate.parentNode.removeChild(groupTemplate);
			groupTemplate.style.display = "";
			testTemplate = byId("testTemplate");
			testTemplate.parentNode.removeChild(testTemplate);
			testTemplate.style.display = "";
			doh._updateTestList();
		});

		_addOnEvt("load", 
			function(){
				// let robot code run if it gets to this first
				var __onEnd = doh._onEnd;
				doh._onEnd = function(){
					__onEnd.apply(doh, arguments);
					if(doh._failureCount == 0){
						doh.debug("WOOHOO!!");
						_playSound("woohoo");
					}else{
						console.debug("doh._failureCount:", doh._failureCount);
					}
					if(byId("play")){
						toggleRunning();
					}
				}
				if(!byId("play")){ 
					// make sure we've got an amenable DOM structure
					return;
				}
				var isRunning = false;
				var toggleRunning = function(){
					// ugg, this would be so much better w/ dojo.query()
					if(isRunning){
						byId("play").style.display = byId("pausedMsg").style.display = "";
						byId("playingMsg").style.display = byId("pause").style.display = "none";
						isRunning = false;
					}else{
						byId("play").style.display = byId("pausedMsg").style.display = "none";
						byId("playingMsg").style.display = byId("pause").style.display = "";
						isRunning = true;
					}
				}
				doh.run = (function(oldRun){
					return function(){
						if(!doh._currentGroup){
							toggleRunning();
						}
						return oldRun.apply(doh, arguments);
					}
				})(doh.run);
				var btns = byId("toggleButtons").getElementsByTagName("span");
				var node; var idx=0;
				while((node=btns[idx++])){
					node.onclick = toggleRunning;
				}
			}
		);
	}else{
		// we're in an iframe environment. Time to mix it up a bit.

		_doh = window.parent.doh;
		var _thisGroup = _doh.currentGroupName;
		var _thisUrl = _doh.currentUrl;
		if(_thisGroup){
			doh._testRegistered = function(group, tObj){
				_doh._updateTestList(_thisGroup, tObj);
			}
			doh._onEnd = function(){
				_doh._errorCount += doh._errorCount;
				_doh._failureCount += doh._failureCount;
				_doh._testCount += doh._testCount;
				// should we be really adding raw group counts?
				//_doh._groupCount += doh._groupCount;
				_doh.currentTestDeferred.callback(true);
			}
			var otr = doh._getTestObj;
			doh._getTestObj = function(){
				var tObj = otr.apply(doh, arguments);
				tObj.name = _thisUrl+"::"+arguments[0]+"::"+tObj.name;
				return tObj;
			}
			doh.debug = doh.hitch(_doh, "debug");
			doh.registerUrl = doh.hitch(_doh, "registerUrl");
			doh._testStarted = function(group, fixture){
				_doh._testStarted(_thisGroup, fixture);
			}
			doh._testFinished = function(g, f, s){
				_doh._testFinished(_thisGroup, f, s);
			}
			doh._groupStarted = function(g){
				if(!this._setParent){
					_doh._curTestCount = this._testCount;
					_doh._curGroupCount = this._groupCount;
					this._setParent = true;
				}
			}
			doh._report = function(){};
		}
	}

})();