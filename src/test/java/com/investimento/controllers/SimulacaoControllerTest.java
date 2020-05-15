package com.investimento.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investimento.enums.RiscoDoInvestimento;
import com.investimento.models.Investimento;
import com.investimento.models.RespSimulacao;
import com.investimento.models.Simulacao;
import com.investimento.services.InvestimentoService;
import com.investimento.services.SimulacaoService;
import org.hamcrest.CoreMatchers;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(SimulacaoController.class)
public class SimulacaoControllerTest {
    @MockBean
    SimulacaoService simulacaoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();
    Simulacao simulacao;
    RespSimulacao respSimulacao;
    Investimento investimento;

    @BeforeEach
    public void inicializar() {
        simulacao = new Simulacao();
        simulacao.setIdIvest(1);
        simulacao.setQtdMeses(10);
        simulacao.setVlrAplicacao(170.00);

        respSimulacao = new RespSimulacao();
        respSimulacao.setResultadoSimulacao(179.80);

        investimento = new Investimento();
        investimento.setIdIvest(1);
        investimento.setNome("CDB ITAU");
        investimento.setDescricao("ESSE EH BOM");
        investimento.setRisco(RiscoDoInvestimento.ALTO);
        investimento.setRentabilidade(0.9);
    }
    @Test
    public void testarSimularInvestimento() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(simulacaoService.criarSimulacao(Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(respSimulacao);
        Mockito.when(simulacaoService.buscarPorId(Mockito.anyInt())).thenReturn(investimentoOptional);

        String json = mapper.writeValueAsString(simulacao);

        mockMvc.perform(MockMvcRequestBuilders.put("/simulacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultadoSimulacao", CoreMatchers.equalTo(179.8)));
    }
    @Test
    public void testarSimularInvestimentoErro() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(simulacaoService.criarSimulacao(Mockito.anyInt(), Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(respSimulacao);
        Mockito.when(simulacaoService.buscarPorId(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("","Nao localizado o Código de investimento informado"));

        String json = mapper.writeValueAsString(simulacao);

        mockMvc.perform(MockMvcRequestBuilders.put("/simulacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
