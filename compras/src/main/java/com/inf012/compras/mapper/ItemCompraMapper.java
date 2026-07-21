package com.inf012.compras.mapper;

import org.springframework.stereotype.Component;

import com.inf012.compras.dto.itemcompra.ItemCompraDto;
import com.inf012.compras.dto.itemcompra.ItemCompraResponseDto;
import com.inf012.compras.model.ItemCompra;

@Component
public record ItemCompraMapper() {

    public ItemCompra toEntity(ItemCompraDto item) {
        ItemCompra novoItem = new ItemCompra();
        novoItem.setProdutoId(item.produtoId());
        novoItem.setQuantidade(item.quantidade());
        novoItem.setPrecoUnitario(item.precoUnitario());

        return novoItem;
    }

    public ItemCompraResponseDto fromEntity(ItemCompra item) {
        return new ItemCompraResponseDto(
                item.getId(),
                item.getProdutoId(),
                item.getQuantidade(),
                item.getPrecoUnitario());
    }
}
