package foo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProfilerTest {

    public static void main(String[] args) {

        Logger.getAnonymousLogger().log(Level.INFO, "hello");
        Bar bar = new Bar();
        bar.setName("foobar");
        bar.getName();
    }

}
