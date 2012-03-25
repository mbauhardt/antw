package antw.example.module1;

import junit.framework.TestCase;

import org.junit.Test;

public class ModuleOneTest extends TestCase {

    @Test
    public void testToString() throws Exception {
        ModuleOne moduleOne = new ModuleOne();
        assertEquals("module1", moduleOne.toString());
    }

    @Test
    public void testHashCode() throws Exception {
        ModuleOne moduleOne = new ModuleOne();
        assertEquals("module1".hashCode(), moduleOne.hashCode());
    }
}
