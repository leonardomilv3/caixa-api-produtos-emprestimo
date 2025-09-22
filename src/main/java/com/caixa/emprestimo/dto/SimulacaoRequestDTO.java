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

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public void setValorSolicitado(Double valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

}
