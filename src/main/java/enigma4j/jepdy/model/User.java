package enigma4j.jepdy.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class User extends PanacheEntity {

    public String login;
    public boolean admin;
    public boolean host;

    public static User findByLogin(String login){
        return find("login", login).firstResult();
    }

}
