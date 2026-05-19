package ada.caixa.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Produto extends PanacheEntity {
    public String nome;
    public String descricao;
    public Double preco;
    public int estoque;
}
