package pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums;

public enum InsuredObject {

    house("House"),
    car("Car"),
    appliance("Appliance"),
    medical("Medical"),
    extended_warranty("Extended Warranty");

    private final String label;

    InsuredObject(String label) {
        this.label = label;
    }
}
