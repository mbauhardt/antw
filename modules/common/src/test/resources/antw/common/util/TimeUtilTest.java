package antw.common.util;

import static org.fest.assertions.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimeUtilTest {

    @Test
    public void test10ms() throws Exception {
	String duration = TimeUtil.formatTimeDuration(TimeUnit.MILLISECONDS
		.toMillis(10));
	assertThat(duration).isEqualTo("10 ms");
    }

    @Test
    public void testDuration_1h_12m_30sec_77ms() throws Exception {
	long millis = TimeUnit.HOURS.toMillis(1);
	millis = millis + TimeUnit.MINUTES.toMillis(12);
	millis = millis + TimeUnit.SECONDS.toMillis(30);
	millis = millis + TimeUnit.MILLISECONDS.toMillis(77);
	String duration = TimeUtil.formatTimeDuration(millis);
	assertThat(duration).isEqualTo("1 hrs, 12 mins, 30 sec, 77 ms");
	assertThat(TimeUtil.convertTimeDuration(duration)).isEqualTo(millis);
    }

}
