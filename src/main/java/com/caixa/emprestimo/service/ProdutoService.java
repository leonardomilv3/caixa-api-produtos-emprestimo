package com.caixa.emprestimo.service;

import com.caixa.emprestimo.dto.BadRequestDTO;
import com.caixa.emprestimo.entity.Produto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProdutoService {

    public List<Produto> listarTodos() {
        return Produto.listAll();
    }

    public Object buscarPorId(Long id) {
        Produto produto = Produto.findById(id);
        if (produto == null) {
            return new BadRequestDTO("produto com id " + id + " não foi encontrado no sistema");
        }
        return produto;
    }

    @Transactional
    public Object criar(Produto produto) {
        if (produto.nome == null || produto.nome.isBlank()) {
            return new BadRequestDTO("o nome do produto é obrigatório");
        }
        if (produto.taxaJurosAnual == null || produto.taxaJurosAnual <= 0) {
            return new BadRequestDTO("a taxa de juros anual deve ser maior que zero");
        }
        if (produto.prazoMaximoMeses == null || produto.prazoMaximoMeses <= 0) {
            return new BadRequestDTO("o prazo máximo deve ser maior que zero");
        }
        produto.persist();
        return produto;
    }

    @Transactional
    public Object atualizar(Long id, Produto produto) {
        Produto entity = Produto.findById(id);
        if (entity == null) {
            return new BadRequestDTO("produto com id " + id + " não foi encontrado no sistema");
        }
        entity.nome = produto.nome;
        entity.taxaJurosAnual = produto.taxaJurosAnual;
        entity.prazoMaximoMeses = produto.prazoMaximoMeses;
        return entity;
    }

    @Transactional
    public Object excluir(Long id) {
        Produto entity = Produto.findById(id);
        if (entity == null) {
            return new BadRequestDTO("produto com id " + id + " não foi encontrado no sistema");
        }
        entity.delete();
        return "Produto removido com sucesso!";
    }
}
