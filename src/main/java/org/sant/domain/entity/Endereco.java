package org.sant.domain.entity;

import java.util.stream.Stream;

public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {}

    public Endereco(String rua, String numero, String complemento, String cidade, String estado, String CEP) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = CEP;
    }

    public String getRua() { return this.rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getNumero() { return this.numero;}
    public void setNumero(String numero) { this.numero = numero;}

    public String getComplemento() { return this.complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getCidade() { return this.cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return this.estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return this.cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String imprimir() {
        String enderecoPrincipal = Stream.of(
                        formatarRuaNumero(),
                        formatarComplemento(),
                        formatarCidadeEstado()
                )
                .filter(s -> s != null && !s.isBlank())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Endereço não informado.");

        String cepFormatado = (cep != null && !cep.isBlank())
                ? "\nCEP: " + cep
                : "";

        return enderecoPrincipal + cepFormatado;
    }

    private String formatarRuaNumero() {
        if (rua == null || rua.isBlank()) return null;
        return numero != null && Integer.parseInt(numero) > 0
                ? rua + ", " + numero
                : rua;
    }

    private String formatarComplemento() {
        return (complemento != null && !complemento.isBlank()) ? complemento : null;
    }

    private String formatarCidadeEstado() {
        if (cidade == null || cidade.isBlank()) return null;
        return (estado != null && !estado.isBlank())
                ? cidade + " - " + estado
                : cidade;
    }
}
