package xyz.phanta.tconevo.trait.base;

public interface EnergeticModifier {

    default EnergyType getEnergyType() {
        return EnergyType.FORGE_ENERGY;
    }

    enum EnergyType {

        FORGE_ENERGY, EU

    }

}
