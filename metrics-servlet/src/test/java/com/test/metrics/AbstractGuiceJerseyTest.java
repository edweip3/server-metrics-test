package com.test.metrics;

import com.google.inject.*;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.LowLevelAppDescriptor;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractGuiceJerseyTest extends AbstractModule
{
    protected Logger log;
    private GuicedJerseyTest wrappedTest;
    @Inject
    protected Injector injector;
    @Inject
    private Provider<GuiceInMemoryTestContainerFactory> containerFactory;

    @Override
    protected void configure()
    {
    }

    @Before
    public void setupLogger()
    {
        log = LoggerFactory.getLogger(this.getClass());
    }

    @Before
    public void setupWrappedTest() throws Exception
    {
        Guice.createInjector(this).injectMembers(this);
        wrappedTest = new GuicedJerseyTest();
        wrappedTest.setUp();
    }

    @After
    public void teardownWrappedTest() throws Exception
    {
        wrappedTest.tearDown();
    }

    public WebResource resource()
    {
        return wrappedTest.resource();
    }

    private class GuicedJerseyTest extends JerseyTest
    {
        @Override
        protected AppDescriptor configure()
        {
            setTestContainerFactory(containerFactory.get());
            return new LowLevelAppDescriptor.Builder("ignore").contextPath("rest").build();
        }
    }
}
