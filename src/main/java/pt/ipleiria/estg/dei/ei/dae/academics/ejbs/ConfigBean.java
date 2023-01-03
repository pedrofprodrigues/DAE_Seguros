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

    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() throws StudentNotInTheSameSubjectCourseException {
        System.out.println("Hello Java EE!");

        administratorBean.create("admin", "admin", "admin", "admin@academics.pt");

        policyBean.create(9119L, "EI");
        policyBean.create(9281L, "EE");
        policyBean.create(9823L, "EM");
        policyBean.create(9822L, "EA");
        policyBean.create(9738L, "ET");

        occurrenceBean.create(1L, "Desenvolvimento de Aplicações Empresariais", "2017/18", "2021/22", 9119L);
        occurrenceBean.create(2L, "Desenvolvimento de Aplicações Distribuidas", "2017/18", "2021/22", 9119L);
        occurrenceBean.create(3L, "Cálculo II", "2017/18", "2021/22", 9738L);


        clientBean.create("student", "secret", "student", "student@my.ipleiria.pt", 9119L);

        clientBean.enroll("student", 1L);
    }

}
