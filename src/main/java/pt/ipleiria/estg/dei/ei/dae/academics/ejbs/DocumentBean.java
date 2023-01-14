package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import java.io.*;
import java.util.Arrays;
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

    public Document create(String filepath, String filename, Long occurrenceCode) {
        Occurrence occurrence = occurrenceBean.findOccurrenceSafe(occurrenceCode);
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
        return em.createNamedQuery("getOccurrenceDocuments", Document.class).setParameter("id", code).getResultList();
    }


    public void importOccurrencesFromCSV(byte[] bytes){
        BufferedReader reader = null;
        try {
            InputStream is = new ByteArrayInputStream(bytes);
            reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");


                System.out.println("\n\n\n\n "+Long.parseLong(values[0])+"\n\n\n\n ");

                occurrenceBean.create(Long.parseLong(values[0]), values[1], values[2]);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("CSV file imported successfully!");
    }

}
