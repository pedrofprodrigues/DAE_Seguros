package pt.ipleiria.estg.dei.ei.dae.academics.entities;

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
        query = "SELECT doc FROM Document doc WHERE doc.occurrence.id = :id"
)
/*
@NamedQuery(
        name = "getOccurrenceDocuments",
        query = "SELECT doc FROM Document doc WHERE 1 = 1"
))*/
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String filepath;

    @NotNull
    private String filename;

    @ManyToOne
    private User user;
    @ManyToOne
    private Occurrence occurrence;

    public Document() {
    }

    public Document(String filepath, String filename, Occurrence occurrence) {
        this.filepath = filepath;
        this.filename = filename;
        this.occurrence = occurrence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Occurrence getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Occurrence occurrence) {
        this.occurrence = occurrence;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}