package com.blueriq.plugin.jmx.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@ManagedResource
@Component
public class MonitorBean {

  private final Monitor monitor;

  @Autowired
  public MonitorBean(Monitor monitor) {
    this.monitor = monitor;
  }

  @ManagedAttribute(description = "The current active number of sessions")
  public long getNumberSessions() {
    return this.monitor.getSessionCounter();
  }

  @ManagedOperation(description = "The occured exceptions")
  public List<Exception> getOccuredExceptions() {
    return this.monitor.getOccuredExceptions();
  }

  @ManagedOperation(description = "Reset the occured exceptions")
  public void resetOccuredExceptions() {
    this.monitor.resetOccuredExceptions();
  }

  @ManagedAttribute(description = "The current average event time in ms")
  public long getAverageEventTime() {
    return this.monitor.getAverageEventTime();
  }

  @ManagedOperation(description = "Reset the average event time")
  public void resetAverageEventTime() {
    this.monitor.resetAverageEventTime();
  }

  @ManagedAttribute(description = "The current average compose time in ms")
  public long getAverageComposeTime() {
    return this.monitor.getAverageComposeTime();
  }

  @ManagedOperation(description = "Reset the average compose time")
  public void resetAverageComposeTime() {
    this.monitor.resetAverageComposeTime();
  }
}
