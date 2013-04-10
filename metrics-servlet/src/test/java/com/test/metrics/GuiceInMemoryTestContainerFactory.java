package com.test.metrics;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scope;
import com.google.inject.servlet.ServletScopes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.WebApplicationFactory;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import com.sun.jersey.test.framework.impl.container.inmemory.TestResourceClientHandler;
import com.sun.jersey.test.framework.spi.container.TestContainer;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import java.net.URI;
import java.util.Map;

/**
 * A Jersey in-memory test container compatible with Guice.
 *
 * Jersey provides a light-weight in-memory container for testing Jersey REST
 * resources. However, this container does not support testing resources that
 * are wired using Google Guice. This class provides a basic implementation of a
 * Jersey in-memory container that support testing Guice-wired REST resources.
 * In addition, the test classes themselves can be injected by the same Guice
 * modules.
 *
 * @see https://github.com/runepeter/jersey-guice-test-framework#readme
 * @author Rune Peter Bjronstad <runepeter@gmail.com>
 */
class GuiceInMemoryTestContainerFactory implements TestContainerFactory
{
    @Inject
    private Injector injector;

    @SuppressWarnings("unchecked")
    @Override
    public Class<LowLevelAppDescriptor> supports()
    {
        return LowLevelAppDescriptor.class;
    }

    @Override
    public TestContainer create(URI uri, AppDescriptor descriptor) throws IllegalArgumentException
    {
        return new GuiceInMemoryTestContainer(uri, (LowLevelAppDescriptor) descriptor, injector);
    }

    private static class GuiceInMemoryTestContainer implements TestContainer
    {
        private final URI baseUri;
        private final ResourceConfig config;
        private final WebApplication application;
        private final Injector injector;
        private final ClientConfig cc;

        public GuiceInMemoryTestContainer(URI baseUri, LowLevelAppDescriptor descriptor, Injector injector)
        {
            this.baseUri = baseUri;
            this.config = descriptor.getResourceConfig();
            this.config.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS, LoggingFilter.class.getName());
            this.config.getProperties().put(ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, LoggingFilter.class.getName());
            this.application = WebApplicationFactory.createWebApplication();
            this.injector = injector;
            this.cc = new DefaultClientConfig();
        }

        @Override
        public Client getClient()
        {
            return new Client(new TestResourceClientHandler(baseUri, application), cc);
        }

        @Override
        public URI getBaseUri()
        {
            return baseUri;
        }

        @Override
        public void start()
        {
            if (!application.isInitiated()) {
                application.initiate(config, new GuiceComponentProviderFactory(config, injector)
                {
                    @Override
                    public Map<Scope, ComponentScope> createScopeMap()
                    {
                        Map<Scope, ComponentScope> m = super.createScopeMap();
                        m.put(ServletScopes.REQUEST, ComponentScope.PerRequest);
                        return m;
                    }
                });
            }
        }

        @Override
        public void stop()
        {
            if (application.isInitiated()) {
                application.destroy();
            }
        }
    }
}