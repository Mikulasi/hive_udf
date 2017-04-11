package com.epam.custom_udf;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringParserUDFTest {

    private StringParserUDF parser;
    private static final String IE9 = "Internet Explorer 9";
    private static final String IE7 = "Internet Explorer 7";
    private static final String CHROME24 = "Chrome 24";
    private static final String SAFARI5 = "Safari 5";
    private static final String COMPUTER = "Computer";
    private static final String TABLET = "Tablet";
    private static final String IOS6IPAD = "iOS 6 (iPad)";
    private static final String W7 = "Windows 7";
    private static final String WXP = "Windows XP";

    @Before
    public void setUp() {
        parser = new StringParserUDF();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_evaluate() {
        String example1 = "Mozilla/5.0(iPad; U;CPU OS 6_1 like Mac OS X; zh-CN; iPad3,3) AppleWebKit/534.46 (KHTML, like Gecko) UCBrowser/2.0.1.280 U3/0.8.0 Safari/7543.48.3";
        String example2 = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.57 Safari/537.17 SE 2.X MetaSr 1.0";
        String example3 = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0";
        String example4 = "Mozilla/5.0 (compatible; MSIE 9.0;\\Windows NT 6.1; WOW64; Trident/5.0)";
        String browserRes1 = parser.evaluate(Criteria.BROWSER.getParam(), example1).toString();
        String deviceRes1 = parser.evaluate(Criteria.DEVICE.getParam(), example1).toString();
        String osRes1 = parser.evaluate(Criteria.OS.getParam(), example1).toString();

        assertThat(browserRes1, is(SAFARI5));
        assertThat(deviceRes1, is(TABLET));
        assertThat(osRes1, is(IOS6IPAD));

        String browserRes2 = parser.evaluate(Criteria.BROWSER.getParam(), example2).toString();
        String deviceRes2 = parser.evaluate(Criteria.DEVICE.getParam(), example2).toString();
        String osRes2 = parser.evaluate(Criteria.OS.getParam(), example2).toString();

        assertThat(browserRes2, is(CHROME24));
        assertThat(deviceRes2, is(COMPUTER));
        assertThat(osRes2, is(WXP));


        String browserRes3 = parser.evaluate(Criteria.BROWSER.getParam(), example3).toString();
        String deviceRes3 = parser.evaluate(Criteria.DEVICE.getParam(), example3).toString();
        String osRes3 = parser.evaluate(Criteria.OS.getParam(), example3).toString();

        assertThat(browserRes3, is(IE7));
        assertThat(deviceRes3, is(COMPUTER));
        assertThat(osRes3, is(WXP));

        String browserRes4 = parser.evaluate(Criteria.BROWSER.getParam(), example4).toString();
        String deviceRes4 = parser.evaluate(Criteria.DEVICE.getParam(), example4).toString();
        String osRes4 = parser.evaluate(Criteria.OS.getParam(), example4).toString();

        assertThat(browserRes4, is(IE9));
        assertThat(deviceRes4, is(COMPUTER));
        assertThat(osRes4, is(W7));

        try {
            parser.evaluate("mobile", example1);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Wrong parameter!!! Enter \"os\", \"device\" or \"browser\""));
        }

    }
}