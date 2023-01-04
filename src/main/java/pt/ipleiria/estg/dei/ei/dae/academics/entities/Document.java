package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "documents")
@NamedQuery(
    name = "getOccurrenceDocuments",
    query = "SELECT doc FROM Document doc WHERE doc.occurrence.code = :code"
)

@Data
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String filepath;

    @NotNull
    private String filename;

    @ManyToOne
    private Occurrence occurrence;

    public Document(String filepath, String filename, Occurrence occurrence) {
        this.filepath = filepath;
        this.filename = filename;
        this.occurrence = occurrence;
    }


}