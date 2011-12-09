package org.jdiameter.server.impl;

import org.jdiameter.api.*;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.controller.IPeer;
import org.jdiameter.common.api.concurrent.IConcurrentFactory;
import org.jdiameter.server.api.INetwork;
import org.jdiameter.server.api.IRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jdiameter.server.impl.helpers.Parameters.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RouterImpl extends org.jdiameter.client.impl.router.RouterImpl
		implements IRouter {

	private static final Logger logger = LoggerFactory
			.getLogger(RouterImpl.class);

	protected INetwork net;
	private ConcurrentHashMap<String, Realm> network;

	public RouterImpl(IConcurrentFactory concurrentFactory,
			Configuration config, MetaData metaData) {
		super(concurrentFactory, config, metaData);
	}

	protected void init() {
		network = new ConcurrentHashMap<String, Realm>();
	}

	protected void loadConfiguration(Configuration config) {
		Configuration[] rt = config.getChildren(RealmTable.ordinal());
		if (rt != null) {
			for (Configuration items : rt) {
				if (items != null) {
					Configuration[] m = items.getChildren(RealmEntry.ordinal());
					for (Configuration c : m) {
						try {
							String name = c.getStringValue(RealmName.ordinal(),
									"");
							ApplicationId appId = null;
							{
								Configuration[] apps = c
										.getChildren(ApplicationId.ordinal());
								if (apps != null) {
									for (Configuration a : apps) {
										if (a != null) {
											long vnd = a.getLongValue(VendorId
													.ordinal(), 0);
											long auth = a.getLongValue(
													AuthApplId.ordinal(), 0);
											long acc = a.getLongValue(
													AcctApplId.ordinal(), 0);
											if (auth != 0) {
												appId = org.jdiameter.api.ApplicationId
														.createByAuthAppId(vnd,
																auth);
											} else {
												appId = org.jdiameter.api.ApplicationId
														.createByAccAppId(vnd,
																acc);
											}
											break;
										}
									}
								}
							}
							String[] hosts = c.getStringValue(
									RealmHosts.ordinal(),
									(String) RealmHosts.defValue()).split(",");
							LocalAction locAction = LocalAction.valueOf(c
									.getStringValue(RealmLocalAction.ordinal(),
											"0"));
							boolean isDynamic = c.getBooleanValue(
									RealmEntryIsDynamic.ordinal(), false);
							long expirationTime = c.getLongValue(
									RealmEntryExpTime.ordinal(), 0);
							addRealm(name, appId, locAction, isDynamic,
									expirationTime, hosts);
						} catch (Exception e) {
							logger.warn("Can not append realm entry", e);
						}
					}
				}
			}
			logger.debug("*** {} ", network);
		}
	}

	protected IPeer getPeerPredProcessing(IMessage message, String destRealm,
			String destHost) {
		String localHost = metaData.getLocalPeer().getUri().getFQDN();
		String localRealm = metaData.getLocalPeer().getRealmName();
		// Check local host
		if ((destHost == null && destRealm == null && hasLocalApp(message))
				|| (destHost == null && destRealm != null
						&& destRealm.equals(localRealm) && hasLocalApp(message))
				|| (destHost != null && destHost.equals(localHost)
						&& destRealm != null && destRealm.equals(localRealm) && hasLocalApp(message))) {

			return (IPeer) metaData.getLocalPeer();
		} else {
			return null;
		}
	}

	public Realm addRealm(String name, ApplicationId applicationId,
			LocalAction localAction, boolean dynamic, long expirationTime,
			String... peers) {
		Realm realm = new RealmImpl(name, applicationId, localAction, dynamic,
				expirationTime, peers);
		network.put(name, realm);
		return realm;
	}

	public Realm remRealm(String name) {
		return network.remove(name);
	}

	public Set<Realm> getRealms() {
		return new HashSet<Realm>(network.values());
	}

	public void setNetWork(INetwork network) {
		logger.debug("*** set : {}", network);
		net = network;
	}

	private boolean hasLocalApp(IMessage message) {
		return message != null && net.getListener(message) != null;
	}

	protected boolean checkRealm(String name) {
		return name == null ? false : network.containsKey(name);
	}

	protected Set<String> getRealmsName() {
		return network.keySet();
	}

	protected String[] getRealmPeers(String key) {
		return network.get(key).getPeerHosts();
	}

}
