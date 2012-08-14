package antw.profiler.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassAdapter extends ClassVisitor {

    private String _className;

    public ClassAdapter(ClassVisitor visitor, String className) {
        super(Opcodes.ASM4, visitor);
        _className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, methodName, desc, signature, exceptions);
        return new MethodAdapter(methodVisitor, _className, methodName);
    }

}
