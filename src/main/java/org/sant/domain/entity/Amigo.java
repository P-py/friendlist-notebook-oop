/*
 * Desenvolvido para a matéria de Programação Orientada a Objetos na Faculdade de Engenharia de Sorocaba (FACENS)
 *
 * Autores:
 * - Pedro Salviano Santos - 236586
 * - Luiz Gustavo Motta Viana
 * - Erick Ferreira Ribeiro
 */
package org.sant.domain.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Classe que representa um amigo cadastrado na aplicação Agenda.
 *
 * <p>
 * Contém informações pessoais como nome, endereço, celular, e-mail e data de nascimento,
 * além de métodos utilitários para impressão e cálculo de tempo até o próximo aniversário.
 * </p>
 *
 * <p>
 * Segue os princípios de <b>Encapsulamento</b> e <b>Responsabilidade Única (SRP)</b>,
 * concentrando toda a lógica e formatação relacionadas ao amigo nesta classe.
 * </p>
 */
public class Amigo {
    private String nome;
    private Endereco endereco;
    private String celular;
    private String email;
    private GregorianCalendar dataNascimento;

    /**
     * Construtor padrão sem parâmetros.
     * <p>Permite a criação de um objeto {@code Amigo} vazio, com atributos definidos posteriormente via setters.</p>
     */
    public Amigo() {}

    /**
     * Construtor completo que inicializa todos os atributos de um amigo.
     */
    public Amigo(String nome, Endereco endereco, String celular, String email, GregorianCalendar dataNascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }

    private Endereco getEndereco() { return this.endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    private String getCelular() { return this.celular; }
    public void setCelular(String celular) { this.celular = celular; }

    private String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public GregorianCalendar getDataNascimento() { return this.dataNascimento; }
    public void setDataNascimento(GregorianCalendar dataNascimento) { this.dataNascimento = dataNascimento; }

    /**
     * Retorna uma representação textual formatada com todas as informações do amigo.
     *
     * <p>
     * O método formata a data de nascimento no padrão <b>dd/MM/yyyy</b> e utiliza o método
     * {@link Endereco#imprimir()} para exibir o endereço de forma legível.
     * Caso algum campo esteja nulo ou vazio, ele é substituído pela mensagem "Não informado".
     * </p>
     *
     * @return uma string contendo os dados completos do amigo formatados.
     */
    public String imprimir() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate nascimento = LocalDate.of(
                dataNascimento.get(Calendar.YEAR),
                dataNascimento.get(Calendar.MONTH) + 1,
                dataNascimento.get(Calendar.DAY_OF_MONTH)
        );

        String dataFormatada = nascimento.format(formatter);

        return String.join("\n",
                "Nome: " + safe(nome),
                "Celular: " + safe(celular),
                "E-mail: " + safe(email),
                "Data de Nascimento: " + dataFormatada,
                endereco != null ? endereco.imprimir() : "Endereço não informado."
        );
    }

    /**
     * Retorna uma string segura representando um valor textual.
     *
     * <p>
     * Caso o valor seja nulo ou em branco, a string <b>"Não informado"</b> é retornada.
     * </p>
     *
     * @param valor texto a ser validado.
     * @return o valor original, ou "Não informado" se nulo ou vazio.
     */
    private static String safe(String valor) {
        return (valor == null || valor.isBlank()) ? "Não informado" : valor.trim();
    }

    /**
     * Calcula a quantidade de dias que faltam (ou já passaram) para o aniversário do amigo.
     * <p>
     * O cálculo é feito com base na data atual e na data de nascimento, utilizando a API {@link java.time}.
     * </p>
     *
     * <p>Exemplos de retorno:</p>
     * <ul>
     *   <li>"Hoje é o aniversário de Paulo!"</li>
     *   <li>"Faltam 7 dias para o aniversário de Maria."</li>
     *   <li>"Passaram 9 dias do aniversário de João."</li>
     * </ul>
     *
     * @return uma mensagem indicando a proximidade ou o tempo decorrido desde o aniversário do amigo.
     */
    public String calcularDiasParaAniversariar() {
        LocalDate hoje = LocalDate.now();
        LocalDate nascimento = LocalDate.of(
                dataNascimento.get(Calendar.YEAR),
                dataNascimento.get(Calendar.MONTH) + 1,
                dataNascimento.get(Calendar.DAY_OF_MONTH)
        );

        LocalDate proximoAniversario = nascimento.withYear(hoje.getYear());

        if (proximoAniversario.isBefore(hoje)) {
            proximoAniversario = proximoAniversario.plusYears(1);
        }

        long dias = ChronoUnit.DAYS.between(hoje, proximoAniversario);

        if (dias == 0) {
            return String.format("Hoje é o aniversário de %s!", nome);
        }
        return String.format(
                dias > 0
                        ? "Faltam %d dias para o aniversário de %s."
                        : "Passaram %d dias do aniversário de %s.",
                Math.abs(dias), nome
        );
    }
}
