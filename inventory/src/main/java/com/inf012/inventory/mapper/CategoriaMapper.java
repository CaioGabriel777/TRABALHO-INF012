package com.inf012.inventory.mapper;

import org.springframework.stereotype.Component;

import com.inf012.inventory.dto.categoria.CategoriaResponseDto;
import com.inf012.inventory.model.Categoria;

@Component
public record CategoriaMapper() {

    public Categoria toEntity(com.inf012.inventory.dto.categoria.CategoriaDto categoria) {
        Categoria novaCategoria = new Categoria();

        novaCategoria.setNome(categoria.nome());
        novaCategoria.setDescricao(categoria.descricao());

        return novaCategoria;
    }

    public CategoriaResponseDto fromEntity(Categoria categoria) {
        return new CategoriaResponseDto(categoria.getId(), categoria.getNome(), categoria.getDescricao(),
                categoria.getDataCriacao(), categoria.getDataAtualizacao());
    }
}
