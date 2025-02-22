package programmerzamannow.jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private String id;

    @Embedded
    private Name name;

    @Column(name = "email")
    private String email;

    @ElementCollection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @Column(name = "name")
    private List<String> hobbies;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
