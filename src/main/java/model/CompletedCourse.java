package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "completed_courses")
public class CompletedCourse {

    @Id
    public Integer id;

    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "course_id")
    public Integer courseId;

    @Column(name = "credit_")
    public Integer credit;

    @Column(name = "grade_")
    public String grade;

}
