package com.caixa.emprestimo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Produto extends PanacheEntity {
    public String nome;
    public Double taxaJurosAnual; 
    public Integer prazoMaximoMeses;

    public Produto() {}

    public Produto(String nome, Double taxaJurosAnual, Integer prazoMaximoMeses) {
        this.nome = nome;
        this.taxaJurosAnual = taxaJurosAnual;
        this.prazoMaximoMeses = prazoMaximoMeses;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }

    public void setTaxaJurosAnual(Double taxaJurosAnual) {
        this.taxaJurosAnual = taxaJurosAnual;
    }

    public Integer getPrazoMaximoMeses() {
        return prazoMaximoMeses;
    }

    public void setPrazoMaximoMeses(Integer prazoMaximoMeses) {
        this.prazoMaximoMeses = prazoMaximoMeses;
    }
}

