package com.aquima.plugin.jmx.integration;

import com.aquima.interactions.foundation.DataType;
import com.aquima.interactions.portal.IApplication;
import com.aquima.interactions.portal.IApplicationConfig;
import com.aquima.interactions.portal.IPortalSession;
import com.aquima.interactions.test.templates.ApplicationTemplate;
import com.aquima.interactions.test.templates.EngineFactory;
import com.aquima.web.config.PluginManager;

import com.blueriq.plugin.jmx.config.JmxConfig;
import com.blueriq.plugin.jmx.management.ManagerBean;
import com.blueriq.plugin.jmx.monitoring.Monitor;
import com.blueriq.plugin.jmx.monitoring.MonitorBean;
import com.blueriq.plugin.jmx.monitoring.MonitorableApplication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { JmxConfig.class, TestConfig.class })
public class JmxPluginTest {

  @Autowired
  private ManagerBean managerBean;

  @Autowired
  private MonitorBean monitorBean;

  @Autowired
  private PluginManager pluginManager;

  @Autowired
  private Monitor monitor;

  @Test
  public void testMonitorableApplication() {
    IApplication application = setupApplication();
    MonitorableApplication monitorableApplication = new MonitorableApplication(application, monitor);

    Assert.assertEquals(0, monitorBean.getNumberSessions());
    IPortalSession monitorablePortalSession = monitorableApplication.createSession();
    Assert.assertEquals(1, monitorBean.getNumberSessions());

    Assert.assertEquals(0, monitorBean.getAverageComposeTime());
    monitorablePortalSession.startFlow("start", null, false);
    Assert.assertTrue(true); // bug https://tools.blueriq.com/jira/browse/AQ-11533

    IApplicationConfig config = monitorableApplication.getConfiguration();
    Assert.assertNotNull(config);
  }

  private IApplication setupApplication() {
    ApplicationTemplate applicationTemplate = new ApplicationTemplate();

    applicationTemplate.getMetaModel().addEntity("Person", null, true).addAttribute("Name", DataType.STRING, false);
    applicationTemplate.getComposer().addPage("page").addField("Person.Name").setIsRefreshField(true);
    applicationTemplate.getFlowEngine().addFlow("start").addPage("page").addEndNode("ok");
    return EngineFactory.createPortalEngine(applicationTemplate).getApplication(applicationTemplate.getApplicationId());
  }
}
