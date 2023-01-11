package pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums;

public enum Cover {

    fire("Fire"),
    steal("Steal"),
    all_cover("All Cover"),
    glass("Glass");

    private final String label;

    Cover(String label) {
        this.label = label;
    }
}
