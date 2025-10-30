package org.sant.service;

import org.sant.domain.entity.Amigo;

import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class GerenciarAmigo {
    private final List<Amigo> listaAmigos = new ArrayList<>();

    public void cadastrarAmigo(Amigo amigo) {
        Objects.requireNonNull(amigo, "Amigo não pode ser nulo.");
        listaAmigos.add(amigo);
        JOptionPane.showMessageDialog(null, "Amigo cadastrado com sucesso!");
    }

    public Optional<Amigo> buscarAmigo(String nome) {
        return listaAmigos.stream()
                .filter(a -> a.getNome() != null && a.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public String buscarAmigoPeloNome(String nome) {
        return buscarAmigo(nome)
                .map(a -> a.imprimir() + "\n\n" + a.calcularDiasParaAniversariar())
                .orElse("Amigo não encontrado!");
    }

    public String aniversariarNoMes(int mes) {
        if (mes < 1 || mes > 12) {
            return "Mês inválido! Informe um número entre 1 e 12.";
        }

        var aniversariantes = listaAmigos.stream()
                .filter(a -> (a.getDataNascimento().get(Calendar.MONTH) + 1) == mes)
                .map(Amigo::getNome)
                .collect(Collectors.toList());

        if (aniversariantes.isEmpty()) {
            return "Nenhum amigo faz aniversário neste mês.";
        }

        return String.format("Amigos que fazem aniversário no mês %d:\n%s",
                mes,
                String.join("\n- ", aniversariantes)
        );
    }

    public String listarTodosAmigos() {
        if (listaAmigos.isEmpty()) {
            return "Nenhum amigo cadastrado.";
        }

        return listaAmigos.stream()
                .map(Amigo::imprimir)
                .collect(Collectors.joining("\n-----------------------------\n",
                        "Lista de Amigos:\n\n", ""));
    }
}
