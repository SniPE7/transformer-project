package org.jdiameter.client.impl;

/*
 * Copyright (c) 2006 jDiameter.
 * https://jdiameter.dev.java.net/
 *
 * License: GPL v3
 *
 * e-mail: erick.svenson@yahoo.com
 *
 */

import static org.jdiameter.client.impl.helpers.Parameters.AcctApplId;
import static org.jdiameter.client.impl.helpers.Parameters.ApplicationId;
import static org.jdiameter.client.impl.helpers.Parameters.AuthApplId;
import static org.jdiameter.client.impl.helpers.Parameters.OwnDiameterURI;
import static org.jdiameter.client.impl.helpers.Parameters.OwnFirmwareRevision;
import static org.jdiameter.client.impl.helpers.Parameters.OwnIPAddress;
import static org.jdiameter.client.impl.helpers.Parameters.OwnProductName;
import static org.jdiameter.client.impl.helpers.Parameters.OwnRealm;
import static org.jdiameter.client.impl.helpers.Parameters.OwnVendorID;
import static org.jdiameter.client.impl.helpers.Parameters.VendorId;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.api.Configuration;
import org.jdiameter.api.IllegalDiameterStateException;
import org.jdiameter.api.InternalException;
import org.jdiameter.api.OverloadException;
import org.jdiameter.api.Peer;
import org.jdiameter.api.PeerState;
import org.jdiameter.api.PeerStateListener;
import org.jdiameter.api.StackType;
import org.jdiameter.api.URI;
import org.jdiameter.api.app.StateChangeListener;
import org.jdiameter.client.api.IContainer;
import org.jdiameter.client.api.IMessage;
import org.jdiameter.client.api.IMetaData;
import org.jdiameter.client.api.controller.IPeer;
import org.jdiameter.client.api.fsm.EventTypes;
import org.jdiameter.client.api.io.IConnectionListener;
import org.jdiameter.client.api.io.TransportException;
import org.jdiameter.client.impl.helpers.IPConverter;
import org.jdiameter.common.api.statistic.IStatistic;
import org.jdiameter.common.api.statistic.IStatisticFactory;
import org.jdiameter.common.api.statistic.IStatisticRecord;
import org.jdiameter.common.impl.controller.AbstractPeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Use stack extension point
 */
public class MetaDataImpl implements IMetaData {

  private static final Logger log = LoggerFactory.getLogger(MetaDataImpl.class);
  protected List<MemoryPoolMXBean> beans = ManagementFactory.getMemoryPoolMXBeans();

  protected IContainer stack;
  protected int state;
  protected IPeer peer;
  protected Set<ApplicationId> appIds = new LinkedHashSet<ApplicationId>();

  public MetaDataImpl(IContainer s) {
    this.stack = s;
  }

  public MetaDataImpl(IContainer s, IStatisticFactory statisticFactory) {
    this(s);
    this.peer = newLocalPeer(statisticFactory);
    IStatisticRecord heapMemory = statisticFactory.newCounterRecord(IStatistic.Counters.HeapMemory,
        new IStatisticRecord.LongValueHolder() {
      public long getValueAsLong() {
        for (MemoryPoolMXBean bean : beans) {
          MemoryType memoryType = bean.getType();
          MemoryUsage memoryUsage = bean.getUsage();
          if (memoryType == MemoryType.HEAP) {
            return memoryUsage.getUsed();
          }
        }
        return 0;
      }

      public String getValueAsString() {
        return String.valueOf(getValueAsLong());
      }
    }
    );
    IStatisticRecord noHeapMemory = statisticFactory.newCounterRecord(IStatistic.Counters.NoHeapMemory,
        new IStatisticRecord.LongValueHolder() {
      public long getValueAsLong() {
        for (MemoryPoolMXBean bean : beans) {
          MemoryType memoryType = bean.getType();
          MemoryUsage memoryUsage = bean.getUsage();
          if (memoryType != MemoryType.HEAP) {
            return memoryUsage.getUsed();
          }
        }
        return 0;
      }

      public String getValueAsString() {
        return String.valueOf(getValueAsLong());
      }
    }
    );
    peer.getStatistic().appendCounter(heapMemory, noHeapMemory);
  }

  protected IPeer newLocalPeer(IStatisticFactory statisticFactory) {
    return new ClientLocalPeer(statisticFactory);
  }

  public Peer getLocalPeer() {
    return peer;
  }

  public int getMajorVersion() {
    return 2;
  }

  public int getMinorVersion() {
    return 1;
  }

  public StackType getStackType() {
    return StackType.TYPE_CLIENT;
  }

  public Peer getLocalPeerInfo() {
    return peer;
  }                              

  public Configuration getConfiguration() {
    return stack.getConfiguration();
  }

  public void updateLocalHostStateId() {
    state = Math.abs((int) System.currentTimeMillis());
  }

  public int getLocalHostStateId() {
    return state;
  }

  public boolean isWrapperFor(Class<?> aClass) throws InternalException {
    return aClass == IMetaData.class;
  }

  public <T> T unwrap(Class<T> aClass) throws InternalException {
    if (aClass == IMetaData.class) {
      return (T) this;
    }

    return null;
  }

  protected class ClientLocalPeer extends AbstractPeer implements IPeer {

