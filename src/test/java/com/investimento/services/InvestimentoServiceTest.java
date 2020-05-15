package com.investimento.services;

import com.investimento.enums.RiscoDoInvestimento;
import com.investimento.models.Investimento;
import com.investimento.repositories.InvestimentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.hibernate.ObjectNotFoundException;
import org.springframework.test.util.AssertionErrors;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class InvestimentoServiceTest {
    @MockBean
    InvestimentoRepository investimentoRepository;

    @Autowired
    InvestimentoService investimentoService;
    Investimento investimento;
    Investimento investimento2;
    Investimento investimento3;
    Optional investimentoOptional;

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

        investimento3 = new Investimento();
        investimento3.setIdIvest(2);
        investimento3.setNome("CD");
        investimento3.setDescricao("CDB FLEXIVEL");
        investimento3.setRisco(RiscoDoInvestimento.MEDIO);
        investimento3.setRentabilidade(0.3);

        investimentoOptional = Optional.of(investimento);
    }
    @Test
    public void testarCadastrarInvestimento(){

        Mockito.when(investimentoRepository.save(Mockito.any(Investimento.class))).thenReturn(investimento);

        Investimento investimentoObjeto = investimentoService.cadastrarInvestimento(investimento);

        Assertions.assertEquals(investimento, investimentoObjeto);
    }
    @Test
    public void testarDeletarInvestimento(){
        investimentoService.deletarInvestimento(investimento);
        Mockito.verify(investimentoRepository, Mockito.times(1)).delete(Mockito.any(Investimento.class));
    }
    @Test
    public void testarBuscarTodosInvestimentos(){
        Iterable<Investimento> investimentoIterable = Arrays.asList(investimento,investimento2,investimento3);

        Mockito.when(investimentoRepository.findAll()).thenReturn(investimentoIterable);

        Iterable<Investimento> investimentoIterable2 = investimentoService.buscarTodosInvestimentos();
        Assertions.assertEquals(investimentoIterable, investimentoIterable2);
    }
    @Test
    public void TestarAtualizarInvestimento(){
        Mockito.when(investimentoRepository.save(Mockito.any(Investimento.class))).thenReturn(investimento);

        Investimento investimentoObjeto = investimentoService.atualizarInvestimento(investimento);

        Assertions.assertEquals(investimento, investimentoObjeto);
    }
    @Test
    public void testarBuscarInvestimentoPorId(){

        Mockito.when(investimentoRepository.findById(Mockito.anyInt())).thenReturn(investimentoOptional);

        Optional<Investimento> investimentoOptional2 = investimentoService.buscarPorId(investimento.getIdIvest());
        Assertions.assertEquals(investimentoOptional, investimentoOptional2);
    }

}
