### Example Apache Ant Project Structure

We have a demo multoproject to demonstrate how the different loggers work. The project contains 5 modules which depends on each other. The modules _sun_ and _water_ has no dependencies to other modules. The module _plant_ depends on _sun_ and _water_. Module _herbivore_ deends on _plant_ and _carnivore_ depends on _herbivore_.

    sun, water
       \  /
       plant
         |
     herbivore
         |
     carnivore

This project has a target _test_ that depends on _compile_, _jar_, _compile-test_, _unit-jar_.