package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "all_users")
public class AllUsers {

    @Id
    public Integer id;

    @Column(name = "fullname_")
    public String fullName;

    @Column(name = "email_")
    public String email;

    @Column(name = "id_number_")
    public String idNumber;

    @Column(name = "school_")
    public String school;

}
