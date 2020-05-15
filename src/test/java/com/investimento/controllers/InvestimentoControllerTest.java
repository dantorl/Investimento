package com.investimento.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investimento.enums.RiscoDoInvestimento;
import com.investimento.models.Investimento;
import com.investimento.services.InvestimentoService;
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

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(InvestimentoController.class)
public class InvestimentoControllerTest {

    @MockBean
    InvestimentoService investimentoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    Investimento investimento;

    Investimento investimento2;


    @BeforeEach
    public void inicializar() {
        investimento = new Investimento();
        investimento.setIdIvest(1);
        investimento.setNome("CDB ITAU");
        investimento.setDescricao("ESSE EH BOM");
        investimento.setRisco(RiscoDoInvestimento.ALTO);
        investimento.setRentabilidade(0.9);

        investimento2 = new Investimento();
        investimento2.setIdIvest(2);
        investimento2.setNome("CDB FLEX");
        investimento2.setDescricao("CDB FLEXIVEL");
        investimento2.setRisco(RiscoDoInvestimento.MEDIO);
        investimento2.setRentabilidade(0.3);
    }
    @Test
    public void testarCadastrarInvestimento() throws Exception {

        Mockito.when(investimentoService.cadastrarInvestimento(Mockito.any(Investimento.class))).thenReturn(investimento);

        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("CDB ITAU")));
    }
    @Test
    public void testarCadastrarInvestimentoErro() throws Exception {

        Mockito.when(investimentoService.cadastrarInvestimento(Mockito.any(Investimento.class))).thenReturn(investimento);
        investimento.setNome(null);
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testarBuscarInvestimento() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(investimentoOptional);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/investimentos/" + investimento.getIdIvest()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idIvest", CoreMatchers.equalTo(1)));
    }
    @Test
    public void testarBuscarInvestimentoErro() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("","Nao localizado o Código de investimento informado"));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/investimentos/" + investimento.getIdIvest()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testarBuscarTodosInvestimentos() throws Exception {

        Iterable<Investimento> investimentoIterable = Arrays.asList(investimento, investimento2);

        Mockito.when(investimentoService.buscarTodosInvestimentos()).thenReturn(investimentoIterable);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/investimentos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].idIvest", CoreMatchers.equalTo(2)));
    }
    @Test
    public void testarBuscarTodosInvestimentosVazio() throws Exception {

        Iterable<Investimento> investimentoIterable = Arrays.asList();

        Mockito.when(investimentoService.buscarTodosInvestimentos()).thenReturn(investimentoIterable);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/investimentos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
    @Test
    public void testarDeleteInvestimento() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(investimentoOptional);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/investimentos/"+investimento.getIdIvest()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.idIvest", CoreMatchers.equalTo(1)));
        Mockito.verify(investimentoService, Mockito.times(1)).deletarInvestimento(Mockito.any(Investimento.class));

    }
    @Test
    public void testarDeleteInvestimentoErro() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("","Nao localizado o Código de investimento informado"));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/investimentos/"+investimento.getIdIvest()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(investimentoService, Mockito.times(0)).deletarInvestimento(Mockito.any(Investimento.class));

    }
    @Test
    public void testarAtualizarInvestimento() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        investimento.setNome("Teste Investimento");

        Mockito.when(investimentoService.atualizarInvestimento(Mockito.any(Investimento.class))).thenReturn(investimento);
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(investimentoOptional);

        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.put("/investimentos/"+investimento.getIdIvest())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.equalTo("Teste Investimento")));
    }
    @Test
    public void testarAtualizarInvestimentoErro() throws Exception {
        Optional<Investimento> investimentoOptional = Optional.of(investimento);

        investimento.setNome("Teste Investimento");

        Mockito.when(investimentoService.atualizarInvestimento(Mockito.any(Investimento.class))).thenReturn(investimento);
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("","Nao localizado o Código de investimento informado"));

        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.put("/investimentos/"+investimento.getIdIvest())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
}
