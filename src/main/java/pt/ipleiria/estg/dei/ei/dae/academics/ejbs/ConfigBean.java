package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
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
    private UserBean userBean;

    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB()  {
        System.out.println("Hello Java EE!");


     userBean.create("a","password","a");




    }

}