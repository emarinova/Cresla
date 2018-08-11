package cresla.entities.modules;

import cresla.interfaces.Module;

public abstract class ModuleImpl implements Module {
    private int id;

    protected ModuleImpl(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s Module - %d", this.getClass().getSimpleName(), getId()))
                .append(System.lineSeparator());
        return sb.toString();
    }
}
