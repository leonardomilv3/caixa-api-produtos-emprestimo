package com.caixa.emprestimo.resource;

import com.caixa.emprestimo.dto.BadRequestDTO;
import com.caixa.emprestimo.entity.Produto;
import com.caixa.emprestimo.service.ProdutoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    public List<Produto> listAll() {
        return produtoService.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Object result = produtoService.buscarPorId(id);
        if (result instanceof BadRequestDTO erro) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
        }
        return Response.ok(result).build();
    }

    @POST
    public Response create(Produto produto) {
        Object result = produtoService.criar(produto);
        if (result instanceof BadRequestDTO erro) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
        }
        return Response.status(Response.Status.CREATED).entity(result).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Produto produto) {
        Object result = produtoService.atualizar(id, produto);
        if (result instanceof BadRequestDTO erro) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
        }
        return Response.ok(result).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Object result = produtoService.excluir(id);
        if (result instanceof BadRequestDTO erro) {
            return Response.status(Response.Status.BAD_REQUEST).entity(erro).build();
        }
        return Response.ok(result).build();
    }
}
