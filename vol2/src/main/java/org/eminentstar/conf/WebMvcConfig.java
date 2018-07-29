package org.eminentstar.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(
  basePackages = {
    "org.eminentstar.learningtest.spring.controllers",
    "org.eminentstar.mvc.controller",
    "org.eminentstar.mvc.service",
    "org.eminentstar.modelbinding.propertyeditor"
  }
)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
}
