package com.blueriq.plugin.jmx.monitoring;

import com.aquima.interactions.foundation.exception.AppException;
import com.aquima.interactions.metamodel.Channel;
import com.aquima.interactions.portal.ApplicationID;
import com.aquima.interactions.portal.IApplication;
import com.aquima.interactions.portal.IApplicationConfig;
import com.aquima.interactions.portal.IApplicationMonitor;
import com.aquima.interactions.portal.IPortalEventBus;
import com.aquima.interactions.portal.IPortalMessageBus;
import com.aquima.interactions.portal.IPortalSession;
import com.aquima.interactions.portal.IWebServices;
import com.aquima.interactions.portal.model.ISessionState;
import com.aquima.interactions.process.IProcessEngine;
import com.aquima.interactions.project.IUserData;
import com.aquima.interactions.project.ModuleID;

public class MonitorableApplication implements IApplication {
  private final IApplication delegate;
  private final Monitor monitor;

  public MonitorableApplication(IApplication delegate, Monitor monitor) {
    this.delegate = delegate;
    this.monitor = monitor;
  }

  protected IPortalSession wrapSession(IPortalSession original) {
    MonitorablePortalSession result = new MonitorablePortalSession(original, this.monitor);
    this.monitor.sessionCreated(result);
    return result;
  }

  @Override
  public IPortalSession createSession() throws AppException {
    return this.wrapSession(delegate.createSession());
  }

  @Override
  public IPortalSession createSession(IPortalMessageBus messageBus, IPortalEventBus eventBus) throws AppException {
    return this.wrapSession(delegate.createSession(messageBus, eventBus));
  }

  @Override
  public IPortalSession createSession(Channel channel, IUserData user) throws AppException {
    return this.wrapSession(delegate.createSession(channel, user));
  }

  @Override
  public IPortalSession createSession(Channel channel, IUserData user, IPortalMessageBus messageBus,
      IPortalEventBus eventBus) throws AppException {
    return this.wrapSession(delegate.createSession(channel, user, messageBus, eventBus));
  }

  @Override
  public IPortalSession createSession(ModuleID moduleId, Channel channel, IUserData user, IPortalMessageBus messageBus,
      IPortalEventBus eventBus) throws AppException {
    return this.wrapSession(delegate.createSession(moduleId, channel, user, messageBus, eventBus));
  }

  @Override
  public ApplicationID getId() {
    return this.delegate.getId();
  }

  @Override
  public IApplicationConfig getConfiguration() {
    return this.delegate.getConfiguration();
  }

  @Override
  public IApplicationMonitor getMonitor() {
    return this.delegate.getMonitor();
  }

  @Override
  public IPortalSession restoreSession(String sessionXml, IPortalMessageBus messageBus, IPortalEventBus eventBus)
      throws AppException {
    return this.wrapSession(delegate.restoreSession(sessionXml, messageBus, eventBus));
  }

  @Override
  public IPortalSession restoreSession(ISessionState sessionState, IPortalMessageBus messageBus,
      IPortalEventBus eventBus) throws AppException {
    return this.wrapSession(delegate.restoreSession(sessionState, messageBus, eventBus));
  }

  @Override
  public IProcessEngine getProcessEngine() throws AppException {
    return this.delegate.getProcessEngine();
  }

  @Override
  public IWebServices getWebServices() {
    return this.delegate.getWebServices();
  }
}
