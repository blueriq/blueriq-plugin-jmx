package com.blueriq.plugin.jmx.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = { "com.blueriq.plugin.jmx" })
@EnableAspectJAutoProxy
public class JmxConfig {

}
