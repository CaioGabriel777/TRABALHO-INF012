package com.inf012.cliente.controller;

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

import com.inf012.cliente.dto.cliente.ClienteDto;
import com.inf012.cliente.dto.cliente.ClienteResponseDto;
import com.inf012.cliente.dto.cliente.ClienteUpdateDto;
import com.inf012.cliente.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> cadastrar(@RequestBody @Valid ClienteDto cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrar(cliente));
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDto> atualizar(@PathVariable Long idCliente,
            @RequestBody @Valid ClienteUpdateDto cliente) {
        return ResponseEntity.ok(clienteService.atualizar(idCliente, cliente));
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> remover(@PathVariable Long idCliente) {
        clienteService.remover(idCliente);
        return ResponseEntity.noContent().build();
    }
}
