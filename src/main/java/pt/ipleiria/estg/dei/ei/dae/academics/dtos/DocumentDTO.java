package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Document;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocumentDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private String filename;


    public static DocumentDTO from(Document document) {
        return new DocumentDTO(document.getId(), document.getFilename());
    }

    public static List<DocumentDTO> from(List<Document> documents) {
        return documents.stream().map(DocumentDTO::from).collect(Collectors.toList());
    }
}
