package com.inf012.compras.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.inf012.compras.dto.compra.CompraDto;
import com.inf012.compras.dto.compra.CompraResponseDto;
import com.inf012.compras.exception.BusinessException;
import com.inf012.compras.exception.ResourceNotFoundException;
import com.inf012.compras.feign.ClienteClient;
import com.inf012.compras.mapper.CompraMapper;
import com.inf012.compras.model.Compra;
import com.inf012.compras.model.StatusCompra;
import com.inf012.compras.rabbitmq.event.CompraRealizadaEvent;
import com.inf012.compras.rabbitmq.publisher.CompraEventPublisher;
import com.inf012.compras.repository.CompraRepository;

import feign.FeignException;
import jakarta.transaction.Transactional;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteClient clienteClient;
    private final CompraEventPublisher compraEventPublisher;
    private final CompraMapper mapper;

    public CompraService(CompraRepository compraRepository,
            ClienteClient clienteClient,
            CompraEventPublisher compraEventPublisher,
            CompraMapper mapper) {
        this.compraRepository = compraRepository;
        this.clienteClient = clienteClient;
        this.compraEventPublisher = compraEventPublisher;
        this.mapper = mapper;
    }

    @Transactional
    public List<CompraResponseDto> listarTodas() {
        List<Compra> compras = compraRepository.findAll();

        if (compras.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma compra encontrada");
        }

        return compras.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public CompraResponseDto buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Id inválido");
        }

        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada: " + id));

        return mapper.fromEntity(compra);
    }

    @Transactional
    public List<CompraResponseDto> historicoPorCliente(Long clienteId) {
        List<Compra> compras = compraRepository.findByClienteId(clienteId);

        if (compras.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma compra encontrada para o cliente: " + clienteId);
        }

        return compras.stream().map(mapper::fromEntity).toList();
    }

    @Transactional
    public CompraResponseDto registrarCompra(CompraDto compraDto) {
        validarCliente(compraDto.clienteId());

        Compra novaCompra = mapper.toEntity(compraDto);

        BigDecimal valorTotal = novaCompra.getItens().stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        novaCompra.setValorTotal(valorTotal);
        novaCompra.setStatus(StatusCompra.PENDENTE);

        Compra salva = compraRepository.save(novaCompra);

        compraEventPublisher.publicarCompraRealizada(
                new CompraRealizadaEvent(salva.getId(), salva.getClienteId(), salva.getValorTotal(),
                        LocalDateTime.now()));

        return mapper.fromEntity(salva);
    }

    @Transactional
    public CompraResponseDto cancelar(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Compra não encontrada: " + id));

        compra.setStatus(StatusCompra.CANCELADA);

        return mapper.fromEntity(compraRepository.save(compra));
    }

    private void validarCliente(Long clienteId) {
        try {
            clienteClient.buscarPorId(clienteId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Cliente não encontrado: " + clienteId);
        }
    }
}
