package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Stateless
public class EmailBean {

    @Resource(name = "java:/jboss/mail/fakeSMTP")
    private Session mailSession;

    public void send(String to, String subject, String text) throws MessagingException {
        var msg = new MimeMessage(mailSession);

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));


        msg.setSubject(subject);

        msg.setText(text);

        var timeStamp = new Date();
        msg.setSentDate(timeStamp);

        Transport.send(msg);
    }
}
