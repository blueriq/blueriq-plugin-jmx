package com.aquima.plugin.jmx.integration;

import com.aquima.web.config.PluginManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

  @Bean
  public PluginManager pluginManager() {
    return new PluginManager();
  }

}
