/*******************************************************************************
 * Copyright © 2012,2014 IBM Corporation and other Contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial Contribution
 *******************************************************************************/
package com.ibm.example.wlmjmsattach;

import javax.jms.JMSException;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

/**
 * A wrapping object containing a JMS TopicConnection, TopicSession and TopicPublisher
 */
public class WLMJMSTopicPublisher {

  /** A logger for debug */
  private static final WLMJMSLogger log = new WLMJMSLogger(WLMJMSTopicPublisher.class);
  
  /** The JMS Topic Connection */
  private final TopicConnection topicConnection;
  
  /** The JMS Topic Session */
  private final TopicSession topicSession;
  
  /** The JMS Topic Publisher */
  private final TopicPublisher topicPublisher;
  

/**
 * Constructor (package private)
 * @param topicConnection
 * @param topicSession
 * @param topicPublisher
 */
  WLMJMSTopicPublisher(TopicConnection topicConnection, TopicSession topicSession, TopicPublisher topicPublisher) {
    this.topicConnection = topicConnection;
    this.topicSession = topicSession;
    this.topicPublisher = topicPublisher;
  }
  
  /**
   * @return The JMS Topic Connection
   */
  public TopicConnection getTopicConnection() {
    return topicConnection;
  }
    
  /**
   * @return The JMS Topic Session
   */
  public TopicSession getTopicSession() {
    return topicSession;
  }
  
  /**
   * @return The JMS Topic Publisher
   */  
  public TopicPublisher getTopicPublisher() {
    return topicPublisher;
  }

  /**
   * Close all the resource under this object. 
   * @param throwExceptions JMSExceptions are only thrown when this is set to true. Otherwise they are suppressed
   * @throws JMSException The last exception encountered during the close, if any and throwExceptions set
   */
  public void close(boolean throwExceptions) throws JMSException {
    final String methodName = "close";
    JMSException lastException = null;
    try {
      topicPublisher.close();
    }
    catch (JMSException e) {
      if (log.enabled()) log.logExStack(methodName, "Publisher close failed", e);
      lastException = e; 
    }
    try {
      topicSession.close();
    }
    catch (JMSException e) {
      if (log.enabled()) log.logExStack(methodName, "Session close failed", e);
      lastException = e; 
    }
    try {
      topicConnection.close();
    }
    catch (JMSException e) {
      if (log.enabled()) log.logExStack(methodName, "Connection close failed", e);
      lastException = e; 
    }
    if (lastException != null && throwExceptions) {
      if (log.enabled()) log.logRootExMsg(methodName, "Throwing. Root exception", lastException);
      throw lastException;
    }
  }
}
