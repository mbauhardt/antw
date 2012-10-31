package antw.example.herbivore;

import junit.framework.Assert;

import org.junit.Test;

import antw.example.plant.Grass;
import antw.example.sun.Sun;
import antw.example.water.Water;

public class ZebraTest {

    private Grass _grass = new Grass(new Sun(12), new Water(22));
    private Zebra _zebra = new Zebra();

    @Test
    public void testSurvive() throws Exception {
        Assert.assertTrue(_zebra.survive(_grass));
    }
}
