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
        return em.createNamedQuery("getOccurrenceDocuments", Document.class).setParameter("code", code).getResultList();
    }


    public void importOccurrencesFromCSV(byte[] file){

        BufferedReader reader = null;

        InputStream is = new ByteArrayInputStream(file);

        try {
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while (true) {

                if (!((line = reader.readLine()) != null)) break;
                String[] values = line.split(",");

                occurrenceBean.create(Long.getLong(values[0]), values[1], values[2]);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("CSV file imported successfully!");

    }

}
