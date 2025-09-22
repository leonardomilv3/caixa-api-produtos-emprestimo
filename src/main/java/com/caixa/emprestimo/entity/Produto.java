package com.caixa.emprestimo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Produto extends PanacheEntity {
    public String nome;
    public Double taxaJurosAnual; // ex: 0.18
    public Integer prazoMaximoMeses;
    
    public String getNome() {
        return nome;
    }
    
    public Double getTaxaJurosAnual() {
        return taxaJurosAnual;
    }
    
    public Integer getPrazoMaximoMeses() {
        return prazoMaximoMeses;
    }
}

