package dbService.data;

import javax.persistence.*;

@Entity
@Table(name="users")
public class UserData {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="login", unique = true)
    private String login;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;

    public UserData(){}

    public UserData(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
