package ada.caixa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends PanacheEntity {
    public String nome;
    @Column(unique = true)
    public String email;
    public String senha;
    public String role;

    public static Usuario findByEmail(String email) {
            return find("email", email).firstResult();
    }
}
