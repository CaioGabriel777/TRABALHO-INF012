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

import com.inf012.inventory.dto.produto.ProdutoDto;
import com.inf012.inventory.dto.produto.ProdutoResponseDto;
import com.inf012.inventory.dto.produto.ProdutoUpdateDto;
import com.inf012.inventory.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoResponseDto> buscarPorId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoService.buscarPorId(produtoId));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> cadastrar(@RequestBody @Valid ProdutoDto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrarProduto(produto));
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<ProdutoResponseDto> atualizar(@PathVariable Long idProduto,
            @RequestBody @Valid ProdutoUpdateDto produto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(idProduto, produto));
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> remover(@PathVariable Long idProduto) {
        produtoService.removerProduto(idProduto);
        return ResponseEntity.noContent().build();
    }
}
