/*
 * Copyright (c) 2006 jDiameter.
 * https://jdiameter.dev.java.net/
 *
 * License: GPL v3
 *
 * e-mail: erick.svenson@yahoo.com
 *
 */
package org.jdiameter.client.api;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jdiameter.api.ApplicationId;
import org.jdiameter.client.api.controller.IPeer;

/**
 * This interface extends basic message interface
 * Data: $Date: 2009/07/27 18:05:03 $
 * Revision: $Revision: 1.2 $
 * @version 1.5.0.1
 */
public interface IMessage extends IRequest, IAnswer {

  /**
   * The message is not sent to the network
   */
  int STATE_NOT_SENT = 0;
  /**
   * 	The message is ent to the network
   */
  int STATE_SENT = 1;
  /**
   * The message is biffered ( not use yet )
   */
  int STATE_BUFFERED = 2;
  /**
   * 	Stack received answer to this message
   */
  int STATE_ANSWERED = 3;

  /**
   * Return state of message
   * @return state of message
   */
  int getState();

  /**
   * Set new state
   * @param newState new state value
   */
  void setState(int newState);

  /**
   * Return header applicationId
   * @return header applicationId
   */
  long getHeaderApplicationId();

  /**
   * Set header message application id
   * @param applicationId header message application id
   */
  void setHeaderApplicationId(long applicationId);

  /**
   * Return flags as inteher
   * @return flags as inteher
   */
  int getFlags();

  /**
   * Create timer for request timout procedure
   * @param scheduledFacility timer facility
   * @param timeOut value of timeout
   * @param timeUnit time unit
   */
  void createTimer(ScheduledExecutorService scheduledFacility, long timeOut, TimeUnit timeUnit);

  /**
   * Execute timer task
   */
  void runTimer();

  /**
   * Cancel timer
   */
  void clearTimer();

  /**
   * Set hop by hop id
   * @param hopByHopId   hopByHopId value
   */
  void setHopByHopIdentifier(long hopByHopId);

  /**
   * Set end by end id
   * @param endByEndId  endByEndId value
   */
  void setEndToEndIdentifier(long endByEndId);

  /**
   * Return attached peer
   * @return attached peer
   */
  IPeer getPeer();

  /**
   * Attach message to peer
   * @param peer attached peer
   */
  void setPeer(IPeer peer);

  /**
   * Return application id
   * @return application id
   */
  ApplicationId getSingleApplicationId();

  /**
   * Return application id
   * @return application id
   */
  ApplicationId getSingleApplicationId(long id);

  /**
   * Check timeout
   * @return true if request has timeout
   */
  boolean isTimeOut();

  /**
   * Set event listener
   * @param listener event listener
   */
  void setListener(IEventListener listener);

  /**
   * Return event listener
   * @return event listener
   */
  IEventListener getEventListener();

  /**
   * Return duplication key of message
   * @return duplication key of message
   */
  String getDuplicationKey();

  /**
   * Generate duplication key
   * @param host origination host
   * @param endToEndId end to end id
   * @return duplication key
   */
  String getDuplicationKey(String host, long endToEndId);

  /**
   * Create clone object
   * @return clone
   */
  Object clone();    
}
