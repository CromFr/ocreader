package email.schaal.ocreader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Date;

import email.schaal.ocreader.util.StringUtils;

import static org.junit.Assert.assertEquals;

/**
 * Test class for StringUtils
 */
@RunWith(RobolectricTestRunner.class)
public class StringUtilsTest {
    @Test
    public void testGetByLine() throws Exception {
        assertEquals("From testFeed", StringUtils.getByLine(RuntimeEnvironment.application, "testFeed", null));
        assertEquals("By testAuthor from testFeed", StringUtils.getByLine(RuntimeEnvironment.application, "testFeed", "testAuthor"));
    }

    @Test
    public void testGetTimeSpanString() throws Exception {
        Date testDateStart = new Date(1469849100000L);

        Date testDateMinute = new Date(1469849100000L + 60 * 1000);
        Date testDateHour = new Date(1469849100000L + 60 * 60 * 1000);
        Date testDateDay = new Date(1469849100000L + 24 * 60 * 60 * 1000);

        assertEquals("now", StringUtils.getTimeSpanString(RuntimeEnvironment.application, testDateStart, testDateStart));
        assertEquals("1m", StringUtils.getTimeSpanString(RuntimeEnvironment.application, testDateStart, testDateMinute));
        assertEquals("1h", StringUtils.getTimeSpanString(RuntimeEnvironment.application, testDateStart, testDateHour));
        assertEquals("1d", StringUtils.getTimeSpanString(RuntimeEnvironment.application, testDateStart, testDateDay));
    }

    @Test
    public void testCleanString() throws Exception {
        String html = "<span>Test</span>";
        String entity = "Test &gt; Test";

        assertEquals("Test", StringUtils.cleanString(html));
        assertEquals("Test > Test", StringUtils.cleanString(entity));
    }
}