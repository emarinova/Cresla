package cresla.entities.reactors;


import cresla.interfaces.*;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

public abstract class ReactorImpl implements Reactor {
    private int id;
    private Container moduleContainer;

    protected ReactorImpl(int id, Container moduleContainer) {
        this.id = id;
        this.moduleContainer = moduleContainer;
    }



    @Override
    public long getTotalHeatAbsorbing() {
        return this.moduleContainer.getTotalHeatAbsorbing();
    }

    @Override
    public int getModuleCount() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> moduleContainerClass = Class.forName("cresla.entities.containers.ModuleContainer");
        Field containerCapacity = moduleContainerClass.getDeclaredField("modulesByInput");
        containerCapacity.setAccessible(true);
        List<cresla.interfaces.Module> modules = (List<cresla.interfaces.Module>) containerCapacity.get(this.moduleContainer);
        return modules.size();
    }

    @Override
    public void addEnergyModule(EnergyModule energyModule) {
        this.moduleContainer.addEnergyModule(energyModule);
    }

    @Override
    public void addAbsorbingModule(AbsorbingModule absorbingModule) {
        this.moduleContainer.addAbsorbingModule(absorbingModule);
    }

    @Override
    public long getTotalEnergyOutput() {
        long energyOutput = this.moduleContainer.getTotalEnergyOutput();
        if (energyOutput > this.getTotalHeatAbsorbing()) {
            return 0L;
        }
        return energyOutput;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        try {
            sb.append(String.format("%s - %d", this.getClass().getSimpleName(), this.getId()))
                    .append(System.lineSeparator())
                    .append(String.format("Energy Output: %d", this.getTotalEnergyOutput()))
                    .append(System.lineSeparator())
                    .append(String.format("Heat Absorbing: %d", this.getTotalHeatAbsorbing()))
                    .append(System.lineSeparator())
                    .append(String.format("Modules: %d", this.getModuleCount()))
                    .append(System.lineSeparator());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
