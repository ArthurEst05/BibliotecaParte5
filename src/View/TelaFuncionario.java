package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Controller.Controller;
import Emprest.Emprestimos;
import Emprest.Reserva;
import Obras.Livro;
import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;

public class TelaFuncionario extends JFrame {
    private Controller control;
    private List<Livro> livros;
    private List<Pessoa> usuarios;
    private List<Emprestimos> emprestimos;
    private List<Reserva> reservas;

    private JTable livrosTable;
    private JTable usuariosTable;

    public TelaFuncionario(Controller control) {
        this.control = control;
        inicializarDados();
        setTitle("Tela Funcionário");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel mainPanel = new JPanel();
        placeComponents(mainPanel);
        tabbedPane.addTab("Gerenciar", mainPanel);

        JPanel emprestimoPanel = createEmprestimoPanel();
        tabbedPane.addTab("Realizar Empréstimo", emprestimoPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void inicializarDados() {
        livros = control.getAllLivros();
        if (livros == null) {
            livros = new ArrayList<>();
        }

        usuarios = control.getAllPessoas();
        if (usuarios == null) {
            usuarios = new ArrayList<>();
        }

        emprestimos = control.getAllEmprestimos();
        if (emprestimos == null) {
            emprestimos = new ArrayList<>();
        }

        reservas = control.getAllReservas();
        if (reservas == null) {
            reservas = new ArrayList<>();
        }
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        JButton listarUsuariosButton = new JButton("Listar Usuários");
        JButton listarLivrosButton = new JButton("Listar Livros");
        JButton gerenciarLivrosButton = new JButton("Gerenciar Livros");
        JButton gerenciarUsuariosButton = new JButton("Gerenciar Usuários");

        buttonPanel.add(listarUsuariosButton);
        buttonPanel.add(listarLivrosButton);
        buttonPanel.add(gerenciarLivrosButton);
        buttonPanel.add(gerenciarUsuariosButton);

        panel.add(buttonPanel);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 1));

        livrosTable = new JTable();
        usuariosTable = new JTable();

        tablePanel.add(new JScrollPane(livrosTable));
        tablePanel.add(new JScrollPane(usuariosTable));
        panel.add(tablePanel);

        listarUsuariosButton.addActionListener(e -> updateUsuariosTable());

        listarLivrosButton.addActionListener(e -> updateLivrosTable());

        gerenciarLivrosButton.addActionListener(e -> {
            dispose();
            GerenciadorLivro gerenciadorLivro = new GerenciadorLivro(control);
            gerenciadorLivro.setVisible(true);
        });

        gerenciarUsuariosButton.addActionListener(e -> {
            dispose();
            GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario(control);
            gerenciadorUsuario.setVisible(true);
        });
    }

    private JPanel createEmprestimoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField livroTituloField = new JTextField(20);
        JTextField usuarioNomeField = new JTextField(20);

        panel.add(new JLabel("Digite o título do livro:"));
        panel.add(livroTituloField);
        panel.add(new JLabel("Digite o nome do usuário:"));
        panel.add(usuarioNomeField);

        JButton realizarEmprestimoButton = new JButton("Realizar Empréstimo");
        realizarEmprestimoButton.addActionListener(e -> {
            String livroTitulo = livroTituloField.getText().trim();
            String usuarioNome = usuarioNomeField.getText().trim();

            Livro livroSelecionado = livros.stream()
                    .filter(livro -> livro.getTitulo().equalsIgnoreCase(livroTitulo))
                    .findFirst().orElse(null);

            Pessoa usuarioSelecionado = usuarios.stream()
                    .filter(usuario -> usuario.getNome().equalsIgnoreCase(usuarioNome))
                    .findFirst().orElse(null);

            if (livroSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                return;
            }

            if (usuarioSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                return;
            }

            if (!livroSelecionado.isDisponivel()) {
                JOptionPane.showMessageDialog(null, "O livro não está disponível para empréstimo.");
                return;
            }

            LocalDate dataEmprestimo = LocalDate.now();
            LocalDate dataDevolucao = dataEmprestimo.plusDays(14);

            Emprestimos novoEmprestimo = new Emprestimos(usuarioSelecionado, livroSelecionado, dataEmprestimo, dataDevolucao);
            emprestimos.add(novoEmprestimo);
            control.addEmprestimo(novoEmprestimo);

            livroSelecionado.setDisponivel(false);
            control.updateLivro(livroSelecionado);

            JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso!");
        });

        panel.add(realizarEmprestimoButton);

        return panel;
    }

    private void updateLivrosTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Disponível");

        for (Livro livro : livros) {
            model.addRow(new Object[]{livro.getTitulo(), livro.getAutores(), livro.isDisponivel()});
        }

        livrosTable.setModel(model);
    }

    private void updateUsuariosTable() {
        usuarios = control.getAllPessoas();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nome");
        model.addColumn("Tipo");

        for (Pessoa usuario : usuarios) {
            String tipo = usuario instanceof Aluno ? "Aluno" : usuario instanceof Funcionario ? "Funcionario" : "Orientador";
            model.addRow(new Object[]{usuario.getNome(), tipo});
        }

        usuariosTable.setModel(model);

        usuariosTable.revalidate();
        usuariosTable.repaint();
    }
}
