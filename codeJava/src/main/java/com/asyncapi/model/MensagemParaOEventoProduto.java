package com.asyncapi.model;

import javax.validation.Valid;

import java.util.Objects;
import java.util.List;

/**
 * Examples: {"name":"produto-criado-1s","summary":"Exemplo de mensagem","headers":{"correlationId":"my-correlation-id","applicationInstanceId":"myInstanceId"},"payload":{"idProduto":1,"descricao":"Celular bom","preco":1.99,"dataAtualizacao":"2022-05-23T13:06:42.375-0300"}}
 */
public class MensagemParaOEventoProduto {
    private @Valid ProdutoPayload payload;

    public ProdutoPayload getPayload() {
        return payload;
    }

    public void setPayload(ProdutoPayload payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MensagemParaOEventoProduto event = (MensagemParaOEventoProduto) o;
        return Objects.equals(this.payload, event.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload);
    }

    @Override
    public String toString() {
        return "class MensagemParaOEventoProduto {\n" +
                "    payload: " + toIndentedString(payload) + "\n" +
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