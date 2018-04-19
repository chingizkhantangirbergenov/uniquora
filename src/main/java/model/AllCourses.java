package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "all_courses")
public class AllCourses {

    @Id
    public Integer id;

    @Column(name = "abbr_")
    public String abbr;

    @Column(name = "name_")
    public String name;

}
