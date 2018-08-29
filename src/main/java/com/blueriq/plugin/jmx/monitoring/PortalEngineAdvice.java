package com.blueriq.plugin.jmx.monitoring;

import com.aquima.interactions.portal.IApplication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PortalEngineAdvice {
  private static final Logger LOG = LoggerFactory.getLogger(PortalEngineAdvice.class);

  private final Monitor monitor;

  @Autowired
  public PortalEngineAdvice(Monitor monitor) {
    this.monitor = monitor;
  }

  @Around("execution(* com.aquima.interactions.portal.IPortalEngine.getApplication(..))")
  public Object getApplication(ProceedingJoinPoint pjp) throws Throwable {
    IApplication application = (IApplication) pjp.proceed();

    if (LOG.isDebugEnabled()) {
      LOG.debug("Creating monitorable application for " + application.getId());
    }

    return new MonitorableApplication(application, this.monitor);
  }
}
