package com.caixa.emprestimo.resource;

import com.caixa.emprestimo.entity.Produto;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> listAll() {
        return Produto.listAll();
    }

    @GET
    @Path("/{id}")
    public Produto findById(@PathParam("id") Long id) {
        return Produto.findById(id);
    }

    @POST
    @Transactional
    public Produto create(Produto produto) {
        produto.persist();
        return produto;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Produto update(@PathParam("id") Long id, Produto produto) {
        Produto entity = Produto.findById(id);
        entity.nome = produto.nome;
        entity.taxaJurosAnual = produto.taxaJurosAnual;
        entity.prazoMaximoMeses = produto.prazoMaximoMeses;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Produto entity = Produto.findById(id);
        entity.delete();
    }
}
