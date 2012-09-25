package antw.example.carnivore;

import antw.example.herbivore.Zebra;
import antw.example.plant.Grass;

public class Lion {

    public boolean survive(Zebra zebra, Grass grass) {
        return zebra.survive(grass);
    }

}
