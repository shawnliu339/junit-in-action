package com.shearan.junitinaction.chapter08;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class JMockitSampleServletTest {

    @Tested
    private SampleServlet servlet;
    @Mocked
    private HttpServletRequest mockHttpServletRequest;
    @Mocked
    private HttpSession mockHttpSession;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsAuthenticatedAuthenticated() {

        new Expectations() {{
           mockHttpServletRequest.getSession(false); result=mockHttpSession;
           mockHttpSession.getAttribute("authenticated"); result="true";
        }};

        assertTrue(servlet.isAuthenticated(mockHttpServletRequest));
    }

    @Test
    public void testIsAuthenticatedNotAuthenticated() {
        new Expectations() {{
            mockHttpServletRequest.getSession(false); result=mockHttpSession;
            mockHttpSession.getAttribute("authenticated"); result="false";
        }};

        assertFalse(servlet.isAuthenticated(mockHttpServletRequest));
    }

    @Test
    public void testIsAuthenticatedNoSession() {
        new Expectations() {{
           mockHttpServletRequest.getSession(false); result = null;
        }};

        assertFalse(servlet.isAuthenticated(mockHttpServletRequest));
    }

    @After
    public void tearDown() { }
}