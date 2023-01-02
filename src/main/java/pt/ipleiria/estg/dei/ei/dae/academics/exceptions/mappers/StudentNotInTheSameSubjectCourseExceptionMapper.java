package pt.ipleiria.estg.dei.ei.dae.academics.exceptions.mappers;

import pt.ipleiria.estg.dei.ei.dae.academics.dtos.ErrorDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.ClientBean;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.OccurrenceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.StudentNotInTheSameSubjectCourseException;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class StudentNotInTheSameSubjectCourseExceptionMapper implements ExceptionMapper<StudentNotInTheSameSubjectCourseException> {
    @EJB
    private ClientBean clientBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    @Override
    public Response toResponse(StudentNotInTheSameSubjectCourseException e) {
        var student = clientBean.findClientSafe(e.getStudentUsername());
        var subject = occurrenceBean.findOccurrenceSafe(e.getSubjectCode());

        var error = new ErrorDTO(
                Client.class.getSimpleName() + " '" + student.getName() + "'" +
                " can't enroll in " +
                Occurrence.class.getSimpleName() + " '" + subject.getName() + "'. " +
                "Different course."
        );

        return Response.status(Response.Status.FORBIDDEN).entity(error).build();
    }
}
