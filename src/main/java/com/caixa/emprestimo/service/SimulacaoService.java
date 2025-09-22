package com.caixa.emprestimo.service;

import com.caixa.emprestimo.dto.BadRequestDTO;
import com.caixa.emprestimo.dto.MemoriaParcelaDTO;
import com.caixa.emprestimo.dto.SimulacaoRequestDTO;
import com.caixa.emprestimo.dto.SimulacaoResponseDTO;
import com.caixa.emprestimo.entity.Produto;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;
    private static final int SCALE_CURRENCY = 2;

    public Object simular(SimulacaoRequestDTO requestDTO) {
        
        // validações
        if (requestDTO.getIdProduto() == null) {
            return new BadRequestDTO("é obrigatório informar o idProduto");
        }
        if (requestDTO.getValorSolicitado() == null || requestDTO.getValorSolicitado() <= 0) {
            return new BadRequestDTO("valor solicitado deve ser maior que zero");
        }
        if (requestDTO.getPrazoMeses() == null || requestDTO.getPrazoMeses() <= 0) {
            return new BadRequestDTO("prazo em meses deve ser maior que zero");
        }

        Produto produto = Produto.findById(requestDTO.getIdProduto());
        if (produto == null) {
            return new BadRequestDTO("produto com id " + requestDTO.getIdProduto() + " não foi encontrado no sistema");
        }

        if (requestDTO.getPrazoMeses() > produto.prazoMaximoMeses) {
            return new BadRequestDTO("prazo solicitado (" + requestDTO.getPrazoMeses()
                    + " meses) excede o prazo máximo permitido (" + produto.prazoMaximoMeses + " meses)");
        }

        BigDecimal valorSolicitado = BigDecimal.valueOf(requestDTO.getValorSolicitado());
        int prazoMeses = requestDTO.getPrazoMeses();

        // taxa anual armazenada em porcentagem → converter para fração
        BigDecimal taxaAnual = BigDecimal.valueOf(produto.taxaJurosAnual)
                .divide(BigDecimal.valueOf(100), 10, ROUNDING);

        MathContext mc = new MathContext(16);
        BigDecimal taxaMensal = pow(BigDecimal.ONE.add(taxaAnual, mc), BigDecimal.valueOf(1.0 / 12.0), mc)
                .subtract(BigDecimal.ONE, mc);


        // Price: A = P * i / (1 - (1+i)^-n)
        BigDecimal i = taxaMensal;
        int n = prazoMeses;
        BigDecimal numerator = valorSolicitado.multiply(i, mc);
        BigDecimal denom = BigDecimal.ONE.subtract(pow(BigDecimal.ONE.add(i, mc), BigDecimal.valueOf(-n), mc), mc);
        BigDecimal parcela = numerator.divide(denom, SCALE_CURRENCY + 6, ROUNDING)
                .setScale(SCALE_CURRENCY, ROUNDING);
            

        // memória
        BigDecimal saldo = valorSolicitado.setScale(SCALE_CURRENCY, ROUNDING);
        List<MemoriaParcelaDTO> memoria = new ArrayList<>();
        BigDecimal totalPago = BigDecimal.ZERO;

        for (int mes = 1; mes <= n; mes++) {
            BigDecimal saldoInicial = saldo;

            BigDecimal juros = saldoInicial.multiply(i, mc).setScale(SCALE_CURRENCY, ROUNDING);
            BigDecimal amortizacao = parcela.subtract(juros).setScale(SCALE_CURRENCY, ROUNDING);

            if (mes == n) {
                amortizacao = saldoInicial;
                parcela = juros.add(amortizacao).setScale(SCALE_CURRENCY, ROUNDING);
            }

            saldo = saldoInicial.subtract(amortizacao).setScale(SCALE_CURRENCY, ROUNDING);

            memoria.add(new MemoriaParcelaDTO(mes, saldoInicial, juros, amortizacao, saldo));
            totalPago = totalPago.add(parcela);
        }

        SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO();
        responseDTO.setProduto(produto);
        responseDTO.setValorSolicitado(valorSolicitado);
        responseDTO.setPrazoMeses(prazoMeses);
        responseDTO.setTaxaJurosEfetivaMensal(taxaMensal.setScale(5, ROUNDING));
        responseDTO.setParcelaMensal(parcela);
        responseDTO.setValorTotalComJuros(totalPago.setScale(SCALE_CURRENCY, ROUNDING));
        responseDTO.setMemoriaCalculo(memoria);

        return responseDTO;
    }

    private BigDecimal pow(BigDecimal base, BigDecimal exponent, MathContext mc) {
        return new BigDecimal(Math.pow(base.doubleValue(), exponent.doubleValue()), mc);
    }
}


