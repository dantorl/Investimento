package com.investimento.repositories;

import com.investimento.models.Investimento;
import org.springframework.data.repository.CrudRepository;

public interface InvestimentoRepository extends CrudRepository<Investimento, Integer> {
}
