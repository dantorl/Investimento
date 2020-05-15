package com.investimento.services;

import com.investimento.enums.RiscoDoInvestimento;
import com.investimento.models.Investimento;
import com.investimento.models.RespSimulacao;
import com.investimento.models.Simulacao;
import com.investimento.repositories.InvestimentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class SimulacaoServiceTest {

    @MockBean
    InvestimentoRepository investimentoRepository;

    @Autowired
    SimulacaoService simulacaoService;

    Simulacao simulacao;
    RespSimulacao respSimulacao;
    Investimento investimento;
    Optional investimentoOptional;

    @BeforeEach
    public void inicializar() {
        simulacao = new Simulacao();
        simulacao.setIdIvest(1);
        simulacao.setQtdMeses(10);
        simulacao.setVlrAplicacao(100.00);

        respSimulacao = new RespSimulacao();
        respSimulacao.setResultadoSimulacao(110.35774776577495);

        investimento = new Investimento();
        investimento.setIdIvest(1);
        investimento.setNome("CDB ITAU");
        investimento.setDescricao("ESSE EH BOM");
        investimento.setRisco(RiscoDoInvestimento.ALTO);
        investimento.setRentabilidade(0.9);

        investimentoOptional = Optional.of(investimento);
    }
    @Test
    public void testarBuscarInvestimentoPorId(){

        Mockito.when(investimentoRepository.findById(Mockito.anyInt())).thenReturn(investimentoOptional);

        Optional<Investimento> investimentoOptional2 = simulacaoService.buscarPorId(investimento.getIdIvest());
        Assertions.assertEquals(investimentoOptional, investimentoOptional2);
    }
    @Test
    public void testarCriarSimulacao(){
        RespSimulacao respSimulacao1 = simulacaoService.criarSimulacao(simulacao.getQtdMeses(), simulacao.getVlrAplicacao(), investimento.getRentabilidade());
        Assertions.assertEquals(respSimulacao1.getResultadoSimulacao(), respSimulacao.getResultadoSimulacao());
    }

}
