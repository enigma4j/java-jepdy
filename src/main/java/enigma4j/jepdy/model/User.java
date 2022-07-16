package enigma4j.jepdy.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User extends PanacheEntity {

    public String login;
    public boolean admin;
    public boolean host;

    public static User findByLogin(String login){
        return find("login", login).firstResult();
    }

}
