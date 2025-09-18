package com.caixa.emprestimo.dto;

import java.math.BigDecimal;

public class MemoriaParcelaDTO {
    public int mes;
    public BigDecimal juros;
    public BigDecimal amortizacao;
    public BigDecimal parcela;
    public BigDecimal saldoDevedor;

    public MemoriaParcelaDTO(int mes, BigDecimal juros, BigDecimal amortizacao, BigDecimal parcela, BigDecimal saldoDevedor) {
        this.mes = mes;
        this.juros = juros;
        this.amortizacao = amortizacao;
        this.parcela = parcela;
        this.saldoDevedor = saldoDevedor;
    }
}
