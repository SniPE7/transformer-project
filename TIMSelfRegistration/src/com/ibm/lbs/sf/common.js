/*
 * MAP对象，实现MAP功能
 *
 * 接口：
 * size()     获取MAP元素个数
 * isEmpty()    判断MAP是否为空
 * clear()     删除MAP所有元素
 * put(key, value)   向MAP中增加元素（key, value) 
 * remove(key)    删除指定KEY的元素，成功返回True，失败返回False
 * get(key)    获取指定KEY的元素值VALUE，失败返回NULL
 * element(index)   获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
 * containsKey(key)  判断MAP中是否含有指定KEY的元素
 * containsValue(value) 判断MAP中是否含有指定VALUE的元素
 * values()    获取MAP中所有VALUE的数组（ARRAY）
 * keys()     获取MAP中所有KEY的数组（ARRAY）
 *
 * 例子：
 * var map = new Map();
 *
 * map.put("key", "value");
 * var val = map.get("key")
 * ……
 *
 */
function Map() {
  this.elements = new Array();

  //获取MAP元素个数
  this.size = function() {
    return this.elements.length;
  }

  //判断MAP是否为空
  this.isEmpty = function() {
    return (this.elements.length < 1);
  }

  //删除MAP所有元素
  this.clear = function() {
    this.elements = new Array();
  }

  //向MAP中增加元素（key, value) 
  this.put = function(_key, _value) {
    this.elements.push( {
      key : _key,
      value : _value
    });
  }

  //删除指定KEY的元素，成功返回True，失败返回False
  this.remove = function(_key) {
    var bln = false;
    try {
      for (i = 0; i < this.elements.length; i++) {
        if (this.elements[i].key == _key) {
          this.elements.splice(i, 1);
          return true;
        }
      }
    } catch (e) {
      bln = false;
    }
    return bln;
  }

  //获取指定KEY的元素值VALUE，失败返回NULL
  this.get = function(_key) {
    try {
      for (i = 0; i < this.elements.length; i++) {
        if (this.elements[i].key == _key) {
          return this.elements[i].value;
        }
      }
    } catch (e) {
      return null;
    }
  }

  //获取指定索引的元素（使用element.key，element.value获取KEY和VALUE），失败返回NULL
  this.element = function(_index) {
    if (_index < 0 || _index >= this.elements.length) {
      return null;
    }
    return this.elements[_index];
  }

  //判断MAP中是否含有指定KEY的元素
  this.containsKey = function(_key) {
    var bln = false;
    try {
      for (i = 0; i < this.elements.length; i++) {
        if (this.elements[i].key == _key) {
          bln = true;
        }
      }
    } catch (e) {
      bln = false;
    }
    return bln;
  }

  //判断MAP中是否含有指定VALUE的元素
  this.containsValue = function(_value) {
    var bln = false;
    try {
      for (i = 0; i < this.elements.length; i++) {
        if (this.elements[i].value == _value) {
          bln = true;
        }
      }
    } catch (e) {
      bln = false;
    }
    return bln;
  }

  //获取MAP中所有VALUE的数组（ARRAY）
  this.values = function() {
    var arr = new Array();
    for (i = 0; i < this.elements.length; i++) {
      arr.push(this.elements[i].value);
    }
    return arr;
  }

  //获取MAP中所有KEY的数组（ARRAY）
  this.keys = function() {
    var arr = new Array();
    for (i = 0; i < this.elements.length; i++) {
      arr.push(this.elements[i].key);
    }
    return arr;
  }
}

 var ArrayList = function () {
	    var args = ArrayList.arguments;
	    var initialCapacity = 10;
	    
	    if (args != null && args.length > 0) {
	        initialCapacity = args[0];
	    }
	    
	    var elementData = new Array(initialCapacity);
	    var elementCount = 0;
	    
	    this.size = function () {
	        return elementCount;
	    };
	    
	    this.add = function (element) {
	        //alert("add");
	        ensureCapacity(elementCount + 1);
	        elementData[elementCount++] = element;
	        return true;
	    };
	    
	    this.addElementAt = function (index, element) {
	        //alert("addElementAt");
	        if (index > elementCount || index < 0) {
	            alert("IndexOutOfBoundsException, Index: " + index + ", Size: " + elementCount);
	            return;
	            //throw (new Error(-1,"IndexOutOfBoundsException, Index: "+index+", Size: " + elementCount));
	        }
	        ensureCapacity(elementCount + 1);
	        for (var i = elementCount + 1; i > index; i--) {
	            elementData[i] = elementData[i - 1];
	        }
	        elementData[index] = element;
	        elementCount++;
	    };
	    
	    this.setElementAt = function (index, element) {
	        //alert("setElementAt");
	        if (index > elementCount || index < 0) {
	            alert("IndexOutOfBoundsException, Index: " + index + ", Size: " + elementCount);
	            return;
	            //throw (new Error(-1,"IndexOutOfBoundsException, Index: "+index+", Size: " + elementCount));
	        }
	        elementData[index] = element;
	    };
	    
	    this.toString = function () {
	        //alert("toString()");
	        var str = "{";
	        for (var i = 0; i < elementCount; i++) {
	            if (i > 0) {
	                str += ",";
	            }
	            str += elementData[i];
	        }
	        str += "}";
	        return str;
	    };
	    
	    this.get = function (index) {
	        //alert("elementAt");
	        if (index >= elementCount) {
	            alert("ArrayIndexOutOfBoundsException, " + index + " >= " + elementCount);
	            return;
	            //throw ( new Error( -1,"ArrayIndexOutOfBoundsException, " + index + " >= " + elementCount ) );
	        }
	        return elementData[index];
	    };
	    
	    this.remove = function (index) {
	        if (index >= elementCount) {
	            alert("ArrayIndexOutOfBoundsException, " + index + " >= " + elementCount);
	            //return;
	            throw (new Error(-1, "ArrayIndexOutOfBoundsException, " + index + " >= " + elementCount));
	        }
	        var oldData = elementData[index];
	        for (var i = index; i < elementCount - 1; i++) {
	            elementData[i] = elementData[i + 1];
	        }
	        elementData[elementCount - 1] = null;
	        elementCount--;
	        return oldData;
	    };
	    
	    this.isEmpty = function () {
	        return elementCount == 0;
	    };
	    
	    this.indexOf = function (elem) {
	        //alert("indexOf");
	        for (var i = 0; i < elementCount; i++) {
	            if (elementData[i] == elem) {
	                return i;
	            }
	        }
	        return -1;
	    };
	    
	    this.lastIndexOf = function (elem) {
	        for (var i = elementCount - 1; i >= 0; i--) {
	            if (elementData[i] == elem) {
	                return i;
	            }
	        }
	        return -1;
	    };
	    
	    this.contains = function (elem) {
	        return this.indexOf(elem) >= 0;
	    };
	    
	    function ensureCapacity(minCapacity) {
	        var oldCapacity = elementData.length;
	        if (minCapacity > oldCapacity) {
	            var oldData = elementData;
	            var newCapacity = parseInt((oldCapacity * 3) / 2 + 1);
	            if (newCapacity < minCapacity) {
	                newCapacity = minCapacity;
	            }
	            elementData = new Array(newCapacity);
	            for (var i = 0; i < oldCapacity; i++) {
	                elementData[i] = oldData[i];
	            }
	        }
	    }
	}

 
 function __initNextLevelSelectBox(parentId, level) {
	  // Set value into parameter
	  if (level > 0) {
	     v = document.getElementById(_box_id_prefix + level).options[document.getElementById(_box_id_prefix + level).selectedIndex].value;
	     if (v != null && v != "") {
	    	 node = idAndNodeMap.get(v);
	    	 if (node != null) {
	    		v = node.dn;
	    	 }
	     }
	     document.getElementById(_box_final_value_id).value = v;
	  }

	  boxId = _box_id_prefix + (level + 1);
	  var box = document.getElementById(boxId);
	  
	  if (level > 0 ) {
	    for (var i = level + 1; i < _box_max_deepth + 1; i++) {
	        b = document.getElementById(_box_id_prefix + i);
	        b.style.display = "none";
	        b.options.length = 1;
	    }
	  }
	  box.style.display = "";
	  
	  nodes = null;
	  if (parentId == null) {
	     nodes = topNodes;
	  } else {
	    nodes = nodeAndChildrenMap.get(parentId);
	    if (nodes == null || nodes.size() == 0) {
	       box.style.display = "none";
	    } else {
	      document.getElementById(_box_final_value_id).value = "";
	    }
	  }
	  box.options.length = 0;
	  box.options[0] = new Option(_default_select_hint, "");
	  for (var i = 0; nodes != null && i < nodes.size(); i++) {
	      var oOption =new Option(nodes.get(i).name, nodes.get(i).id);
	      box.options[box.length]=oOption;
	  }
	  
	}
