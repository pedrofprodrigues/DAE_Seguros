package pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums;

public enum OccurrenceState {
    opened("Opened"),
    in_analysis("In Analysis"),
    accepted("Accepted"),
    in_repair("In Repair"),
    repaired("Repaired"),
    delivered("Delivered"),
    rejected("Rejected"),
    closed("Closed");

    private final String label;

    OccurrenceState(String label) {
        this.label = label;
    }
}
