package com.caixa.emprestimo.dto;

import java.math.BigDecimal;
import java.util.List;
import com.caixa.emprestimo.entity.Produto;

public class SimulacaoResponseDTO {

    private Produto produto;
    private BigDecimal valorSolicitado;
    private int prazoMeses;
    private BigDecimal taxaJurosEfetivaMensal;
    private BigDecimal valorTotalComJuros;
    private BigDecimal parcelaMensal;
    private List<MemoriaParcelaDTO> memoriaCalculo;

    public Produto getProduto() { 
        return produto; 
    }

    public void setProduto(Produto produto) { 
        this.produto = produto; 
    }

    public BigDecimal getValorSolicitado() { 
        return valorSolicitado; 
    }
    public void setValorSolicitado(BigDecimal valorSolicitado) { 
        this.valorSolicitado = valorSolicitado; 
    }

    public int getPrazoMeses() { 
        return prazoMeses; 
    }

    public void setPrazoMeses(int prazoMeses) { 
        this.prazoMeses = prazoMeses; 
    }

    public BigDecimal getTaxaJurosEfetivaMensal() { 
        return taxaJurosEfetivaMensal; 
    }
    public void setTaxaJurosEfetivaMensal(BigDecimal taxaJurosEfetivaMensal) { 
        this.taxaJurosEfetivaMensal = taxaJurosEfetivaMensal; 
    }

    public BigDecimal getValorTotalComJuros() { 
        return valorTotalComJuros; 
    }
    public void setValorTotalComJuros(BigDecimal valorTotalComJuros) { 
        this.valorTotalComJuros = valorTotalComJuros; 
    }

    public BigDecimal getParcelaMensal() { 
        return parcelaMensal; 
    }
    public void setParcelaMensal(BigDecimal parcelaMensal) { 
        this.parcelaMensal = parcelaMensal; 
    }

    public List<MemoriaParcelaDTO> getMemoriaCalculo() { 
        return memoriaCalculo; 
    }
    public void setMemoriaCalculo(List<MemoriaParcelaDTO> memoriaCalculo) { 
        this.memoriaCalculo = memoriaCalculo; 
    }
}
