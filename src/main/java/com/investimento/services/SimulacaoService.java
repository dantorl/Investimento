package com.investimento.services;

import com.investimento.models.Investimento;
import com.investimento.models.RespSimulacao;
import com.investimento.repositories.InvestimentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimulacaoService {
    
    @Autowired
    private InvestimentoRepository investimentoRepository;

    public Optional<Investimento> buscarPorId(Integer idIvest) {
        Optional<Investimento> investimentoOptional = investimentoRepository.findById(idIvest);
        if (investimentoOptional.isPresent()) {
            return investimentoOptional;
        } else {
            throw new ObjectNotFoundException("","Nao localizado o Código de investimento informado");
        }
    }

    public RespSimulacao criarSimulacao(Integer qtdMeses, double vlrAplicacao, double rentabilidade) {
        double result = vlrAplicacao;
        for(int i =0; i<= qtdMeses; i++) {
            result += result * (rentabilidade / 100);
        }
        RespSimulacao respSimulacao = new RespSimulacao(result);
        return  respSimulacao;
    }
}
