package PetAppBackend.config;

import PetAppBackend.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@Component
@SessionScope
public class UserSession {
    private Long id;
    private String username;


    public boolean isUserLogged() {
        if (id == null) {
            return false;
        }
        return true;
    }

    public void logOut() {
        this.id = null;
        this.username = null;
    }

    public void login(User user) {
        System.out.println("Logging in user: "+user);
        this.id = user.getId();
        this.username = user.getUsername();
        System.out.println("UserSession updated: ID= "+this.id+"Username: "+this.username);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}