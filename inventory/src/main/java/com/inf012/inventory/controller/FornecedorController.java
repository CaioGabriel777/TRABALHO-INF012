package com.inf012.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inf012.inventory.dto.fornecedor.FornecedorDto;
import com.inf012.inventory.dto.fornecedor.FornecedorResponseDto;
import com.inf012.inventory.dto.fornecedor.FornecedorUpdateDto;
import com.inf012.inventory.service.FornecedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDto>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<FornecedorResponseDto> cadastrar(@RequestBody @Valid FornecedorDto fornecedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.cadastrar(fornecedor));
    }

    @PutMapping("/{idFornecedor}")
    public ResponseEntity<FornecedorResponseDto> atualizar(@PathVariable Long idFornecedor,
            @RequestBody @Valid FornecedorUpdateDto fornecedor) {
        return ResponseEntity.ok(fornecedorService.atualizar(idFornecedor, fornecedor));
    }

    @DeleteMapping("/{idFornecedor}")
    public ResponseEntity<Void> remover(@PathVariable Long idFornecedor) {
        fornecedorService.remover(idFornecedor);
        return ResponseEntity.noContent().build();
    }
}
