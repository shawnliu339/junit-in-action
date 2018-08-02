package com.shearan.junitinaction.codecheck;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestMain {

    @Rule
    public final SystemOutRule log = new SystemOutRule().enableLog();
    private String input;
    private String expected;

    public TestMain(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{
                "2017/02\n" +
                        "2017/02/01 08:00-12:00 13:00-16:00\n",
                "0\n0\n0\n0\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/01 08:00-12:00 13:00-17:00\n",
                "1\n0\n0\n0\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/01 08:00-12:00 13:00-21:00\n",
                "1\n4\n0\n0\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/01 10:00-12:00 13:00-21:00\n",
                "3\n2\n0\n0\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/01 08:00-12:00 13:00-26:00\n",
                "1\n9\n4\n0\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/04 08:00-12:00 13:00-23:00\n",
                "0\n0\n1\n14\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/03 08:00-12:00 13:00-26:00\n",
                "1\n7\n4\n2\n0\n"
        }, {
                "2017/02\n" +
                        "2017/02/05 08:00-12:00 13:00-23:00\n",
                "0\n0\n1\n0\n14\n"
        }, {
                "2017/02\n" +
                        "2017/02/04 08:00-12:00 13:00-26:00\n",
                "0\n0\n4\n15\n2\n"
        }, {
                "2017/01\n" +
                        "2017/01/16 08:00-12:00 13:00-18:00\n" +
                        "2017/01/17 08:00-12:00 13:00-18:00\n" +
                        "2017/01/18 08:00-12:00 13:00-18:00\n" +
                        "2017/01/19 08:00-12:00 13:00-17:00\n",
                "4\n3\n0\n0\n0\n"
        }, {
                "2017/01\n" +
                        "2017/01/16 08:00-12:00 13:00-18:00\n" +
                        "2017/01/17 08:00-12:00 13:00-18:00\n" +
                        "2017/01/18 08:00-12:00 13:00-18:00\n" +
                        "2017/01/19 08:00-12:00 13:00-17:00\n" +
                        "2017/01/20 08:00-12:00 13:00-21:00\n",
                "4\n10\n0\n0\n0\n"
        }
        });
    }

    @Test
    public void testMain() throws Exception {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(this.input.getBytes()));
            Main.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals(this.expected, log.getLog());
    }
}
