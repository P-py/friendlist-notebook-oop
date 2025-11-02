/*
 * Desenvolvido para a matéria de Programação Orientada a Objetos na Faculdade de Engenharia de Sorocaba (FACENS)
 *
 * Autores:
 * - Pedro Salviano Santos - 236586
 * - Luiz Gustavo Motta Viana - 236428
 * - Erick Ferreira Ribeiro - 237046
 */
package org.sant.service;

import org.sant.domain.entity.Amigo;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsável pelo gerenciamento de amigos cadastrados na aplicação Agenda.
 * <p>
 * Esta classe implementa as operações principais sobre a coleção de amigos, incluindo
 * cadastro, busca, listagem e filtragem por mês de aniversário.
 * </p>
 *
 * <p>
 * Segue o princípio de <b>Responsabilidade Única (SRP)</b>, atuando apenas na camada de serviço
 * e isolando a lógica de negócio do restante da aplicação.
 * </p>
 */
public class GerenciarAmigo {

    /** Lista que armazena os amigos cadastrados na aplicação. */
    private final List<Amigo> listaAmigos = new ArrayList<>();

    /**
     * Cadastra um novo amigo na lista.
     * <p>
     * O método valida se o objeto {@link Amigo} é nulo antes de adicioná-lo.
     * Após o cadastro, exibe uma mensagem de sucesso via {@link JOptionPane}.
     * </p>
     *
     * @param amigo objeto do tipo {@link Amigo} a ser cadastrado.
     * @throws NullPointerException se o parâmetro {@code amigo} for nulo.
     */
    public void cadastrarAmigo(Amigo amigo) {
        Objects.requireNonNull(amigo, "Amigo não pode ser nulo.");
        listaAmigos.add(amigo);
        JOptionPane.showMessageDialog(null, "Amigo cadastrado com sucesso!");
    }

    /**
     * Busca um amigo pelo nome informado.
     * <p>
     * A busca é feita de forma <b>case-insensitive</b> (ignorando maiúsculas/minúsculas).
     * </p>
     *
     * @param nome nome do amigo a ser buscado.
     * @return um {@link Optional} contendo o amigo encontrado, ou vazio se não houver correspondência.
     */
    public Optional<Amigo> buscarAmigo(String nome) {
        return listaAmigos.stream()
                .filter(a -> a.getNome() != null && a.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    /**
     * Busca um amigo pelo nome e retorna uma descrição detalhada.
     * <p>
     * Caso o amigo seja encontrado, retorna todas as suas informações formatadas
     * junto com o cálculo de dias para o aniversário. Caso contrário, retorna
     * uma mensagem indicando que o amigo não foi encontrado.
     * </p>
     *
     * @param nome nome do amigo a ser buscado.
     * @return texto contendo as informações do amigo ou mensagem de erro.
     * @see Amigo#imprimir()
     * @see Amigo#calcularDiasParaAniversariar()
     */
    public String buscarAmigoPeloNome(String nome) {
        return buscarAmigo(nome)
                .map(a -> a.imprimir() + "\n\n" + a.calcularDiasParaAniversariar())
                .orElse("Amigo não encontrado!");
    }

    /**
     * Retorna uma lista de amigos que fazem aniversário no mês informado.
     * <p>
     * Caso o mês seja inválido (fora do intervalo 1–12), uma mensagem de erro é retornada.
     * Se nenhum amigo for encontrado para o mês informado, uma mensagem informativa é exibida.
     * </p>
     *
     * @param mes número do mês (1 a 12).
     * @return uma string formatada com os nomes dos aniversariantes do mês.
     */
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

        return String.format("Amigos que fazem aniversário no mês %d:\n- %s",
                mes,
                String.join("\n- ", aniversariantes)
        );
    }

    /**
     * Retorna uma string contendo todos os amigos cadastrados.
     * <p>
     * Caso a lista esteja vazia, retorna uma mensagem indicando que nenhum amigo foi encontrado.
     * </p>
     *
     * @return uma string formatada com as informações de todos os amigos.
     * @see Amigo#imprimir()
     */
    public String listarTodosAmigos() {
        if (listaAmigos.isEmpty()) {
            return "Nenhum amigo cadastrado.";
        }

        return listaAmigos.stream()
                .map(Amigo::imprimir)
                .collect(Collectors.joining(
                        "\n-----------------------------\n",
                        "Lista de Amigos:\n\n",
                        ""
                ));
    }
}
