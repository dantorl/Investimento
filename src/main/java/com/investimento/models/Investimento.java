package com.investimento.models;

import com.investimento.enums.RiscoDoInvestimento;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "Investimento")
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIvest;

    @Size(min = 3, max = 100, message = "O nome deve ter no minimo 4 caracteres e no maximo 100")
    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    private RiscoDoInvestimento risco;

    @NotNull
    @DecimalMin("0.1")
    private double rentabilidade;

    public Investimento() {
    }

    public Investimento(@Size(min = 4, max = 100, message = "O nome deve ter no minimo 4 caracteres e no maximo 100") @NotNull String nome, @NotNull String descricao, @NotNull RiscoDoInvestimento risco, @NotNull @DecimalMin("0.1") double rentabilidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.risco = risco;
        this.rentabilidade = rentabilidade;
    }

    public Investimento(Integer idIvest) {
        this.idIvest = idIvest;
    }

    public Integer getIdIvest() {
        return idIvest;
    }

    public void setIdIvest(Integer idIvest) {
        this.idIvest = idIvest;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public RiscoDoInvestimento getRisco() {
        return risco;
    }

    public void setRisco(RiscoDoInvestimento risco) {
        this.risco = risco;
    }

    public double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }
}
