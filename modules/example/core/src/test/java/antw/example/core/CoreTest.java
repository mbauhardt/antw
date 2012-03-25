package antw.example.core;

import junit.framework.TestCase;

import org.junit.Test;

import antw.example.core.Core;

public class CoreTest extends TestCase {

    @Test
    public void testCore() throws Exception {
        Core core = new Core();
        assertEquals("core", core.toString());
    }
}
