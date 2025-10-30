package org.sant.domain.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Amigo {
    private String nome;
    private Endereco endereco;
    private String celular;
    private String email;
    private GregorianCalendar dataNascimento;

    public Amigo() {}

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

    private static String safe(String valor) {
        return (valor == null || valor.isBlank()) ? "Não informado" : valor.trim();
    }

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
