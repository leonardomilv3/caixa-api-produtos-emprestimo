package com.caixa.emprestimo.dto;

public class SimulacaoRequestDTO {
    public Long idProduto;
    public Double valorSolicitado;
    public Integer prazoMeses;

    public Long getIdProduto() {
        return idProduto;
    }

    public Double getValorSolicitado() {
        return valorSolicitado;
    }
    
    public Integer getPrazoMeses() {
        return prazoMeses;
    }

}
