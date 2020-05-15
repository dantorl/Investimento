package com.investimento.services;

import com.investimento.models.Investimento;
import com.investimento.repositories.InvestimentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired
    private InvestimentoRepository investimentoRepository;

    public Iterable<Investimento> buscarTodosInvestimentos() {
        Iterable<Investimento> investimentos = investimentoRepository.findAll();
        return investimentos;
    }

    public Optional<Investimento> buscarPorId(Integer id) {
        Optional<Investimento> investimentoOptional = investimentoRepository.findById(id);
        if (investimentoOptional.isPresent()) {
            return investimentoOptional;
        } else {
            throw new ObjectNotFoundException("","Nao localizado o Código de investimento informado");
        }
      }

    public Investimento cadastrarInvestimento(Investimento investimento) {
        Investimento investimentoObjeto = investimentoRepository.save(investimento);
        return investimentoObjeto;
    }

    public void deletarInvestimento(Investimento investimento) {
        investimentoRepository.delete(investimento);
    }

    public Investimento atualizarInvestimento(Investimento investimento) {
            Investimento investimentoObjeto = investimentoRepository.save(investimento);
            return investimentoObjeto;
    }
}
