package cresla.managers;


import cresla.entities.containers.ModuleContainer;
import cresla.entities.modules.AbsorbingModuleImpl;
import cresla.entities.modules.EnergyModuleImpl;
import cresla.entities.reactors.CryoReactor;
import cresla.entities.reactors.HeatReactor;
import cresla.interfaces.*;
import cresla.interfaces.Module;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class ManagerImpl implements Manager {
    private int id;
    private Map<Integer, cresla.interfaces.Module> modules;
    private Map<Integer, Reactor> reactors;
    private OutputWriter writer;

    public ManagerImpl(int id, Map<Integer, cresla.interfaces.Module> modules, Map<Integer, Reactor> reactors, OutputWriter writer) {
        this.id = id;
        this.modules = modules;
        this.reactors = reactors;
        this.writer = writer;
    }

    @Override
    public String reactorCommand(List<String> arguments) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> reactorClass = Class.forName("cresla.entities.reactors." + arguments.get(0) + "Reactor");
        Constructor constructor = reactorClass.getDeclaredConstructor(int.class, Container.class, int.class);
        Reactor reactor = (Reactor) constructor.newInstance(this.getNextId(), new ModuleContainer(Integer.parseInt(arguments.get(2))), Integer.parseInt(arguments.get(1)));
        this.reactors.put(reactor.getId(), reactor);
        return String.format("Created %s Reactor - %d", arguments.get(0), reactor.getId());
    }

    @Override
    public String moduleCommand(List<String> arguments) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int reactorId = Integer.parseInt(arguments.get(0));
        String type = arguments.get(1);
        int additionalParameter = Integer.parseInt(arguments.get(2));
        Class<?> moduleClass = Class.forName("cresla.entities.modules." + type);
        Constructor constructor = moduleClass.getDeclaredConstructor(int.class, int.class);
        Module module = (Module) constructor.newInstance(this.getNextId(), additionalParameter);
        this.modules.put(module.getId(), module);
            switch (type.toLowerCase()) {
                case "cooldownsystem":
                case "heatprocessor":
                    this.reactors.get(reactorId).addAbsorbingModule((AbsorbingModule) module);
                    break;
                case "cryogenrod":
                    this.reactors.get(reactorId).addEnergyModule((EnergyModule) module);
                    break;
            }
        return String.format("Added %s - %d to Reactor - %d", type, module.getId(), reactorId);
    }

    @Override
    public String reportCommand(List<String> arguments) {
        int id = Integer.parseInt(arguments.get(0));
        if (this.modules.containsKey(id)) {
            return this.modules.get(id).toString();
        }
        if (this.reactors.containsKey(id)) {
            return this.reactors.get(id).toString();
        }
        return "Error";
    }

    @Override
    public String exitCommand(List<String> arguments) {
        int cryoReactors = 0;
        int heatReactors = 0;

        for (Map.Entry<Integer, Reactor> integerReactorEntry : reactors.entrySet()) {
            if (integerReactorEntry.getValue() instanceof HeatReactor) {
                heatReactors++;
            }
            if (integerReactorEntry.getValue() instanceof CryoReactor) {
                cryoReactors++;
            }
        }

        int energyModules = 0;
        int absorbingModules = 0;

        for (Map.Entry<Integer, Module> integerModuleEntry : modules.entrySet()) {
            if (integerModuleEntry.getValue() instanceof EnergyModuleImpl) {
                energyModules++;
            }
            if(integerModuleEntry.getValue() instanceof AbsorbingModuleImpl) {
                absorbingModules++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Cryo Reactors: %d", cryoReactors))
                .append(System.lineSeparator())
                .append(String.format("Heat Reactors: %d", heatReactors))
                .append(System.lineSeparator())
                .append(String.format("Energy Modules: %d", energyModules))
                .append(System.lineSeparator())
                .append(String.format("Absorbing Modules: %d", absorbingModules))
                .append(System.lineSeparator())
                .append(String.format("Total Energy Output: %d", this.reactors.entrySet().stream().mapToLong(r -> r.getValue().getTotalEnergyOutput()).sum()))
                .append(System.lineSeparator())
                .append(String.format("Total Heat Absorbing: %d", this.reactors.entrySet().stream().mapToLong(r -> r.getValue().getTotalHeatAbsorbing()).sum()))
                .append(System.lineSeparator());
        return sb.toString();
    }

    private int getNextId() {
        return this.id++;
    }
}
