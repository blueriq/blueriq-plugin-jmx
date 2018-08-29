package com.blueriq;

import com.aquima.web.boot.RootConfig;

import com.blueriq.plugin.jmx.config.JmxConfig;

import org.springframework.boot.builder.SpringApplicationBuilder;

public class WarApplication extends AbstractWarApplication {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return RootConfig.configure(application).sources(JmxConfig.class);
  }
}
