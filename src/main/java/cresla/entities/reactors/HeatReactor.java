package cresla.entities.reactors;

import cresla.entities.containers.ModuleContainer;
import cresla.interfaces.Container;

public class HeatReactor extends ReactorImpl {
    private int heatReductionIndex;

    public HeatReactor(int id, Container moduleContainer, int heatReductionIndex) {
        super(id, moduleContainer);
        this.heatReductionIndex = heatReductionIndex;
    }

    @Override
    public long getTotalHeatAbsorbing() {
        return super.getTotalHeatAbsorbing() + this.heatReductionIndex;
    }
}
