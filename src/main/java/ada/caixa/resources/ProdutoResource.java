package ada.caixa.resources;

import ada.caixa.dto.ErroResponse;
import ada.caixa.entity.Produto;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    @RolesAllowed({"USER", "ADMIN"})
    public List<Produto> listar() {
        return Produto.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"USER", "ADMIN"})
    public Response buscarPorId(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);

        if (produto == null) {
            return Response.status(404)
                    .entity(new ErroResponse("Produto não encontrado"))
                    .build();
        }

        return  Response.ok(produto).build();
    }

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response cadastrar(Produto produto) {
        if(produto.nome == null || produto.descricao == null || produto.preco == null) {
            return Response.status(400)
                    .entity(new ErroResponse("Campos obrigatórios não informados"))
                    .build();
        }

        produto.persist();

        return Response.status(201)
                .entity(produto)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response atualizar(@PathParam("id") Long id, Produto dados) {

        if(dados.nome == null || dados.descricao == null || dados.preco == null) {
            return  Response.status(400)
            .entity(new ErroResponse("Campos obrigatórios não informados"))
            .build();
        }

        Produto produto = Produto.findById(id);

        if(produto == null) {
            return Response.status(404)
                    .entity(new ErroResponse("Produto não encontrado"))
                    .build();
        }

        produto.nome = dados.nome;
        produto.descricao = dados.descricao;
        produto.preco = dados.preco;
        produto.estoque = dados.estoque;

        return Response.ok(produto).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public  Response deletar(@PathParam("id") Long id) {
        Produto produto = Produto.findById(id);

        if(produto == null) {
            return Response.status(404)
                    .entity(new ErroResponse("Produto não encontrado"))
                    .build();
        }

        produto.delete();

        return Response.noContent().build();
    }
}
