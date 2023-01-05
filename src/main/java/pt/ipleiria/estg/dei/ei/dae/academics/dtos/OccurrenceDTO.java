package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OccurrenceDTO implements Serializable {
    private Long code;

    private String description;

    private Long policyCode;

    private OccurrenceState occurrenceState;




    public static OccurrenceDTO from(Occurrence occurrence) {
        return new OccurrenceDTO(
                occurrence.getCode(),
                occurrence.getDescription(),
                occurrence.getPolicy().getCode(),
                occurrence.getOccurrenceState()
        );
    }

    public static List<OccurrenceDTO> from(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::from).collect(Collectors.toList());
    }
}
