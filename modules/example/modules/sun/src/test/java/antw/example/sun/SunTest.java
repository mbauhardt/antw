package antw.example.sun;

import junit.framework.Assert;

import org.junit.Test;

public class SunTest {

    private Sun _sun = new Sun(6);

    @Test
    public void testSunrise() throws Exception {
        Assert.assertTrue(_sun.sunrise());
    }

    @Test
    public void testSunset() throws Exception {
        Assert.assertFalse(_sun.sunset());
    }

    @Test
    public void testSunshine() throws Exception {
        Assert.assertFalse(_sun.sunshine());
    }

}
