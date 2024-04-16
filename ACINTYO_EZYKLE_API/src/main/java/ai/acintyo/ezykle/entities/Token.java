
package ai.acintyo.ezykle.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(name = "token")
    private String token;

    @Column(name = "is_logged_out")
    private boolean loggedOut;

    @ManyToOne(cascade = CascadeType.PERSIST) // or CascadeType.ALL depending on your requirements
    @JoinColumn(name = "user_id")
    private EzUserRegistration user;
 
    public Integer getId() {
        return tokenId;
    }

    public void setId(Integer id) {
        this.tokenId = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isLoggedOut() {
        return loggedOut;
    }

    public void setLoggedOut(boolean loggedOut) {
        this.loggedOut = loggedOut;
    }

    public EzUserRegistration getUser() {
        return user;
    }

    public void setUser(EzUserRegistration user) {
        this.user = user;
    }
}
