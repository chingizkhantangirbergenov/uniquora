package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current_courses")
public class CurrentCourse {

    @Id
    public Integer id;

    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "course_id")
    public Integer courseId;

    @Column(name = "expected_grade")
    public String expectedGrade;

    @Column(name = "credit_")
    public Integer credit;

}
