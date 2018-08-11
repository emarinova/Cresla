package cresla.entities.reactors;

import cresla.entities.containers.ModuleContainer;
import cresla.interfaces.Container;

public class CryoReactor extends ReactorImpl {
    private int cryoProductionIndex;

    public CryoReactor(int id, Container moduleContainer, int cryoProductionIndex) {
        super(id, moduleContainer);
        this.cryoProductionIndex = cryoProductionIndex;
    }

    @Override
    public long getTotalEnergyOutput() {
        long energyOutput = super.getTotalEnergyOutput() * this.cryoProductionIndex;

        if (energyOutput > super.getTotalHeatAbsorbing()) {
            return 0L;
        }
        return energyOutput;
    }
}
