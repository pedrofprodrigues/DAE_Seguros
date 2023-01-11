package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    @EJB
    private RepairServiceBean repairServiceBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB()  {
        System.out.println("Hello Java EE!");


        InsuredObject insuredObject = InsuredObject.car;
        OccurrenceState occurrenceState = OccurrenceState.accepted;

        RepairService repairService =  new RepairService("Teste", "Teste2", insuredObject);

        occurrenceBean.create(1L, "Worten", repairService,  occurrenceState);
        repairServiceBean.create("Teste", "test2", insuredObject, 1L);



    }

}
