/*
 * Desenvolvido para a matéria de Programação Orientada a Objetos na Faculdade de Engenharia de Sorocaba (FACENS)
 *
 * Autores:
 * - Pedro Salviano Santos - 236586
 * - Luiz Gustavo Motta Viana - 236428
 * - Erick Ferreira Ribeiro - 237046
 */
package org.sant.domain.entity;

import java.util.stream.Stream;

/**
 * Classe que representa o endereço de um amigo na aplicação Agenda.
 * <p>
 * Contém informações de localização como rua, número, complemento, cidade, estado e CEP.
 * A classe também fornece métodos utilitários para formatação e impressão de endereços
 * de forma legível e padronizada.
 * </p>
 *
 * <p>
 * Segue os princípios de <b>Encapsulamento</b> e <b>Responsabilidade Única</b>,
 * garantindo que a manipulação e formatação de dados de endereço ocorram dentro da própria classe.
 * </p>
 */
public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    /**
     * Construtor padrão sem parâmetros.
     * <p>
     * Utilizado quando o endereço será definido posteriormente por meio dos setters.
     * </p>
     */
    public Endereco() {}

    /**
     * Construtor completo que inicializa todos os atributos do endereço.
     *
     * @param rua nome da rua.
     * @param numero número do imóvel.
     * @param complemento complemento do endereço (pode ser nulo ou vazio).
     * @param cidade nome da cidade.
     * @param estado sigla do estado (UF).
     * @param CEP código de endereçamento postal.
     */
    public Endereco(String rua, String numero, String complemento, String cidade, String estado, String CEP) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = CEP;
    }
    /**
     * Getters e setters para controle de acesso aos atributos.
     */
    public String getRua() { return this.rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getNumero() { return this.numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return this.complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getCidade() { return this.cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return this.estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCep() { return this.cep; }
    public void setCep(String cep) { this.cep = cep; }

    /**
     * Retorna uma representação formatada e legível do endereço.
     * <p>
     * Este método concatena apenas os campos preenchidos, omitindo valores nulos ou vazios,
     * e apresenta o CEP em uma nova linha quando informado.
     * </p>
     *
     * @return uma string contendo o endereço completo e formatado,
     *         ou "Endereço não informado." se todos os campos estiverem vazios.
     */
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

    /**
     * Formata a parte do endereço composta por rua e número.
     * <p>
     * Caso o número seja inválido ou não informado, apenas o nome da rua é retornado.
     * </p>
     *
     * @return uma string no formato "Rua, Número", ou apenas "Rua" se o número for inválido,
     *         ou {@code null} se a rua não for informada.
     */
    private String formatarRuaNumero() {
        if (rua == null || rua.isBlank()) return null;
        try {
            return numero != null && Integer.parseInt(numero) > 0
                    ? rua + ", " + numero
                    : rua;
        } catch (NumberFormatException e) {
            return rua;
        }
    }

    /**
     * Retorna o complemento do endereço, se existir.
     *
     * @return o complemento formatado ou {@code null} se estiver vazio.
     */
    private String formatarComplemento() {
        return (complemento != null && !complemento.isBlank()) ? complemento : null;
    }

    /**
     * Formata a parte do endereço composta por cidade e estado.
     * <p>
     * Retorna "Cidade - Estado" quando ambos são informados,
     * ou apenas "Cidade" quando o estado é nulo.
     * </p>
     *
     * @return uma string no formato "Cidade - Estado", ou {@code null} se a cidade não for informada.
     */
    private String formatarCidadeEstado() {
        if (cidade == null || cidade.isBlank()) return null;
        return (estado != null && !estado.isBlank())
                ? cidade + " - " + estado
                : cidade;
    }
}
