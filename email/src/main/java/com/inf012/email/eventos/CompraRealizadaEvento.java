package com.inf012.email.eventos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CompraRealizadaEvento(
        Long compraId,
        Long clienteId,
        BigDecimal valorTotal,
        LocalDateTime dataCompra) {
}
