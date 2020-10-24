package xyz.phanta.tconevo.coremod;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import org.objectweb.asm.Opcodes;

class VarTracker {

    private final TIntIntMap mapped = new TIntIntHashMap();
    private int nextFreeVar;

    public VarTracker(int initial) {
        for (int i = 0; i < initial; i++) {
            mapped.put(i, i);
        }
        this.nextFreeVar = initial;
    }

    public int map(int opcode, int var) {
        if (mapped.containsKey(var)) {
            return mapped.get(var);
        }
        int mappedVar;
        switch (opcode) { // operations acting on 2 words eat up two variable slots
            case Opcodes.DLOAD:
            case Opcodes.DSTORE:
            case Opcodes.LLOAD:
            case Opcodes.LSTORE:
                mappedVar = createDouble();
                break;
            default:
                mappedVar = create();
                break;
        }
        mapped.put(var, mappedVar);
        return mappedVar;
    }

    public int create() {
        return nextFreeVar++;
    }

    public int createDouble() {
        int var = nextFreeVar;
        nextFreeVar += 2;
        return var;
    }

}
