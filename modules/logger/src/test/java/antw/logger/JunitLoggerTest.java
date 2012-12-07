package antw.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JunitLoggerTest {

    private JunitLogger _junitLogger = new JunitLogger();
    private ByteArrayOutputStream _stream;

    @Before
    public void setup() {
	_stream = new ByteArrayOutputStream();
	_junitLogger.setOutputPrint(new PrintStream(_stream));
    }

    @After
    public void after() throws IOException {
	_stream.close();
    }

    @Test
    public void test() {
	_junitLogger
		.printTestCase("    [testcase]             foo   1 sec, 101 ms   FAST            PASSED");
	byte[] byteArray = _stream.toByteArray();
	Assert.assertEquals("null,foo,1101,FAST,PASSED", new String(byteArray));
    }

}
