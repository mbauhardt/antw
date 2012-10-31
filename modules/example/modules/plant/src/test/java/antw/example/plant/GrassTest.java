package antw.example.plant;

import junit.framework.Assert;

import org.junit.Test;

import antw.example.sun.Sun;
import antw.example.water.Water;

public class GrassTest {

    private Grass _plant = new Grass(new Sun(13), new Water(22));

    @Test
    public void testThrive() throws Exception {
        Assert.assertTrue(_plant.thrive());
    }
}
