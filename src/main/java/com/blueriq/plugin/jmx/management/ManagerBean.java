package com.blueriq.plugin.jmx.management;

import com.aquima.web.config.PluginManager;

import com.blueriq.component.api.plugin.IPluginMetadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@ManagedResource
@Component
public class ManagerBean {

  private final ApplicationContext applicationContext;
  private final PluginManager pluginManager;

  @Autowired
  public ManagerBean(ApplicationContext applicationContext, PluginManager pluginManager) {
    this.applicationContext = applicationContext;
    this.pluginManager = pluginManager;
  }

  @ManagedOperation(description = "Returns all installed plugins")
  public List<String> getInstalledPlugins() {
    List<String> result = new ArrayList<String>();
    for (IPluginMetadata plugin : pluginManager.getPlugins()) {
      result.add(plugin.getArtifactId());
    }
    return result;
  }

  @ManagedOperation(description = "Reloads the configuration")
  public void reloadConfiguration() {
    refreshParentFirst(applicationContext);
  }

  private void refreshParentFirst(ApplicationContext applicationContext) {
    if (applicationContext.getParent() != null) {
      refreshParentFirst(applicationContext.getParent());
    }
    if (applicationContext instanceof ConfigurableWebApplicationContext) {
      ConfigurableWebApplicationContext configurableWebApplicationContext =
          (ConfigurableWebApplicationContext) applicationContext;
      configurableWebApplicationContext.refresh();
    }
  }
}
