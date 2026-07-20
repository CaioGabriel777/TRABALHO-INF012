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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inf012.cliente.dto.endereco.EnderecoDto;
import com.inf012.cliente.dto.endereco.EnderecoResponseDto;
import com.inf012.cliente.dto.endereco.EnderecoUpdateDto;
import com.inf012.cliente.service.EnderecoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<EnderecoResponseDto>> listarPorCliente(@RequestParam Long clienteId) {
        return ResponseEntity.ok(enderecoService.listarPorCliente(clienteId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDto> cadastrar(@RequestBody @Valid EnderecoDto endereco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.cadastrar(endereco));
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<EnderecoResponseDto> atualizar(@PathVariable Long idEndereco,
            @RequestBody @Valid EnderecoUpdateDto endereco) {
        return ResponseEntity.ok(enderecoService.atualizar(idEndereco, endereco));
    }

    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> remover(@PathVariable Long idEndereco) {
        enderecoService.remover(idEndereco);
        return ResponseEntity.noContent().build();
    }
}
