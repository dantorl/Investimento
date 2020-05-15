package com.investimento.models;

import javax.validation.constraints.*;

public class Simulacao {

    @NotNull
    @Min(1)
    private Integer idIvest;

    @NotNull
    @Min(1)
    private Integer qtdMeses;

    @NotNull
    @DecimalMin("100.00")
    private double vlrAplicacao;

    public Simulacao() {
}

    public Simulacao(@NotNull @Min(1) Integer idIvest, @NotNull @Min(1) Integer qtdMeses, @NotNull @DecimalMin("100.00") double vlrAplicacao) {
        this.idIvest = idIvest;
        this.qtdMeses = qtdMeses;
        this.vlrAplicacao = vlrAplicacao;
    }

    public Integer getIdIvest() {
        return idIvest;
    }

    public void setIdIvest(Integer idIvest) {
        this.idIvest = idIvest;
    }

    public Integer getQtdMeses() {
        return qtdMeses;
    }

    public void setQtdMeses(Integer qtdMeses) {
        this.qtdMeses = qtdMeses;
    }

    public double getVlrAplicacao() {
        return vlrAplicacao;
    }

    public void setVlrAplicacao(double vlrAplicacao) {
        this.vlrAplicacao = vlrAplicacao;
    }
}
