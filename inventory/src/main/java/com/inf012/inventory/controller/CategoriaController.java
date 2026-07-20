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

import com.inf012.inventory.dto.categoria.CategoriaDto;
import com.inf012.inventory.dto.categoria.CategoriaResponseDto;
import com.inf012.inventory.dto.categoria.CategoriaUpdateDto;
import com.inf012.inventory.mapper.CategoriaMapper;
import com.inf012.inventory.model.Categoria;
import com.inf012.inventory.service.CategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper mapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper mapper) {
        this.categoriaService = categoriaService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listarTodas() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(categoriaService.buscarPorId(idCategoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> cadastrar(@RequestBody @Valid CategoriaDto categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.cadastrar(categoria));
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponseDto> atualizar(@PathVariable Long idCategoria,
            @RequestBody @Valid CategoriaUpdateDto categoria) {
        return ResponseEntity.ok(categoriaService.atualizar(idCategoria, categoria));
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> remover(@PathVariable Long idCategoria) {
        categoriaService.remover(idCategoria);
        return ResponseEntity.noContent().build();
    }
}
