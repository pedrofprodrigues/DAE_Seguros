package pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums;

public enum RepairState {
    waiting("Waiting for approval"),
    progress("Repair in progress"),
    completed("Repair completed"),
    delivered("Delivered"),
    rejected("Rejected"),
    closed("Closed");

    private final String label;

    RepairState(String label) {
        this.label = label;
    }
}
