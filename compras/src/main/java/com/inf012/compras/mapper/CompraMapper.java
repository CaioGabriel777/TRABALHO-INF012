package com.inf012.compras.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.inf012.compras.dto.compra.CompraDto;
import com.inf012.compras.dto.compra.CompraResponseDto;
import com.inf012.compras.model.Compra;
import com.inf012.compras.model.ItemCompra;

@Component
public record CompraMapper(ItemCompraMapper itemCompraMapper) {

    public Compra toEntity(CompraDto compra) {
        Compra novaCompra = new Compra();
        novaCompra.setClienteId(compra.clienteId());
        novaCompra.setTipoEntrega(compra.tipoEntrega());

        List<ItemCompra> itens = compra.itens().stream().map(itemCompraMapper::toEntity).toList();
        itens.forEach(item -> item.setCompra(novaCompra));
        novaCompra.setItens(itens);

        return novaCompra;
    }

    public CompraResponseDto fromEntity(Compra compra) {
        return new CompraResponseDto(
                compra.getId(),
                compra.getClienteId(),
                compra.getStatus(),
                compra.getTipoEntrega(),
                compra.getValorTotal(),
                compra.getItens().stream().map(itemCompraMapper::fromEntity).toList(),
                compra.getDataCriacao(),
                compra.getDataAtualizacao());
    }
}
