package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Document;

@Stateless
public class DocumentBean {

    @EJB
    private UserBean userBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    @PersistenceContext
    private EntityManager em;

    public Document create(String filepath, String filename, Long id) {
        var occurrence = occurrenceBean.findOrFail(id);
        var document = new Document(filepath, filename, occurrence);

        em.persist(document);
        occurrence.addDocument(document);

        return document;
    }

    public Document find(Long id) {
        return em.find(Document.class, id);
    }

    public Document findOrFail(Long id) {
        var document = em.getReference(Document.class, id);
        Hibernate.initialize(document);
        return document;
    }

    public List<Document> getUserDocuments(Long nif){
        return em.createNamedQuery("getUserDocuments", Document.class).setParameter("nif", nif).getResultList();
    }

    public List<Document> getOccorrenceDocuemnts(Long id){
        return em.createNamedQuery("getOccurrenceDocuments", Document.class).setParameter("ID", id).getResultList();
    }

}
