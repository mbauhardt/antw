package antw.example.plant;

import antw.example.sun.Sun;
import antw.example.water.Water;

public class Grass {

    private Sun _sun;
    private Water _water;

    public Grass(Sun sun, Water water) {
        _sun = sun;
        _water = water;
    }

    public boolean thrive() {
        return _sun.sunshine() && _water.flow();
    }

}
