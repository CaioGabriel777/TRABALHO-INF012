package com.inf012.inventory.mapper;

import org.springframework.stereotype.Component;

import com.inf012.inventory.dto.produto.ProdutoDto;
import com.inf012.inventory.dto.produto.ProdutoResponseDto;
import com.inf012.inventory.model.Produto;

@Component
public record ProdutoMapper() {

    public Produto dtoToProduto(ProdutoDto produto) {
        Produto newProduto = new Produto();
        newProduto.setNome(produto.nome());
        newProduto.setDescricao(produto.descricao());
        newProduto.setPreco(produto.preco());
        newProduto.setQuantidadeEstoque(produto.quantidadeEstoque());
        newProduto.setEstoqueMinimo(produto.estoqueMinimo());

        return newProduto;
    }

    public ProdutoResponseDto fromEntity(Produto produto) {
        return new ProdutoResponseDto(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getCategoria().getNome(),
                produto.getAtivo(),
                produto.getDataCriacao(),
                produto.getDataAtualizacao());
    }
}
