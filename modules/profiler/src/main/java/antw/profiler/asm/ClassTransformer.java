package antw.profiler.asm;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class ClassTransformer implements ClassFileTransformer {

    private Properties _defaultProperties = new Properties();
    private Properties _dynamicProperties = new Properties();

    public ClassTransformer() throws IOException {
        // load default
        _defaultProperties.load(this.getClass().getResourceAsStream("/antw-transform.properties"));

        String dynamicPropertyFile = System.getProperty("antw-transform.properties");
        if (dynamicPropertyFile != null) {
            _dynamicProperties.load(new FileInputStream(dynamicPropertyFile));
        }
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        if (loader != ClassLoader.getSystemClassLoader()) {
            return classfileBuffer;
        }

        if (!canTransform(className)) {
            return classfileBuffer;
        }
        return transform(className, classfileBuffer);
    }

    private boolean canTransform(String className) {
        if (include(className)) {
            return true;
        } else if (exclude(className)) {
            return false;
        }
        return true;
    }

    private boolean exclude(String className) {
        return exclude(className, _defaultProperties) || exclude(className, _dynamicProperties);
    }

    private boolean exclude(String className, Properties properties) {
        if (properties.containsKey("antw.transform.excludes")) {
            String excludes = (String) properties.get("antw.transform.excludes");
            String[] classNames = excludes.split(",");
            for (String excludeClassName : classNames) {
                if (className.startsWith(excludeClassName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean include(String className) {
        return include(className, _defaultProperties) || include(className, _dynamicProperties);
    }

    private boolean include(String className, Properties properties) {
        if (properties.containsKey("antw.transform.includes")) {
            String includes = (String) properties.get("antw.transform.includes");
            String[] classNames = includes.split(",");
            for (String includeClassName : classNames) {
                if (className.startsWith(includeClassName)) {
                    return true;
                }
            }
        }
        return false;

    }

    private byte[] transform(String className, byte[] classfileBuffer) {
        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classVisitor = new ClassAdapter(writer, className);
        reader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        return writer.toByteArray();
    }

}
