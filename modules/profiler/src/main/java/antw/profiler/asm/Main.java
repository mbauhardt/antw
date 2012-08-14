package antw.profiler.asm;

import java.lang.instrument.Instrumentation;

public class Main {

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ClassTransformer());
    }
}
