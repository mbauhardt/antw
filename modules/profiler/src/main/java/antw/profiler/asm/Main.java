package antw.profiler.asm;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Main {

    public static void premain(String args, Instrumentation instrumentation) throws IOException {
        instrumentation.addTransformer(new ClassTransformer());
    }
}
