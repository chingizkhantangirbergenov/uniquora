package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current_courses")
public class Syllabus {

    @Id
    public Integer id;

    @Column(name = "current_course_id")
    public Integer currentCourseId;

    @Column(name = "title")
    public String title;

    @Column(name = "weight")
    public Integer weight;

    @Column(name = "grade")
    public Integer grade;

    @Column(name = "contribution")
    public Integer contribution;

}
