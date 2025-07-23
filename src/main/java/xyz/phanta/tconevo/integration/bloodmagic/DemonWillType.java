package xyz.phanta.tconevo.integration.bloodmagic;

import xyz.phanta.tconevo.client.util.IntegrationLocal;

@IntegrationLocal
public enum DemonWillType {

    RAW,
    CORROSIVE,
    DESTRUCTIVE,
    VENGEFUL,
    STEADFAST;

    public static final DemonWillType[] VALUES = values();

}
