package com.test.metrics.servlet;

import com.google.common.collect.ImmutableMap;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.test.metrics.resource.ClientMetricsResourceV1;
import com.test.metrics.resource.ClientMetricsResourceV2;

public class MetricsServletModule extends ServletModule
{
    @Override
    protected void configureServlets()
    {
        bind(ClientMetricsResourceV1.class);
        bind(ClientMetricsResourceV2.class);
        serve("/*").with(
                GuiceContainer.class,
                ImmutableMap.of("com.sun.jersey.config.feature.Trace", "true"));
    }
}