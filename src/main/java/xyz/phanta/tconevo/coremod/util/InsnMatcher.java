package xyz.phanta.tconevo.coremod.util;

import io.github.phantamanta44.libnine.util.tuple.IPair;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

// not truly regular expressions because the Kleene closure operator is missing
// we would need to make everything lazy to implement it, which is annoying... but we probably don't need it anyways
@FunctionalInterface
public interface InsnMatcher {

    Collection<InsnMatcher> step(AbstractInsnNode insn);

    static InsnMatcher oneOf(InsnMatcher a, InsnMatcher b) {
        return insn -> {
            List<InsnMatcher> newStates = new ArrayList<>();
            newStates.addAll(a.step(insn));
            newStates.addAll(b.step(insn));
            return newStates;
        };
    }

    static InsnMatcher oneOf(InsnMatcher... matchers) {
        return insn -> {
            List<InsnMatcher> newStates = new ArrayList<>();
            for (InsnMatcher matcher : matchers) {
                newStates.addAll(matcher.step(insn));
            }
            return newStates;
        };
    }

    static InsnMatcher oneOf(Iterable<InsnMatcher> matchers) {
        return insn -> {
            List<InsnMatcher> newStates = new ArrayList<>();
            for (InsnMatcher matcher : matchers) {
                newStates.addAll(matcher.step(insn));
            }
            return newStates;
        };
    }

    default InsnMatcher then(InsnMatcher next) {
        return insn -> {
            List<InsnMatcher> newStates = new ArrayList<>();
            boolean accepted = false;
            for (InsnMatcher newState : step(insn)) {
                if (newState == null) {
                    accepted = true;
                } else {
                    newStates.add(newState.then(next));
                }
            }
            if (accepted) {
                newStates.add(next);
            }
            return newStates;
        };
    }

    static InsnMatcher sequence(InsnMatcher head, InsnMatcher... tail) {
        for (InsnMatcher matcher : tail) {
            head = head.then(matcher);
        }
        return head;
    }

    @Nullable
    default Match match(@Nullable AbstractInsnNode start) {
        AbstractInsnNode insn = start;
        Collection<InsnMatcher> state = Collections.singleton(this);
        while (insn != null && !state.isEmpty()) {
            Collection<InsnMatcher> nextState = new ArrayList<>();
            for (InsnMatcher matcher : state) {
                for (InsnMatcher nextMatcher : matcher.step(insn)) {
                    if (nextMatcher == null) {
                        return new Match(start, insn.getNext());
                    }
                    nextState.add(nextMatcher);
                }
            }
            state = nextState;
            insn = insn.getNext();
        }
        return null;
    }

    @Nullable
    default Match find(@Nullable AbstractInsnNode insns) {
        Collection<IPair<AbstractInsnNode, InsnMatcher>> state = new ArrayList<>();
        while (insns != null) {
            state.add(IPair.of(insns, this));
            Collection<IPair<AbstractInsnNode, InsnMatcher>> nextState = new ArrayList<>();
            for (IPair<AbstractInsnNode, InsnMatcher> partial : state) {
                for (InsnMatcher nextMatcher : partial.getB().step(insns)) {
                    if (nextMatcher == null) {
                        return new Match(partial.getA(), insns.getNext());
                    }
                    nextState.add(IPair.of(partial.getA(), nextMatcher));
                }
            }
            state = nextState;
            insns = insns.getNext();
        }
        return null;
    }

    class Match {

        public final AbstractInsnNode start;
        @Nullable
        public final AbstractInsnNode tail;

        public Match(AbstractInsnNode start, @Nullable AbstractInsnNode tail) {
            this.start = start;
            this.tail = tail;
        }

    }

    InsnMatcher ANY_INSN = insn -> Collections.singleton(null);

    static InsnMatcher anyInsn(int opcode) {
        return insn -> insn.getOpcode() == opcode ? Collections.singleton(null) : Collections.emptyList();
    }

    static <T extends AbstractInsnNode> InsnMatcher matchByType(Class<T> insnType, int opcode, Predicate<T> matcher) {
        return insn -> insn.getOpcode() == opcode && insnType.isInstance(insn) && matcher.test(insnType.cast(insn))
                ? Collections.singleton(null) : Collections.emptyList();
    }

    static InsnMatcher varInsn(int opcode, int varIndex) {
        return matchByType(VarInsnNode.class, opcode, insn -> insn.var == varIndex);
    }

    static InsnMatcher fieldInsn(int opcode, String owner, String name) {
        return matchByType(FieldInsnNode.class, opcode, insn ->
                insn.owner.equals(owner) && insn.name.equals(name));
    }

    static InsnMatcher methodInsn(int opcode, String owner, String name, String desc) {
        return matchByType(MethodInsnNode.class, opcode, insn ->
                insn.owner.equals(owner) && insn.name.equals(name) && insn.desc.equals(desc));
    }

    static InsnMatcher typeInsn(int opcode, String typeName) {
        return matchByType(TypeInsnNode.class, opcode, insn -> insn.desc.equals(typeName));
    }

    static InsnMatcher intInsn(int opcode, int operand) {
        return matchByType(IntInsnNode.class, opcode, insn -> insn.operand == operand);
    }

}