    protected AtomicLong hopByHopId = new AtomicLong(0);
    protected InetAddress[] addresses = new InetAddress[0];

    public void resetAddresses() {
      addresses = new InetAddress[0];
    }

    public void connect() throws IllegalDiameterStateException {
      throw new IllegalDiameterStateException("Illegal operation");
    }

    public void disconnect() throws IllegalDiameterStateException {
      throw new IllegalDiameterStateException("Illegal operation");
    }

    public ClientLocalPeer(IStatisticFactory statisticFactory) {
      super(null, statisticFactory);
    }

    public <E> E getState(Class<E> anEnum) {
      switch (stack.getState()) {
      case IDLE:
        return (E) PeerState.DOWN;
      case CONFIGURED:
        return (E) PeerState.INITIAL;
      case STARTED:
        return (E) PeerState.OKAY;
      case STOPPED:
        return (E) PeerState.SUSPECT;
      }
      return (E) PeerState.DOWN;
    }

    public URI getUri() {
      try {
        return new URI(stack.getConfiguration().getStringValue(OwnDiameterURI.ordinal(), (String) OwnDiameterURI.defValue()));
      }
      catch (URISyntaxException e) {
        throw new IllegalArgumentException(e);
      }
      catch (UnknownServiceException e) {
        throw new IllegalArgumentException(e);
      }
    }

    public String getRealmName() {
      return stack.getConfiguration().getStringValue(OwnRealm.ordinal(), (String) OwnRealm.defValue());
    }

    public long getVendorId() {
      return stack.getConfiguration().getLongValue(OwnVendorID.ordinal(), (Long) OwnVendorID.defValue());
    }

    public String getProductName() {
      return stack.getConfiguration().getStringValue(OwnProductName.ordinal(), (String) OwnProductName.defValue());
    }

    public long getFirmware() {
      return stack.getConfiguration().getLongValue(OwnFirmwareRevision.ordinal(), -1L);
    }

    public Set<ApplicationId> getCommonApplications() {
      if (appIds.size() == 0) {
        Configuration[] apps = stack.getConfiguration().getChildren(ApplicationId.ordinal());
        if (apps != null) {
          for (Configuration a : apps) {
            long vnd = a.getLongValue(VendorId.ordinal(), 0L);
            long auth = a.getLongValue(AuthApplId.ordinal(), 0L);
            long acc = a.getLongValue(AcctApplId.ordinal(), 0L);
            if (auth != 0) {
              appIds.add(org.jdiameter.api.ApplicationId.createByAuthAppId(vnd, auth));
            }
            if (acc != 0) {
              appIds.add(org.jdiameter.api.ApplicationId.createByAccAppId(vnd, acc));
            }
          }
        }
      }
      return appIds;
    }

    public InetAddress[] getIPAddresses() {
      if (addresses.length == 0) {
        String address = stack.getConfiguration().getStringValue(OwnIPAddress.ordinal(), null);
        if (address == null || address.length() == 0) {
          try {
            addresses = new InetAddress[]{InetAddress.getByName(getUri().getFQDN())};
          }
          catch (UnknownHostException e) {
            try {
              addresses = new InetAddress[]{InetAddress.getLocalHost()};
            }
            catch (UnknownHostException e1) {
              addresses = new InetAddress[0];
            }
          }
        }
        else {
          InetAddress ia = IPConverter.InetAddressByIPv4(address);
          if (ia == null) {
            ia = IPConverter.InetAddressByIPv6(address);
          }
          if (ia == null) {
            try {
              addresses = new InetAddress[]{InetAddress.getLocalHost()};
            } catch (UnknownHostException e) {
              addresses = new InetAddress[0];
            }
          }
          else {
            addresses = new InetAddress[]{ia};
          }
        }
      }
      return addresses;
    }

    public IStatistic getStatistic() {
      return statistic;
    }

    public String toString() {
      return "Peer{" +
      "\n\tUri=" + getUri() + "; RealmName=" + getRealmName() + "; VendorId=" + getVendorId() +
      ";\n\tProductName=" + getProductName() + "; FirmWare=" + getFirmware() +
      ";\n\tAppIds=" + getCommonApplications() +
      ";\n\tIPAddresses=" + Arrays.asList(getIPAddresses()).toString() + ";" + "\n}";
    }

    public int getRating() {
      return 0;
    }

    public void addPeerStateListener(PeerStateListener peerStateListener) {
    }

    public void removePeerStateListener(PeerStateListener peerStateListener) {
    }

    public long getHopByHopIdentifier() {
      return hopByHopId.incrementAndGet();
    }

    public void addMessage(IMessage message) {
    }

    public void remMessage(IMessage message) {
    }

    public IMessage[] remAllMessage() {
      return new IMessage[0];
    }

    public boolean handleMessage(EventTypes type, IMessage message, String key) throws TransportException, OverloadException, InternalException {
      return false;  
    }

    public boolean sendMessage(IMessage message) throws TransportException, OverloadException {
      return false;
    }

    public boolean hasValidConnection() {
      return false;
    }

    public void setRealm(String realm) {
    }

    public void addStateChangeListener(StateChangeListener listener) {
    }

    public void remStateChangeListener(StateChangeListener listener) {
    }

    public void addConnectionListener(IConnectionListener listener) {
    }

    public void remConnectionListener(IConnectionListener listener) {
    }
  }
}
