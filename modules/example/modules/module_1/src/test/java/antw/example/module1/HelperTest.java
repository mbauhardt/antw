package antw.example.module1;

import junit.framework.TestCase;

import org.junit.Test;

public class HelperTest extends TestCase {

    @Test
    public void testTimestamp() throws Exception {
        Helper helper = new Helper();
        assertNotNull(helper.getTimestamp());
    }
}
