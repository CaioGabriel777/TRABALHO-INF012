package com.inf012.compras.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inf012.compras.dto.compra.CompraDto;
import com.inf012.compras.dto.compra.CompraResponseDto;
import com.inf012.compras.service.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDto>> listarTodas() {
        return ResponseEntity.ok(compraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<CompraResponseDto>> historicoPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(compraService.historicoPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<CompraResponseDto> registrarCompra(@RequestBody @Valid CompraDto compra) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.registrarCompra(compra));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<CompraResponseDto> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(compraService.cancelar(id));
    }
}
