package org.theplaceholder.dmsm.client.vortex;

public enum VortexType {
    Y1976("1976"),
    Y1980("1980"),
    Y2005("2005"),
    Y2005_BLUE("2005_blue"),
    CUSTOM("custom"),
    DEFAULT("default"),
    EDBLUE("edblue"),
    STARS("stars"),
    WHITE("white"),
    NONE("none");

    public Vortex vortex;
    VortexType(String name) {
        vortex = new Vortex(name);
    }
}
