package ada.caixa.bootstrap;

import ada.caixa.entity.Usuario;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataLoader {

    @Transactional
    void onStart(@Observes StartupEvent event) {
        criarUsuarioSeNaoExistir(
                "Admin Sistema",
                "admin@loja.com",
                "admin123",
                "ADMIN"
        );

        criarUsuarioSeNaoExistir(
                "User Padrão",
                "user@loja.com",
                "user123",
                "USER"
        );

    }

    public void criarUsuarioSeNaoExistir(
            String nome,
            String email,
            String senha,
            String role
    ) {
        if (Usuario.findByEmail(email) == null) {
            Usuario usuario = new Usuario();
            usuario.nome = nome;
            usuario.email = email;
            usuario.senha = BcryptUtil.bcryptHash(senha);
            usuario.role = role;
            usuario.persist();
        }
    }
}
