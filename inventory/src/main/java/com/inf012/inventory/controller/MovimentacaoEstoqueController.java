package com.inf012.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inf012.inventory.dto.movimentacao.MovimentacaoDto;
import com.inf012.inventory.dto.movimentacao.MovimentacaoResponseDto;
import com.inf012.inventory.service.MovimentacaoEstoqueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService estoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping("/movimentacoes")
    public ResponseEntity<MovimentacaoResponseDto> registrarMovimentacao(@RequestBody @Valid MovimentacaoDto estoque) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.registrarMovimentacao(estoque));
    }
}
