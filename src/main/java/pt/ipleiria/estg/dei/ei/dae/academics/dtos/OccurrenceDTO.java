package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OccurrenceDTO implements Serializable {

    private Long id;
    private Long policyNumber;

    private String description;
    private OccurrenceState occurrenceState;

    private String repairServiceName;

    private String expertNif;


    public static OccurrenceDTO from(Occurrence occurrence) {
        return new OccurrenceDTO(
                occurrence.getId(),
                occurrence.getPolicyNumber(),
                occurrence.getDescription(),
                occurrence.getOccurrenceState(),
                occurrence.getRepairService().getInsuranceCompany(),
                occurrence.getExpertNif()
        );
    }

    public static List<OccurrenceDTO> from(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::from).collect(Collectors.toList());
    }
}