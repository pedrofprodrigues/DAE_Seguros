package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceDTO implements Serializable {
    private Long code;

    private String name;

    private Long policyCode;

    private String courseName;

    private String courseYear;

    private String scholarYear;

    public OccurrenceDTO() {
    }

    public OccurrenceDTO(Long code, String name, Long policyCode, String courseName, String courseYear, String scholarYear) {
        this.code = code;
        this.name = name;
        this.policyCode = policyCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(Long policyCode) {
        this.policyCode = policyCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
    }

    public static OccurrenceDTO from(Occurrence occurrence) {
        return new OccurrenceDTO(
                occurrence.getCode(),
                occurrence.getName(),
                occurrence.getPolicy().getCode(),
                occurrence.getPolicy().getName(),
                occurrence.getCourseYear(),
                occurrence.getScholarYear()
        );
    }

    public static List<OccurrenceDTO> from(List<Occurrence> occurrences) {
        return occurrences.stream().map(OccurrenceDTO::from).collect(Collectors.toList());
    }
}
