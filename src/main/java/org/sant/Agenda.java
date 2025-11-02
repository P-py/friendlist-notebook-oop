/*
 * Desenvolvido para a matéria de Programação Orientada a Objetos na Faculdade de Engenharia de Sorocaba (FACENS)
 *
 * Autores:
 * - Pedro Salviano Santos - 236586
 * - Luiz Gustavo Motta Viana - 236428
 * - Erick Ferreira Ribeiro - 237046
 */
package org.sant;

import org.sant.domain.entity.Amigo;
import org.sant.domain.entity.Endereco;
import org.sant.service.GerenciarAmigo;

import javax.swing.*;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * Classe principal da aplicação <b>Agenda de Amigos</b>.
 * <p>
 * Responsável por gerenciar o fluxo principal da interface de usuário, exibindo menus
 * e capturando entradas via {@link JOptionPane}. Implementa os princípios de
 * <b>Responsabilidade Única (SRP)</b> e <b>Clean Code</b>.
 * </p>
 */
public class Agenda {

    /** Instância única do gerenciador de amigos. */
    private static final GerenciarAmigo gerenciar = new GerenciarAmigo();

    /**
     * Método principal da aplicação Agenda.
     * <p>
     * Controla o fluxo de execução da aplicação, exibindo o menu de opções e chamando
     * as funções correspondentes a cada escolha do usuário. O programa é encerrado
     * quando o usuário seleciona a opção {@code SAIR}.
     * </p>
     *
     * @param args parâmetros de linha de comando (não utilizados nesta aplicação)
     */
    public static void main(String[] args) {
        while (true) {
            MenuOpcao opcao = exibirMenu();
            if (opcao == MenuOpcao.SAIR) break;

            try {
                switch (opcao) {
                    case CADASTRAR -> cadastrarAmigo();
                    case BUSCAR -> buscarAmigo();
                    case ANIVERSARIANTES -> listarAniversariantes();
                    case LISTAR_TODOS -> listarTodos();
                    case INVALIDA -> JOptionPane.showMessageDialog(null, "Opção inválida!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Erro: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        JOptionPane.showMessageDialog(null, "Encerrando a Agenda. Até logo!");
    }

    /**
     * Exibe o menu principal da aplicação e retorna a opção escolhida pelo usuário.
     * <p>
     * Utiliza o método {@link #safeIntInput(String, Integer, Integer)} para garantir
     * que o valor informado seja numérico. Caso o usuário cancele a operação ou
     * digite algo inválido, é retornada a opção {@link MenuOpcao#INVALIDA}.
     * </p>
     *
     * @return a opção selecionada pelo usuário como um {@link MenuOpcao}.
     */
    private static MenuOpcao exibirMenu() {
        String menu = """
                ======= AGENDA DE AMIGOS =======
                1 - Cadastrar amigo
                2 - Buscar amigo pelo nome
                3 - Aniversariantes do mês
                4 - Listar todos os amigos
                0 - Sair
                """;

        int escolha = safeIntInput(menu, null, null).orElse(-1);
        return MenuOpcao.fromCodigo(escolha);
    }

    /**
     * Exibe um formulário gráfico para cadastrar um novo amigo e o adiciona à lista gerenciada.
     * <p>
     * O formulário é criado dinamicamente via {@link JPanel} e contém campos para informações pessoais
     * e de endereço. As entradas são validadas antes do cadastro, garantindo que a data de nascimento
     * seja válida e que os campos obrigatórios estejam preenchidos.
     * </p>
     *
     * @throws NumberFormatException caso algum campo numérico (dia, mês ou ano) seja inválido.
     * @throws IllegalArgumentException caso a data de nascimento seja incorreta.
     */
    private static void cadastrarAmigo() {
        JPanel panel = criarFormularioAmigo();

        int result = JOptionPane.showConfirmDialog(
                null, panel, "Cadastrar Novo Amigo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.");
            return;
        }

        try {
            JTextField[] campos = extrairCampos(panel);

            String nome = campos[0].getText().trim();
            String celular = campos[1].getText().trim();
            String email = campos[2].getText().trim();
            String rua = campos[3].getText().trim();
            String numero = campos[4].getText().trim();
            String complemento = campos[5].getText().trim();
            String cidade = campos[6].getText().trim();
            String estado = campos[7].getText().trim();
            String cep = campos[8].getText().trim();
            int dia = Integer.parseInt(campos[9].getText().trim());
            int mes = Integer.parseInt(campos[10].getText().trim());
            int ano = Integer.parseInt(campos[11].getText().trim());

            int anoAtual = new GregorianCalendar().get(java.util.Calendar.YEAR);
            if (ano >= anoAtual) throw new IllegalArgumentException("Ano de nascimento deve ser menor que o atual.");
            if (dia < 1 || dia > 31 || mes < 1 || mes > 12)
                throw new IllegalArgumentException("Data de nascimento inválida.");

            Endereco endereco = new Endereco(rua, numero, complemento, cidade, estado, cep);
            GregorianCalendar dataNasc = new GregorianCalendar(ano, mes - 1, dia);

            Amigo amigo = new Amigo(nome, endereco, celular, email, dataNasc);
            gerenciar.cadastrarAmigo(amigo);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "⚠️ Preencha todos os campos numéricos corretamente!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Solicita ao usuário o nome de um amigo e exibe suas informações, caso encontrado.
     * Utiliza o método {@link GerenciarAmigo#buscarAmigoPeloNome(String)} para realizar a busca.
     */
    private static void buscarAmigo() {
        String nome = input("Digite o nome do amigo:");
        JOptionPane.showMessageDialog(null, gerenciar.buscarAmigoPeloNome(nome));
    }

    /**
     * Solicita um mês ao usuário e exibe a lista de amigos que fazem aniversário nesse mês.
     * <p>
     * Utiliza o método {@link GerenciarAmigo#aniversariarNoMes(int)} para obter os resultados.
     * </p>
     */
    private static void listarAniversariantes() {
        safeIntInput("Informe o mês (1-12):", 1, 12)
                .ifPresentOrElse(
                        mes -> JOptionPane.showMessageDialog(null, gerenciar.aniversariarNoMes(mes)),
                        () -> JOptionPane.showMessageDialog(null, "Operação cancelada.")
                );
    }

    /**
     * Exibe todos os amigos cadastrados na aplicação.
     * Utiliza o método {@link GerenciarAmigo#listarTodosAmigos()} para recuperar os dados.
     */
    private static void listarTodos() {
        JOptionPane.showMessageDialog(null, gerenciar.listarTodosAmigos());
    }

    /**
     * Cria e retorna um formulário {@link JPanel} com campos de texto para o cadastro de um amigo.
     *
     * @return painel contendo os campos de entrada de dados do amigo.
     */
    private static JPanel criarFormularioAmigo() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        String[] labels = {
                "Nome", "Celular", "E-mail",
                "Rua", "Número", "Complemento",
                "Cidade", "Estado", "CEP",
                "Dia nascimento", "Mês nascimento", "Ano nascimento"
        };
        for (String label : labels) {
            panel.add(new JLabel(label + ":"));
            panel.add(new JTextField(15));
        }
        return panel;
    }

    /**
     * Extrai todos os campos de texto ({@link JTextField}) de um painel.
     *
     * @param panel painel contendo os componentes.
     * @return um array com os campos de texto presentes no painel.
     */
    private static JTextField[] extrairCampos(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
                .filter(c -> c instanceof JTextField)
                .map(c -> (JTextField) c)
                .toArray(JTextField[]::new);
    }

    /**
     * Solicita uma entrada textual ao usuário e retorna o valor informado.
     * <p>
     * Caso o usuário cancele a operação, a aplicação é encerrada. Se o campo estiver vazio,
     * uma exceção é lançada indicando campo obrigatório.
     * </p>
     *
     * @param msg mensagem exibida na caixa de diálogo.
     * @return texto informado pelo usuário.
     * @throws IllegalArgumentException caso o campo esteja vazio.
     */
    private static String input(String msg) {
        String value = JOptionPane.showInputDialog(msg);

        if (value == null) {
            JOptionPane.showMessageDialog(null, "Encerrando a Agenda...");
            System.exit(0);
        }

        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null, "O campo '" + msg + "' não pode estar vazio!");
            throw new IllegalArgumentException("Campo obrigatório não preenchido.");
        }

        return value.trim();
    }

    /**
     * Solicita uma entrada numérica ao usuário, validando limites opcionais (mínimo e máximo).
     * <p>
     * Caso o usuário clique em "Cancelar" ou feche a janela, a aplicação é encerrada.
     * Se a entrada for vazia ou inválida, retorna um {@link Optional#empty()}.
     * </p>
     *
     * @param msg mensagem exibida ao usuário.
     * @param min valor mínimo permitido (pode ser {@code null}).
     * @param max valor máximo permitido (pode ser {@code null}).
     * @return valor numérico válido dentro dos limites, ou vazio se a entrada for inválida.
     */
    private static Optional<Integer> safeIntInput(String msg, Integer min, Integer max) {
        try {
            String s = JOptionPane.showInputDialog(msg);

            if (s == null) {
                JOptionPane.showMessageDialog(null, "Encerrando a Agenda...");
                System.exit(0);
            }

            if (s.isBlank()) {
                JOptionPane.showMessageDialog(null, "O campo não pode estar vazio!");
                return Optional.empty();
            }

            int val = Integer.parseInt(s.trim());

            if (min != null && val < min)
                throw new IllegalArgumentException("Valor deve ser maior ou igual a " + min + ".");
            if (max != null && val > max)
                throw new IllegalArgumentException("Valor deve ser menor ou igual a " + max + ".");

            return Optional.of(val);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Digite apenas números válidos!");
            return Optional.empty();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Enumeração que representa as opções disponíveis no menu principal da aplicação.
     * <p>
     * Cada constante está associada a um código numérico correspondente à opção exibida no menu.
     * </p>
     */
    private enum MenuOpcao {
        CADASTRAR(1),
        BUSCAR(2),
        ANIVERSARIANTES(3),
        LISTAR_TODOS(4),
        SAIR(0),
        INVALIDA(-1);

        /** Código numérico associado à opção do menu. */
        private final int codigo;

        /**
         * Construtor da enum {@code MenuOpcao}.
         *
         * @param codigo número correspondente à opção do menu.
         */
        MenuOpcao(int codigo) {
            this.codigo = codigo;
        }

        /**
         * Retorna a opção de menu correspondente ao código informado.
         * <p>
         * Caso o código não seja reconhecido, retorna {@link MenuOpcao#INVALIDA}.
         * </p>
         *
         * @param codigo valor numérico informado pelo usuário.
         * @return constante {@link MenuOpcao} equivalente, ou {@link MenuOpcao#INVALIDA}.
         */
        public static MenuOpcao fromCodigo(int codigo) {
            for (MenuOpcao opcao : values()) {
                if (opcao.codigo == codigo) return opcao;
            }
            return INVALIDA;
        }
    }
}
