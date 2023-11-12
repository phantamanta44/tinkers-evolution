package xyz.phanta.tconevo.coremod.util;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nullable;

public abstract class MethodRewriter extends MethodVisitor {

    private final MethodVisitor downstream;
    private final MethodNode methodNode;

    private MethodRewriter(int api, MethodVisitor downstream, MethodNode methodNode) {
        super(api, methodNode);
        this.downstream = downstream;
        this.methodNode = methodNode;
    }

    public MethodRewriter(int api, MethodVisitor downstream,
                          int access, String name, String desc, String signature, String[] exceptions) {
        this(api, downstream, new MethodNode(api, access, name, desc, signature, exceptions));
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        rewrite(methodNode);
        methodNode.accept(downstream);
    }

    protected abstract void rewrite(MethodNode method);

    @Nullable
    public static AbstractInsnNode walkForwards(@Nullable AbstractInsnNode start, int steps) {
        for (; steps > 0 && start != null; steps--) {
            start = start.getNext();
        }
        return start;
    }

}
