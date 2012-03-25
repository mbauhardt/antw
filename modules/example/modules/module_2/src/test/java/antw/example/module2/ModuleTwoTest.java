package antw.example.module2;

import junit.framework.TestCase;

import org.junit.Test;

public class ModuleTwoTest extends TestCase {

    @Test
    public void testModuleTwo() throws Exception {
        ModuleTwo moduleTwo = new ModuleTwo();
        assertEquals("module2", moduleTwo.toString());
    }
}
