package com.caixa.emprestimo.resource;

import com.caixa.emprestimo.dto.BadRequestDTO;
import com.caixa.emprestimo.dto.SimulacaoRequestDTO;
import com.caixa.emprestimo.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @POST
    public Response simular(SimulacaoRequestDTO request) {
        Object result = simulacaoService.simular(request);
        if (result instanceof BadRequestDTO erro) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
        }
        return Response.ok(result).build();
    }
}
