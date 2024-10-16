package com.itb.inf2fm.itech.controller;
import com.itb.inf2fm.itech.exceptions.BadRequest;
import com.itb.inf2fm.itech.model.Tecnico;
import com.itb.inf2fm.itech.services.TecnicoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/Tecnicos")
public class TecnicoController {
    private final TecnicoService tecnicoService;

    @Autowired
    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/listar/todos")
    public ResponseEntity<List<Tecnico>> listarTodosTecnicos() {
        return ResponseEntity.ok().body(tecnicoService.listarTodosTecnicos());
    }

    @GetMapping("/listar/{Cpf_Tecnico}")
    public ResponseEntity<Tecnico> listarTecnicoPorID(@PathVariable(value = "Cpf_Tecnico") String id) {
        try {
            return ResponseEntity.ok().body(tecnicoService.buscarTecnicoPorId(Long.parseLong(id)));

        }catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 30.");
        }

    }
    @PostMapping("/salvar/tecnico")
    @Transactional
    public ResponseEntity<Tecnico> salvarTecnico(@RequestBody Tecnico tecnico) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/Tecnicos/salvar/tecnico" + tecnico.getCpf_Tecnico()).toUriString());
        return ResponseEntity.created(uri).body(tecnicoService.salvarTecnico(tecnico));
    }

    @PutMapping("/atualizar/{Cpf_Tecnico}")
    @Transactional
    public ResponseEntity<Tecnico> atualizarTecnico(@RequestBody Tecnico tecnico, @PathVariable (value = "Cpf_Tecnico") String id) {
        try {
            return ResponseEntity.ok().body(tecnicoService.atualizarTecnico(tecnico, Long.parseLong(id)));

        }catch (NumberFormatException ex) {
            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 30.");
        }
    }


    @DeleteMapping("/deletar/{Cpf_Tecnico}")
    @Transactional
    public ResponseEntity<Object> deletarTecnico(@PathVariable(value = "Cpf_Tecnico") String id) {
        try{
            if(tecnicoService.deletarTecnico(Long.parseLong(id))) {
                return ResponseEntity.ok().body("Tecnico com o id " + id + " excluída com sucesso");
            }
        }catch (NumberFormatException ex) {

            throw new BadRequest("'" + id + "' não é um número inteiro válido. Por favor, forneça um valor inteiro, como 30.");
        }

        return ResponseEntity.ok().body("Não foi possível a exclusão da categoria com o id " + id);
    }
}
