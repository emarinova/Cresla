package cresla.entities.containers;

import cresla.interfaces.AbsorbingModule;
import cresla.interfaces.Container;
import cresla.interfaces.EnergyModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class ModuleContainerTest {

    private Container container;

    @Before
    public void setUp() {
        this.container = new ModuleContainer(5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullEnergyModule() {
        this.container.addEnergyModule(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullAbsorbingModule() {
        this.container.addAbsorbingModule(null);
    }

    @Test
    public void addEnergyModule() throws Exception {
        seedEnergyModules();
        AbsorbingModule absorbingModule4 = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule4.getId()).thenReturn(2);
        this.container.addAbsorbingModule(absorbingModule4);

        int expectedResult = 4;

        Class containerClass = ModuleContainer.class;
        Field energyModulesField = containerClass.getDeclaredField("energyModules");
        energyModulesField.setAccessible(true);
        LinkedHashMap<Integer, EnergyModule> energyModules = (LinkedHashMap<Integer, EnergyModule>) energyModulesField.get(this.container);
        int actualResult = energyModules.size();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void addAbsorbingModule() throws Exception {
        seedAbsorbingModules();
        EnergyModule energyModule1 = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule1.getId()).thenReturn(9);
        this.container.addEnergyModule(energyModule1);

        int expectedResult = 4;

        Class containerClass = ModuleContainer.class;
        Field absorbingModulesField = containerClass.getDeclaredField("absorbingModules");
        absorbingModulesField.setAccessible(true);
        LinkedHashMap<Integer, AbsorbingModule> absorbingModules = (LinkedHashMap<Integer, AbsorbingModule>) absorbingModulesField.get(this.container);
        int actualResult = absorbingModules.size();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTotalEnergyOutput() throws Exception {
        seedEnergyModules();
        EnergyModule energyModule = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule.getId()).thenReturn(3);
        Mockito.when(energyModule.getEnergyOutput()).thenReturn(Integer.MAX_VALUE);
        this.container.addEnergyModule(energyModule);

        long expectedResult = 2L * Integer.MAX_VALUE;

        long actualResult = this.container.getTotalEnergyOutput();

        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getTotalHeatAbsorbing() throws Exception {
        seedAbsorbingModules();
        AbsorbingModule absorbingModule = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule.getId()).thenReturn(4);
        Mockito.when(absorbingModule.getHeatAbsorbing()).thenReturn(Integer.MAX_VALUE);
        this.container.addAbsorbingModule(absorbingModule);

        long expectedResult = 2L * Integer.MAX_VALUE;

        long actualResult = this.container.getTotalHeatAbsorbing();

        Assert.assertEquals(expectedResult, actualResult);
    }

    private void seedEnergyModules() {

        EnergyModule energyModule = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule.getId()).thenReturn(8);
        this.container.addEnergyModule(energyModule);
        EnergyModule energyModule1 = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule1.getId()).thenReturn(9);
        this.container.addEnergyModule(energyModule1);
        EnergyModule energyModule2 = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule2.getId()).thenReturn(10);
        this.container.addEnergyModule(energyModule2);
        EnergyModule energyModule3 = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule3.getId()).thenReturn(11);
        this.container.addEnergyModule(energyModule3);
        EnergyModule energyModule5 = Mockito.mock(EnergyModule.class);
        Mockito.when(energyModule5.getId()).thenReturn(1);
        Mockito.when(energyModule5.getEnergyOutput()).thenReturn(Integer.MAX_VALUE);
        this.container.addEnergyModule(energyModule5);
    }

    private void seedAbsorbingModules() {

        AbsorbingModule absorbingModule = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule.getId()).thenReturn(4);
        this.container.addAbsorbingModule(absorbingModule);
        AbsorbingModule absorbingModule1 = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule1.getId()).thenReturn(5);
        this.container.addAbsorbingModule(absorbingModule1);
        AbsorbingModule absorbingModule2 = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule2.getId()).thenReturn(6);
        this.container.addAbsorbingModule(absorbingModule2);
        AbsorbingModule absorbingModule3 = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule3.getId()).thenReturn(7);
        this.container.addAbsorbingModule(absorbingModule3);
        AbsorbingModule absorbingModule4 = Mockito.mock(AbsorbingModule.class);
        Mockito.when(absorbingModule4.getId()).thenReturn(2);
        Mockito.when(absorbingModule4.getHeatAbsorbing()).thenReturn(Integer.MAX_VALUE);
        this.container.addAbsorbingModule(absorbingModule4);
    }

}