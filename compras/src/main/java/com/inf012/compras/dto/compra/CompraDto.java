package com.inf012.compras.dto.compra;

import java.util.List;

import com.inf012.compras.dto.itemcompra.ItemCompraDto;
import com.inf012.compras.model.TipoEntrega;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CompraDto(
                @NotNull(message = "O id do cliente deve ser preenchido") Long clienteId,

                @NotNull(message = "O tipo de entrega deve ser preenchido") TipoEntrega tipoEntrega,

                @NotEmpty(message = "A compra deve ter ao menos um item") @Valid List<ItemCompraDto> itens) {
}
