package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;

import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Document;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;

@Stateless
public class DocumentBean {

    @EJB
    private OccurrenceBean occurrenceBean;

    @PersistenceContext
    private EntityManager em;

    public Document create(String filepath, String filename, Long code) {
        Occurrence occurrence = occurrenceBean.findOccurrenceSafe(code);
        Document document = new Document(filepath, filename, occurrence);

        em.persist(document);
        occurrence.addDocument(document);

        return document;
    }

    public Document find(Long id) {
        return em.find(Document.class, id);
    }

    public Document findDocumentSafe(Long id) {
        var document = em.getReference(Document.class, id);
        Hibernate.initialize(document);
        return document;
    }

    public List<Document> getOccurrenceDocuments(Long code){
        return em.createNamedQuery("getOccurrenceDocuments", Document.class).setParameter("code", code).getResultList();
    }

}
