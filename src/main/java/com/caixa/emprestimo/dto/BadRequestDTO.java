package com.caixa.emprestimo.dto;

public class BadRequestDTO {
    private String erro;

    public BadRequestDTO() {}

    public BadRequestDTO(String erro) {
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
