package antw.example.water;

import junit.framework.Assert;

import org.junit.Test;

public class WaterTest {

    private Water _water = new Water(23);

    @Test
    public void testFlow() throws Exception {
        Assert.assertTrue(_water.flow());
    }

    @Test
    public void testFreeze() throws Exception {
        Assert.assertFalse(_water.freeze());
    }
}
