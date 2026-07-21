package com.inf012.compras.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", url = "${cliente-service.url}")
public interface ClienteClient {

    @GetMapping("/clientes/{id}")
    ClienteDto buscarPorId(@PathVariable Long id);
}
