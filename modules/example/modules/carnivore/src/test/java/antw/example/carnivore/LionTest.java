package antw.example.carnivore;

import junit.framework.Assert;

import org.junit.Test;

import antw.example.herbivore.Zebra;
import antw.example.plant.Grass;
import antw.example.sun.Sun;
import antw.example.water.Water;

public class LionTest {

    private Grass _grass = new Grass(new Sun(13), new Water(22));
    private Zebra _zebra = new Zebra();
    private Lion _lion = new Lion();

    @Test
    public void testEatZebra() throws Exception {
        Assert.assertTrue(_lion.survive(_zebra, _grass));
    }
}
