package antw.profiler.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodAdapter extends MethodVisitor {

    private String _className;
    private String _methodName;

    public MethodAdapter(MethodVisitor methodVisitor, String className, String methodName) {
        super(Opcodes.ASM4, methodVisitor);
        _className = className;
        _methodName = methodName;
    }

    @Override
    public void visitCode() {
        System.out.println(_className + "." + _methodName);
        this.visitLdcInsn(_className);
        this.visitLdcInsn(_methodName);
        this.visitMethodInsn(Opcodes.INVOKESTATIC, "antw/profiler/Profiler", "start",
                "(Ljava/lang/String;Ljava/lang/String;)V");
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode) {
        switch (opcode) {
        case Opcodes.ARETURN:
        case Opcodes.DRETURN:
        case Opcodes.FRETURN:
        case Opcodes.IRETURN:
        case Opcodes.LRETURN:
        case Opcodes.RETURN:
        case Opcodes.ATHROW:
            this.visitLdcInsn(_className);
            this.visitLdcInsn(_methodName);
            this.visitMethodInsn(Opcodes.INVOKESTATIC, "antw/profiler/Profiler", "end",
                    "(Ljava/lang/String;Ljava/lang/String;)V");
            break;
        default:
            break;
        }
        super.visitInsn(opcode);
    }

}
