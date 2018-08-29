package com.blueriq.plugin.jmx.monitoring;

import com.aquima.interactions.portal.IPortalSession;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class Monitor {
  private final AtomicLong sessionCounter = new AtomicLong();
  private final List<Exception> occuredExceptions = new ArrayList<Exception>();
  private long totalEventTime;
  private long numberEvents;
  private long totalComposeTime;
  private long numberComposes;

  public long getSessionCounter() {
    return sessionCounter.get();
  }

  public void sessionCreated(IPortalSession session) {
    sessionCounter.getAndIncrement();
  }

  public void sessionClosed(IPortalSession session) {
    sessionCounter.getAndDecrement();
  }

  public List<Exception> getOccuredExceptions() {
    return occuredExceptions;
  }

  public void exceptionOccured(Exception ex) {
    occuredExceptions.add(ex);
  }

  public void resetOccuredExceptions() {
    occuredExceptions.clear();
  }

  public long getAverageEventTime() {
    return numberEvents > 0 ? totalEventTime / numberEvents : 0;
  }

  public void eventHandled(long elapsedTime) {
    totalEventTime += elapsedTime;
    numberEvents++;
  }

  public void resetAverageEventTime() {
    totalEventTime = 0;
    numberEvents = 0;
  }

  public long getAverageComposeTime() {
    return numberComposes > 0 ? totalComposeTime / numberComposes : 0;
  }

  public void composeHandled(long elapsedTime) {
    totalComposeTime += elapsedTime;
    numberComposes++;
  }

  public void resetAverageComposeTime() {
    totalComposeTime = 0;
    numberComposes = 0;
  }
}
