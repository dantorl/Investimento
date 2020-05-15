package com.investimento.controllers;

import com.investimento.models.Investimento;
import com.investimento.services.InvestimentoService;
import org.aspectj.bridge.IMessage;
import org.hibernate.ObjectNotFoundException;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @GetMapping
    public Iterable<Investimento> buscarTodosInvestimento() {
        return investimentoService.buscarTodosInvestimentos();
    }

    @GetMapping("/{id}")
    public Investimento buscarInvestimentoPorId(@PathVariable Integer id) {
        Optional<Investimento> investimentoOptional;
        try{
        investimentoOptional = investimentoService.buscarPorId(id);
        return investimentoOptional.get();
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Investimento> cadastrarLead(@RequestBody @Valid Investimento investimento) {

        Investimento investimentoObjeto = investimentoService.cadastrarInvestimento(investimento);
        return ResponseEntity.status(201).body(investimentoObjeto);
    }

    @DeleteMapping("/{id}")
    public Investimento deletarInvestimento(@PathVariable Integer id) {
        Optional<Investimento> investimentoOptional;
        try{
            investimentoOptional = investimentoService.buscarPorId(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
            investimentoService.deletarInvestimento(investimentoOptional.get());
            return investimentoOptional.get();
    }

    @PutMapping("/{id}")
    public Investimento atualizarInvestimento(@PathVariable Integer id, @RequestBody @Valid Investimento investimento) {

        investimento.setIdIvest(id);
        Optional<Investimento> investimentoOptional;
        try{
            investimentoOptional = investimentoService.buscarPorId(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        Investimento investimentoObjeto = investimentoService.atualizarInvestimento(investimento);
        return investimentoObjeto;
    }
}
