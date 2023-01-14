package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private ExpertBean expertBean;
    @EJB
    private OccurrenceBean occurrenceBean;
    @EJB
    private RepairCompanyBean repairCompanyBean;



    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB()  {

     expertBean.create("a","password","a");
     expertBean.create("b","password1","b");

     repairCompanyBean.create("worten", "worten@worten.com");
     repairCompanyBean.create("lolada","lolada@lolada.com");
     repairCompanyBean.create("fnac","fnac@fnac.com");


     occurrenceBean.create(1L,"joao","2");
     occurrenceBean.create(1L,"joa3o","2");
     occurrenceBean.create(1L,"joa2o","2");
     occurrenceBean.create(2L,"joa3o","3");
     occurrenceBean.create(2L,"joa4o","3");
     occurrenceBean.create(2L,"joa25o","3");

    }

}