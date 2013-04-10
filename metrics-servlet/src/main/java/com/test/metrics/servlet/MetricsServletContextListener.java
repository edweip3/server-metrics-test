package com.test.metrics.servlet;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MetricsServletContextListener extends GuiceServletContextListener
{
    private static final Logger log = LoggerFactory.getLogger(MetricsServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        log.info("Initialized.");
        super.contextInitialized(servletContextEvent);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
        log.info("Destroyed.");
        super.contextDestroyed(servletContextEvent);
    }

    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                install(new MetricsServletModule());
            }
        });
    }
}
