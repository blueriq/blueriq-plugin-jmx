package com.blueriq.plugin.jmx.monitoring;

import com.aquima.interactions.composer.IPage;
import com.aquima.interactions.flow.IFlowState;
import com.aquima.interactions.foundation.exception.AppException;
import com.aquima.interactions.foundation.text.ILanguage;
import com.aquima.interactions.metamodel.Channel;
import com.aquima.interactions.metamodel.exception.UnknownLanguageException;
import com.aquima.interactions.portal.ApplicationID;
import com.aquima.interactions.portal.IActionHandler;
import com.aquima.interactions.portal.IActionResult;
import com.aquima.interactions.portal.IPortalEventBus;
import com.aquima.interactions.portal.IPortalMessageBus;
import com.aquima.interactions.portal.IPortalSession;
import com.aquima.interactions.portal.IPortalSessionCallback;
import com.aquima.interactions.portal.IPortalSessionState;
import com.aquima.interactions.portal.ISessionActivationContext;
import com.aquima.interactions.portal.ISessionMonitor;
import com.aquima.interactions.portal.PortalEvent;
import com.aquima.interactions.portal.PortalException;
import com.aquima.interactions.portal.PortalResponse;
import com.aquima.interactions.portal.model.IModule;
import com.aquima.interactions.project.IProject;

import java.util.Map;

public class MonitorablePortalSession implements IPortalSession {
  private final IPortalSession delegate;
  private final Monitor monitor;

  public MonitorablePortalSession(IPortalSession delegate, Monitor monitor) {
    this.delegate = delegate;
    this.monitor = monitor;
  }

  @Override
  public boolean close() {
    boolean result = delegate.close();
    if (result) {
      monitor.sessionClosed(this);
    }
    return result;
  }

  @Override
  public String getSessionId() {
    return delegate.getSessionId();
  }

  @Override
  public void setSessionId(String id) {
    delegate.setSessionId(id);
  }

  @Override
  public ApplicationID getApplicationId() {
    return delegate.getApplicationId();
  }

  @Override
  public ISessionMonitor getMonitor() {
    return delegate.getMonitor();
  }

  @Override
  public IModule getModule() {
    return delegate.getModule();
  }

  @Override
  public IProject getConfiguration() {
    return delegate.getConfiguration();
  }

  @Override
  @SuppressWarnings("rawtypes")
  public PortalResponse startFlow(String flowName, Map parameters, boolean allowUnExposedFlows) throws AppException {
    try {
      return delegate.startFlow(flowName, parameters, allowUnExposedFlows);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    }
  }

  @Override
  public void stopFlow(boolean commitPendingChanges) throws AppException {
    try {
      delegate.stopFlow(commitPendingChanges);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    }
  }

  @Override
  public PortalResponse handleEvent(PortalEvent event) throws AppException {
    long start = System.currentTimeMillis();
    try {
      return delegate.handleEvent(event);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    } finally {
      monitor.eventHandled(System.currentTimeMillis() - start);
    }
  }

  @Override
  @SuppressWarnings("rawtypes")
  public IActionResult executeAction(String handlerName, Map parameters) throws PortalException {
    try {
      return delegate.executeAction(handlerName, parameters);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    }
  }

  @Override
  @SuppressWarnings("rawtypes")
  public IActionResult executeInlineAction(IActionHandler handler, Map parameters) throws PortalException {
    try {
      return delegate.executeInlineAction(handler, parameters);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    }
  }

  @Override
  public void executeCallback(IPortalSessionCallback callback) throws PortalException {
    try {
      delegate.executeCallback(callback);
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    }
  }

  @Override
  public IPage getCurrentPage() {
    long start = System.currentTimeMillis();
    try {
      return delegate.getCurrentPage();
    } catch (AppException e) {
      monitor.exceptionOccured(e);
      throw e;
    } catch (RuntimeException e) {
      monitor.exceptionOccured(e);
      throw e;
    } finally {
      monitor.composeHandled(System.currentTimeMillis() - start);
    }
  }

  @Override
  public IFlowState getCurrentFlowState() {
    return delegate.getCurrentFlowState();
  }

  @Override
  public void setCurrentLanguage(String languageCode) throws UnknownLanguageException {
    delegate.setCurrentLanguage(languageCode);
  }

  @Override
  public ILanguage getCurrentLanguage() {
    return delegate.getCurrentLanguage();
  }

  @Override
  public IPortalSessionState getState() {
    return delegate.getState();
  }

  @Override
  public void restore(IPortalSessionState state) {
    delegate.restore(state);
  }

  @Override
  public void reset() {
    delegate.reset();
  }

  @Override
  public Channel getCurrentChannel() {
    return delegate.getCurrentChannel();
  }

  @Override
  public IPortalMessageBus getMessageBus() {
    return delegate.getMessageBus();
  }

  @Override
  public IPortalEventBus getEventBus() {
    return null;
  }

  @Override
  public String getCurrentFlow() {
    return delegate.getCurrentFlow();
  }

  @Override
  public void interrupt() {
    delegate.interrupt();
  }

  @Override
  public boolean isInterrupted() {
    return delegate.isInterrupted();
  }

  @Override
  public void activate(ISessionActivationContext sessionActivationContext) {
    delegate.activate(sessionActivationContext);
  }

  @Override
  public void passivate() {
    delegate.passivate();
  }
}
