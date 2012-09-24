### Example Apache Ant Project Structure

The following loggers will attach at runtime to the ant process. We will demonstrate the different loggers with a small ant multiproject build that has two separately modules.

    project
        build.xml
        modules
            module_1
                build.xml
            module_2
                build.xml

This project has a *target* test that depends on compile, compile-test, jar, unit-jar etc. There are two tests in *module_1*  they are passed. There are one test in *module_2* that will fail.

