package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;


import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;

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

        occurrenceBean.findOccurrenceSafe(5L).setOccurrenceState(OccurrenceState.accepted);

        occurrenceBean.findOccurrenceSafe(5L).setRepairCompany(repairCompanyBean.find(2L));
        occurrenceBean.findOccurrenceSafe(6L).setRepairCompany(repairCompanyBean.find(2L));
        occurrenceBean.findOccurrenceSafe(7L).setRepairCompany(repairCompanyBean.find(1L));
        occurrenceBean.findOccurrenceSafe(8L).setRepairCompany(repairCompanyBean.find(1L));
        occurrenceBean.findOccurrenceSafe(4L).setRepairCompany(repairCompanyBean.find(3L));



    }

}