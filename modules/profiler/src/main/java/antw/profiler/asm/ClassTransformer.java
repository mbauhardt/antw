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

    private Properties _properties = new Properties();

    public ClassTransformer() throws IOException {
        // load default
        _properties.load(this.getClass().getResourceAsStream("/antw-transform.properties"));

        String dynamicPropertyFile = System.getProperty("antw-transform.properties");
        if (dynamicPropertyFile != null) {
            Properties dynamicProperties = new Properties();
            dynamicProperties.load(new FileInputStream(dynamicPropertyFile));
            _properties.putAll(dynamicProperties);
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
        String excludes = (String) _properties.get("antw.transform.excludes");
        String[] classNames = excludes.split(",");
        for (String excludeClassName : classNames) {
            if (className.startsWith(excludeClassName)) {
                return true;
            }
        }
        return false;
    }

    private boolean include(String className) {
        String includes = (String) _properties.get("antw.transform.includes");
        String[] classNames = includes.split(",");
        for (String includeClassName : classNames) {
            if (className.startsWith(includeClassName)) {
                return true;
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
