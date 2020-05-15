package com.investimento.controllers;

import com.investimento.models.Investimento;
import com.investimento.models.RespSimulacao;
import com.investimento.models.Simulacao;
import com.investimento.services.InvestimentoService;
import com.investimento.services.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/simulacao")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @PutMapping
    public RespSimulacao simularInvestimento(@RequestBody @Valid Simulacao simulacao) {

        Optional<Investimento> investimentoOptional;
        try{
            investimentoOptional = simulacaoService.buscarPorId(simulacao.getIdIvest());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }

        Investimento investimento = investimentoOptional.get();
        RespSimulacao respSimulacao = simulacaoService.criarSimulacao(simulacao.getQtdMeses(), simulacao.getVlrAplicacao(), investimento.getRentabilidade());
        return respSimulacao;
    }
}
