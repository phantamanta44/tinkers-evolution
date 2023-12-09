package xyz.phanta.tconevo.coremod.util;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class InsnWriter {

    private final InsnList insns = new InsnList();

    public InsnList asList() {
        return insns;
    }

    public InsnWriter put(AbstractInsnNode insn) {
        insns.add(insn);
        return this;
    }

    public InsnWriter putAll(InsnList insnList) {
        insns.add(insnList);
        return this;
    }

    // variable instructions

    public InsnWriter putVarInsn(int opcode, int varIndex) {
        return put(new VarInsnNode(opcode, varIndex));
    }

    public InsnWriter aload(int varIndex) {
        return putVarInsn(Opcodes.ALOAD, varIndex);
    }

    public InsnWriter astore(int varIndex) {
        return putVarInsn(Opcodes.ASTORE, varIndex);
    }

    // field instructions

    public InsnWriter putFieldInsn(int opcode, String owner, String name, String desc) {
        return put(new FieldInsnNode(
                opcode, owner, FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(owner, name, desc), desc));
    }

    public InsnWriter putfield(String owner, String name, String desc) {
        return putFieldInsn(Opcodes.PUTFIELD, owner, name, desc);
    }

    public InsnWriter getfield(String owner, String name, String desc) {
        return putFieldInsn(Opcodes.GETFIELD, owner, name, desc);
    }

    // method instructions

    public InsnWriter putMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        return put(new MethodInsnNode(
                opcode, owner, FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc), desc, itf));
    }

    public InsnWriter invokestatic(String owner, String name, String desc) {
        return putMethodInsn(Opcodes.INVOKESTATIC, owner, name, desc, false);
    }

    public InsnWriter invokevirtual(String owner, String name, String desc) {
        return putMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, desc, false);
    }

    // type instructions

    public InsnWriter putTypeInsn(int opcode, String typeDesc) {
        return put(new TypeInsnNode(opcode, typeDesc));
    }

    public InsnWriter checkcast(String typeDesc) {
        return putTypeInsn(Opcodes.CHECKCAST, typeDesc);
    }

    // jump instructions

    public InsnWriter putJumpInsn(int opcode, LabelNode target) {
        return put(new JumpInsnNode(opcode, target));
    }

    public InsnWriter ifnonnull(LabelNode target) {
        return putJumpInsn(Opcodes.IFNONNULL, target);
    }

}
