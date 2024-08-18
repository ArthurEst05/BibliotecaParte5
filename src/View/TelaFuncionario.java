package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setTitle("Tela Funcionário");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        // Inicializa os dados
        livros = control.getAllLivros();
        usuarios = control.getAllPessoas();
        emprestimos = control.getAllEmprestimos();
        reservas = control.getAllReservas();
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Botões
        JPanel buttonPanel = new JPanel();
        JButton listarUsuariosButton = new JButton("Listar Usuários");
        JButton listarLivrosButton = new JButton("Listar Livros");
        JButton gerenciarLivrosButton = new JButton("Gerenciar Livros");
        JButton gerenciarUsuariosButton = new JButton("Gerenciar Usuários");
        JButton emprestimoButton = new JButton("Empréstimo");
        JButton reservarButton = new JButton("Reservar");
        JButton devoluçãoButton = new JButton("Devolução/Renovação");
        JButton obraDigitalButton = new JButton("Acessar obra digital");

        buttonPanel.add(listarUsuariosButton);
        buttonPanel.add(listarLivrosButton);
        buttonPanel.add(gerenciarLivrosButton);
        buttonPanel.add(gerenciarUsuariosButton);
        buttonPanel.add(emprestimoButton);
        buttonPanel.add(reservarButton);
        buttonPanel.add(devoluçãoButton);
        buttonPanel.add(obraDigitalButton);

        panel.add(buttonPanel);

        // Tabelas
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 1)); // Usando GridLayout para alinhar as tabelas verticalmente

        livrosTable = new JTable();
        usuariosTable = new JTable();

        tablePanel.add(new JScrollPane(livrosTable));
        tablePanel.add(new JScrollPane(usuariosTable));
        panel.add(tablePanel);

        listarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUsuariosTable();
            }
        });

        listarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLivrosTable();
            }
        });

        gerenciarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GerenciadorLivro gerenciadorLivro = new GerenciadorLivro(control);
                gerenciadorLivro.setVisible(true);
            }
        });

        gerenciarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario(control);
                gerenciadorUsuario.setVisible(true);
            }
        });

        emprestimoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogEmprestimo dialog = new DialogEmprestimo(
                    TelaFuncionario.this,
                    livros,
                    usuarios,
                    emprestimos,
                    control
                );
                dialog.setVisible(true);
            }
        });

        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextField livroTituloField = new JTextField(20);
                JTextField usuarioNomeField = new JTextField(20);

                panel.add(new JLabel("Digite o título do livro:"));
                panel.add(livroTituloField);
                panel.add(new JLabel("Digite o nome do usuário:"));
                panel.add(usuarioNomeField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Reservar Livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String livroTitulo = livroTituloField.getText();
                    String usuarioNome = usuarioNomeField.getText();

                    Livro livroReserva = livros.stream()
                        .filter(livro -> livro.getTitulo().equalsIgnoreCase(livroTitulo))
                        .findFirst().orElse(null);

                    Pessoa usuarioReserva = usuarios.stream()
                        .filter(usuario -> usuario.getNome().equalsIgnoreCase(usuarioNome))
                        .findFirst().orElse(null);

                    if (livroReserva == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        return;
                    }

                    if (usuarioReserva == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        return;
                    }

                    if (livroReserva.isDisponivel()) {
                        JOptionPane.showMessageDialog(null, "O livro está disponível para empréstimo, não é necessário reservar.");
                        return;
                    }

                    Reserva novaReserva = new Reserva(usuarioReserva, livroReserva, null, null);
                    reservas.add(novaReserva);
                    control.addReserva(novaReserva);

                    JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso.");
                }
            }
        });

        devoluçãoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextField livroTituloField = new JTextField(20);
                JTextField usuarioNomeField = new JTextField(20);

                panel.add(new JLabel("Digite o título do livro:"));
                panel.add(livroTituloField);
                panel.add(new JLabel("Digite o nome do usuário:"));
                panel.add(usuarioNomeField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Devolução de Livro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String livroTitulo = livroTituloField.getText();
                    String usuarioNome = usuarioNomeField.getText();

                    Livro livroDevolucao = livros.stream()
                        .filter(livro -> livro.getTitulo().equalsIgnoreCase(livroTitulo))
                        .findFirst().orElse(null);

                    Pessoa usuarioDevolucao = usuarios.stream()
                        .filter(usuario -> usuario.getNome().equalsIgnoreCase(usuarioNome))
                        .findFirst().orElse(null);

                    if (livroDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        return;
                    }

                    if (usuarioDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        return;
                    }

                    Emprestimos emprestimoDevolucao = emprestimos.stream()
                        .filter(emprestimo -> emprestimo.getLivros().equals(livroDevolucao) &&
                                            emprestimo.getPessoa().equals(usuarioDevolucao) &&
                                            !emprestimo.isDevolvido())
                        .findFirst().orElse(null);

                    if (emprestimoDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Não há empréstimo registrado para este livro e usuário.");
                        return;
                    }

                    emprestimoDevolucao.setDevolvido(true);
                    livroDevolucao.setDisponivel(true);

                    control.updateEmprestimo(emprestimoDevolucao);
                    control.updateLivro(livroDevolucao);

                    JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso.");
                }
            }
        });

        obraDigitalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogObrasDigitais dialog = new DialogObrasDigitais(
                    TelaFuncionario.this,
                    livros
                );
                dialog.setVisible(true);
            }
        });
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
        // Certifique-se de que a lista de usuários esteja atualizada
        usuarios = control.getAllPessoas();
    
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nome");
        model.addColumn("Tipo");
    
        for (Pessoa usuario : usuarios) {
            String tipo = usuario instanceof Aluno ? "Aluno" : usuario instanceof Funcionario ? "Funcionario" : "Orientador";
            model.addRow(new Object[]{usuario.getNome(), tipo});
        }
    
        usuariosTable.setModel(model);
    
        // Revalide e repinte a tabela
        usuariosTable.revalidate();
        usuariosTable.repaint();
    }
}
