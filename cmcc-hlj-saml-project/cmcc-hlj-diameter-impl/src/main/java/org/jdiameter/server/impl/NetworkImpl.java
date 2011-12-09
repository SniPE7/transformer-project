package org.jdiameter.server.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.jdiameter.api.ApplicationAlreadyUseException;
import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.LocalAction;
import org.jdiameter.api.Message;
import org.jdiameter.api.NetworkReqListener;
import org.jdiameter.api.Peer;
import org.jdiameter.api.Realm;
import org.jdiameter.api.Selector;
import org.jdiameter.api.Statistic;
import org.jdiameter.api.URI;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticFactory;
import org.jdiameter.common.api.statistic.IStatisticRecord;
import org.jdiameter.server.api.IMetaData;
import org.jdiameter.server.api.IMutablePeerTable;
import org.jdiameter.server.api.INetwork;
import org.jdiameter.server.api.IRouter;
import org.jdiameter.server.impl.helpers.StatisticAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkImpl implements INetwork {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkImpl.class);

	protected IMutablePeerTable manager;
	protected IRouter router;
	protected IMetaData metaData;
	private final ApplicationId commonAuthAppId = ApplicationId
			.createByAuthAppId(0, 0xffffffff);
	private final ApplicationId commonAccAppId = ApplicationId
			.createByAccAppId(0, 0xffffffff);
	private final ConcurrentHashMap<ApplicationId, NetworkReqListener> appIdToNetListener = new ConcurrentHashMap<ApplicationId, NetworkReqListener>();
	private final ConcurrentHashMap<Selector<Message, ApplicationId>, NetworkReqListener> selectorToNetListener = new ConcurrentHashMap<Selector<Message, ApplicationId>, NetworkReqListener>();

	protected IStatistic statistic;

	public NetworkImpl(IStatisticFactory statisticFactory, IMetaData metaData,
			IRouter router) {
		logger.debug("*** {} ", this);
		this.router = router;
		this.metaData = metaData;
		this.router.setNetWork(this);
		IStatisticRecord nrlStat = statisticFactory.newCounterRecord(
				IStatistic.Counters.RequestListenerCount,
				new IStatisticRecord.IntegerValueHolder() {
					public int getValueAsInt() {
						return appIdToNetListener.size();
					}

					public String getValueAsString() {
						return String.valueOf(getValueAsInt());
					}

				});
		IStatisticRecord nslStat = statisticFactory.newCounterRecord(
				IStatistic.Counters.SelectorCount,
				new IStatisticRecord.IntegerValueHolder() {
					public int getValueAsInt() {
						return selectorToNetListener.size();
					}

					public String getValueAsString() {
						return String.valueOf(getValueAsInt());
					}

				});
		statistic = statisticFactory.newStatistic(IStatistic.Groups.Network,
				nrlStat, nslStat);
	}

	public void addNetworkReqListener(NetworkReqListener networkReqListener,
			ApplicationId... applicationId)
			throws ApplicationAlreadyUseException {
		for (ApplicationId a : applicationId) {
			if (appIdToNetListener.containsKey(commonAuthAppId)
					|| appIdToNetListener.containsKey(commonAccAppId))
				throw new ApplicationAlreadyUseException(a
						+ " already use by common application id");

			if (appIdToNetListener.containsKey(applicationId))
				throw new ApplicationAlreadyUseException(a + " already use");

			appIdToNetListener.put(a, networkReqListener);
			metaData.addApplicationId(a);
		}
	}

	public void addNetworkReqListener(NetworkReqListener listener,
			Selector<Message, ApplicationId>... selectors) {
		for (Selector<Message, ApplicationId> s : selectors) {
			selectorToNetListener.put(s, listener);
			ApplicationId ap = s.getMetaData();
			metaData.addApplicationId(ap);
		}
	}

	public void removeNetworkReqListener(ApplicationId... applicationId) {
		for (ApplicationId a : applicationId) {
			appIdToNetListener.remove(a);
			for (Selector<Message, ApplicationId> s : selectorToNetListener
					.keySet()) {
				if (s.getMetaData().equals(a))
					return;
			}
			metaData.remApplicationId(a);
		}
	}

	public void removeNetworkReqListener(
			Selector<Message, ApplicationId>... selectors) {
		for (Selector<Message, ApplicationId> s : selectors) {
			selectorToNetListener.remove(s);
			if (appIdToNetListener.containsKey(s.getMetaData())) {
				return;
			}

			for (Selector<Message, ApplicationId> i : selectorToNetListener
					.keySet()) {
				if (i.getMetaData().equals(s.getMetaData())) {
					return;
				}
			}
			metaData.remApplicationId(s.getMetaData());
		}
	}

	public Peer addPeer(String name, String realm, boolean connecting) {
		if (manager != null)
			try {
				return manager.addPeer(new URI(name), realm, connecting);
			} catch (Exception e) {
				return null;
			}
		else
			return null;
	}

	public boolean isWrapperFor(Class<?> aClass) throws InternalException {
		return false;
	}

	public <T> T unwrap(Class<T> aClass) throws InternalException {
		return null;
	}

	public Realm addRealm(String name, ApplicationId applicationId,
			LocalAction localAction, boolean dynamic, long expirationTime) {
		return router.addRealm(name, applicationId, localAction, dynamic,
				expirationTime);
	}

	public Realm remRealm(String name) {
		return router.remRealm(name);
	}

	public Statistic getStatistic() {
		return StatisticAdaptor.adapt(statistic);
	}

	public NetworkReqListener getListener(IMessage message) {
		if (message == null)
			return null;
		for (Selector<Message, ApplicationId> s : selectorToNetListener
				.keySet()) {
			boolean r = s.checkRule(message);
			if (r)
				return selectorToNetListener.get(s);
		}

		ApplicationId appId = message.getSingleApplicationId();
		if (appId == null)
			return null;
		if (appIdToNetListener.containsKey(commonAuthAppId))
			return appIdToNetListener.get(commonAuthAppId);
		else if (appIdToNetListener.containsKey(commonAccAppId))
			return appIdToNetListener.get(commonAccAppId);
		else
			return appIdToNetListener.get(appId);
	}

	public void setPeerManager(IMutablePeerTable manager) {
		logger.debug("*** set : {}", manager);
		this.manager = manager;
	}

}