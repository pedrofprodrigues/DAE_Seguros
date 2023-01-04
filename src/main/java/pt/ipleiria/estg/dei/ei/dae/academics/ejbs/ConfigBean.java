package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.StudentNotInTheSameSubjectCourseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private ClientBean clientBean;

    @EJB
    private PolicyBean policyBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    @EJB
    private CompanyBean companyBean;

    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() throws StudentNotInTheSameSubjectCourseException {
        System.out.println("Hello Java EE!");


        companyBean.create("niches","sddssdsd@gmail.com");


/*
        occurrenceBean.create(1L, "Desenvolvimento de Aplicações Empresariais", 9119L);
        occurrenceBean.create(2L, "Desenvolvimento de Aplicações Distribuidas", 9119L);
        occurrenceBean.create(3L, "Cálculo II", 9738L);


        administratorBean.create("admin", "admin","admin", "student@my.ipleiria.pt");
*/

    }

}
