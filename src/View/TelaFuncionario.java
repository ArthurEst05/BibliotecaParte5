package View;

import javax.swing.*;

import Controller.Controller;
import Emprest.Emprestimos;
import Emprest.Reserva;
import Faculdade.Trabalho;
import Obras.Livro;
import Usuarios.Aluno;
import Usuarios.Pessoa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TelaFuncionario extends JFrame {
    Controller control;
    public static final String USUARIOS_FILE = "C:\\temp\\usuarios.txt";
    public static final String LIVROS_FILE = "C:\\temp\\livros.txt";
    public static final String EMPRESTIMOS_FILE = "C:\\temp\\emprestimos.txt";
    public static final String RESERVA_FILE = "C:\\temp\\reservas.txt";
    public static final String TRABALHO_FILE = "C:\\temp\\trabalhos.txt";

    public static ArrayList<Emprestimos> emprestimos = new ArrayList<>();
    public static ArrayList<Livro> livros = new ArrayList<>();
    public static ArrayList<Pessoa> usuarios = new ArrayList<>();
    public static ArrayList<Reserva> reservas = new ArrayList<>();
    public static ArrayList<Trabalho> trabalhos = new ArrayList<>();

    public TelaFuncionario() {
        setTitle("Tela Funcionario");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        
        usuarios.add(new Aluno("Sergio"));
        usuarios.add(new Aluno("Arthur"));
        control.saveUsuariosToFile(USUARIOS_FILE, usuarios);
        livros = control.loadLivrosFromFile(LIVROS_FILE);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton listarUsuariosButton = new JButton("Listar Usuários");
        listarUsuariosButton.setBounds(100, 50, 200, 30);
        panel.add(listarUsuariosButton);

        JButton listarLivrosButton = new JButton("Listar Livros");
        listarLivrosButton.setBounds(100, 100, 200, 30);
        panel.add(listarLivrosButton);

        JButton gerenciarLivrosButton = new JButton("Gerenciar Livros");
        gerenciarLivrosButton.setBounds(100, 150, 200, 30);
        panel.add(gerenciarLivrosButton);

        JButton gerenciarUsuariosButton = new JButton("Gerenciar Usuários");
        gerenciarUsuariosButton.setBounds(100, 200, 200, 30);
        panel.add(gerenciarUsuariosButton);

        JButton emprestimoButton = new JButton("Empréstimo");
        emprestimoButton.setBounds(100, 250, 200, 30);
        panel.add(emprestimoButton);

        JButton reservarButton = new JButton("Reservar");
        reservarButton.setBounds(100, 300, 200, 30);
        panel.add(reservarButton);

        JButton devoluçãoButton = new JButton("Devolução/Renovação");
        devoluçãoButton.setBounds(100, 350, 200, 30);
        panel.add(devoluçãoButton);

        JButton obraDigitalButton = new JButton("Acessar obra digital");
        obraDigitalButton.setBounds(100, 400, 200, 30);
        panel.add(obraDigitalButton);

        
        listarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder pessoa = new StringBuilder("Usuarios:\n");
                for (Pessoa p : usuarios) {
                    pessoa.append(p.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, pessoa.toString());
            }
        });

        listarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder acervo = new StringBuilder("Livros:\n");
                for (Livro l : livros) {
                    acervo.append(l.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, acervo.toString());
            }
        });

        gerenciarLivrosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GerenciadorLivro gerenciadorLivro = new GerenciadorLivro();
                gerenciadorLivro.setVisible(true);
            }
        });

        gerenciarUsuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario();
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
                // Cria o painel para entrada de dados
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
        
                    // Verifica se o livro e o usuário existem
                    Livro livroReserva = null;
                    Pessoa usuarioReserva = null;
        
                    for (Livro livro : livros) {
                        if (livro.getTitulo().equalsIgnoreCase(livroTitulo)) {
                            livroReserva = livro;
                            break;
                        }
                    }
        
                    for (Pessoa usuario : usuarios) {
                        if (usuario.getNome().equalsIgnoreCase(usuarioNome)) {
                            usuarioReserva = usuario;
                            break;
                        }
                    }
        
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
        
                    // Cria a nova reserva
                    Reserva novaReserva = new Reserva(usuarioReserva, livroReserva, LocalDate.now(), LocalTime.now());
                    reservas.add(novaReserva);
        
                    // Salva os dados atualizados
                    control.saveReservasToFile(RESERVA_FILE, reservas);
        
                    JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso.");
                }
            }
        });

        devoluçãoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para entrada de dados
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
        
                    // Verifica se o livro e o usuário existem
                    Livro livroDevolucao = null;
                    Pessoa usuarioDevolucao = null;
                    Emprestimos emprestimoDevolucao = null;
        
                    for (Livro livro : livros) {
                        if (livro.getTitulo().equalsIgnoreCase(livroTitulo)) {
                            livroDevolucao = livro;
                            break;
                        }
                    }
        
                    for (Pessoa usuario : usuarios) {
                        if (usuario.getNome().equalsIgnoreCase(usuarioNome)) {
                            usuarioDevolucao = usuario;
                            break;
                        }
                    }
        
                    if (livroDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        return;
                    }
        
                    if (usuarioDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        return;
                    }
        
                    // Verifica se há um empréstimo correspondente para devolução
                    for (Emprestimos emprestimo : emprestimos) {
                        if (emprestimo.getLivros().equals(livroDevolucao) && emprestimo.getPessoa().equals(usuarioDevolucao) && !emprestimo.isDevolvido()) {
                            emprestimoDevolucao = emprestimo;
                            break;
                        }
                    }
        
                    if (emprestimoDevolucao == null) {
                        JOptionPane.showMessageDialog(null, "Não há empréstimo registrado para este livro e usuário.");
                        return;
                    }
        
                    // Processa a devolução
                    emprestimoDevolucao.setDevolvido(true);
                    livroDevolucao.setDisponivel(true);
        
                    // Salva os dados atualizados
                    control.saveEmprestimosToFile(EMPRESTIMOS_FILE, emprestimos);
                    control.saveLivrosToFile(LIVROS_FILE, livros);
        
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaUsuario().setVisible(true);
        });
    }

}
