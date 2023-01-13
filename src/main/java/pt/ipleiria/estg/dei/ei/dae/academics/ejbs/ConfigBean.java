package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;


import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;

import static pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject.house;

@Startup
@Singleton
public class ConfigBean {


    @EJB
    private ExpertBean expertBean;


  @EJB
    private OccurrenceBean occurrenceBean;


  @EJB
    private PolicyAPIBean policyAPIBean;


  @EJB
    private RepairServiceBean repairServiceBean;



    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB()  {
 ;


     expertBean.create("a","password","a");
     repairServiceBean.create("as","as",house);
     occurrenceBean.create(1L,"joao","2",1L,OccurrenceState.opened);

    }

}