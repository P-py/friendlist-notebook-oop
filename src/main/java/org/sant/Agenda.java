package org.sant;

import org.sant.domain.entity.Amigo;
import org.sant.domain.entity.Endereco;
import org.sant.service.GerenciarAmigo;

import javax.swing.*;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * Aplicação principal - Agenda de Amigos.
 * Responsável apenas pela interação com o usuário (UI).
 * Segue princípios SRP e Clean Code.
 */
public class Agenda {
    private static final GerenciarAmigo gerenciar = new GerenciarAmigo();

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

    private static MenuOpcao exibirMenu() {
        String menu = """
                ======= AGENDA DE AMIGOS =======
                1 - Cadastrar amigo
                2 - Buscar amigo pelo nome
                3 - Aniversariantes do mês
                4 - Listar todos os amigos
                0 - Sair
                """;

        int escolha = safeIntInput(menu,null, null).orElse(-1);
        return MenuOpcao.fromCodigo(escolha);
    }

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
            // Recupera campos do formulário
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
            JOptionPane.showMessageDialog(
                    null,
                    "⚠️ Preencha todos os campos numéricos corretamente!"
            );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static void buscarAmigo() {
        String nome = input("Digite o nome do amigo:");
        JOptionPane.showMessageDialog(null, gerenciar.buscarAmigoPeloNome(nome));
    }

    private static void listarAniversariantes() {
        safeIntInput("Informe o mês (1-12):", 1, 12)
                .ifPresentOrElse(
                        mes -> JOptionPane.showMessageDialog(null, gerenciar.aniversariarNoMes(mes)),
                        () -> JOptionPane.showMessageDialog(null, "Operação cancelada.")
                );
    }

    private static void listarTodos() {
        JOptionPane.showMessageDialog(null, gerenciar.listarTodosAmigos());
    }

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

    private static JTextField[] extrairCampos(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
                .filter(c -> c instanceof JTextField)
                .map(c -> (JTextField) c)
                .toArray(JTextField[]::new);
    }

    private static String input(String msg) {
        String value = JOptionPane.showInputDialog(msg);

        if (value == null) { // Cancelar ou X
            JOptionPane.showMessageDialog(null, "Encerrando a Agenda...");
            System.exit(0);
        }

        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null, "O campo '" + msg + "' não pode estar vazio!");
            throw new IllegalArgumentException("Campo obrigatório não preenchido.");
        }

        return value.trim();
    }


    private static Optional<Integer> safeIntInput(String msg, Integer min, Integer max) {
        try {
            String s = JOptionPane.showInputDialog(msg);

            // Usuário clicou em "Cancelar" ou no "X"
            if (s == null) {
                JOptionPane.showMessageDialog(null, "Encerrando a Agenda...");
                System.exit(0);
            }

            // Usuário clicou em OK sem digitar nada → apenas inválido
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

    private enum MenuOpcao {
        CADASTRAR(1),
        BUSCAR(2),
        ANIVERSARIANTES(3),
        LISTAR_TODOS(4),
        SAIR(0),
        INVALIDA(-1);

        private final int codigo;

        MenuOpcao(int codigo) {
            this.codigo = codigo;
        }

        public static MenuOpcao fromCodigo(int codigo) {
            for (MenuOpcao opcao : values()) {
                if (opcao.codigo == codigo) return opcao;
            }
            return INVALIDA;
        }
    }
}
