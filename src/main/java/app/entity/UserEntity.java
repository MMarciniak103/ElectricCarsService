package app.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "users")
public class UserEntity implements Serializable {


    public enum Role{
        ADMIN,NORMAL
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name ="pswd")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;




    public UserEntity() {}

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
