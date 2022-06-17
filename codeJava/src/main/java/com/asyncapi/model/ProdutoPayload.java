package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Objects;


public class ProdutoPayload {
    
    private @Valid Integer idProduto;
    
    private @Valid String descricao;
    
    private @Valid Double preco;
    
    private @Valid String dataAtualizacao;
    

    

    /**
     * Id Produto.
     */
    @JsonProperty("idProduto")
    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
    

    /**
     * Descrição produto.
     */
    @JsonProperty("descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    

    /**
     * Preço do produto.
     */
    @JsonProperty("preco")
    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    

    /**
     * Data da alteração/criação do produto.
     */
    @JsonProperty("dataAtualizacao")
    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProdutoPayload produtoPayload = (ProdutoPayload) o;
        return 
            Objects.equals(this.idProduto, produtoPayload.idProduto) &&
            Objects.equals(this.descricao, produtoPayload.descricao) &&
            Objects.equals(this.preco, produtoPayload.preco) &&
            Objects.equals(this.dataAtualizacao, produtoPayload.dataAtualizacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, descricao, preco, dataAtualizacao);
    }

    @Override
    public String toString() {
        return "class ProdutoPayload {\n" +
        
                "    idProduto: " + toIndentedString(idProduto) + "\n" +
                "    descricao: " + toIndentedString(descricao) + "\n" +
                "    preco: " + toIndentedString(preco) + "\n" +
                "    dataAtualizacao: " + toIndentedString(dataAtualizacao) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
           return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}