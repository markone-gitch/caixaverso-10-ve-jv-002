package ada.caixa.exceptions;

import ada.caixa.dto.ErroResponse;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.awt.*;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ErroResponse("Acesso negado"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
