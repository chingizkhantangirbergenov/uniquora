package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    public Integer id;
    public Integer userId;
    public Integer courseId;
    public String feedback;
    public Date created_at;

}
