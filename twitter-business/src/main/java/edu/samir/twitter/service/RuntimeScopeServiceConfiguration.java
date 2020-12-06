package edu.samir.twitter.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"edu.samir.twitter.service", "edu.samir.twitter.data" })
public class RuntimeScopeServiceConfiguration { }
