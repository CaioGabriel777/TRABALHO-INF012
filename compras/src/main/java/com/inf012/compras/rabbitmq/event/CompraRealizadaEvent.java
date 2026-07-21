package com.inf012.compras.rabbitmq.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompraRealizadaEvent(
        Long compraId,
        Long clienteId,
        BigDecimal valorTotal,
        LocalDateTime dataCompra) {
}
