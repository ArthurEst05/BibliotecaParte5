package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Controller;
import Emprest.Emprestimos;
import Emprest.Reserva;
import Faculdade.Curso;
import Faculdade.Faculdade;
import Faculdade.Trabalho;
import Obras.Livro;
import Usuarios.Aluno;
import Usuarios.Orientador;
import Usuarios.Pessoa;

public class TelaUsuario extends JFrame {
    Controller control;
    public static final String USUARIOS_FILE = "C:\\temp\\usuarios.txt";
    public static final String LIVROS_FILE = "C:\\temp\\livros.txt";
    public static final String EMPRESTIMOS_FILE = "C:\\temp\\emprestimos.txt";
    public static final String RESERVA_FILE = "C:\\temp\\reservas.txt";
    public static final String TRABALHO_FILE = "C:\\temp\\trabalhos.txt";

    public static ArrayList<Emprestimos> emprestimos;
    public static ArrayList<Livro> livros;
    public static ArrayList<Pessoa> usuarios;
    public static ArrayList<Reserva> reservas;
    public static ArrayList<Trabalho> trabalhos;
    // Lista para armazenar os livros

    public TelaUsuario() {
        setTitle("Tela Usuario");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        // Inicializa a lista de livros
        livros = new ArrayList<>();
        // Adiciona alguns livros de exemplo
        livros.add(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien"));
        livros.add(new Livro("1984", "George Orwell"));
        livros.add(new Livro("A Revolução dos Bichos", "George Orwell"));
        livros.add(new Livro("Batman", "Leonardo Da Vinci"));
        livros.add(new Livro("A Volta", "Jorge Ferdinand"));
        livros.add(new Livro("Futebol", "Globinho"));
        control.saveLivrosToFile(LIVROS_FILE, livros);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton listarAcervoButton = new JButton("Listar Acervo");
        listarAcervoButton.setBounds(100, 50, 200, 30);
        panel.add(listarAcervoButton);

        JButton listarEmprestimosButton = new JButton("Listar Empréstimo");
        listarEmprestimosButton.setBounds(100, 100, 200, 30);
        panel.add(listarEmprestimosButton);

        JButton listarReservasButton = new JButton("Listar Reserva");
        listarReservasButton.setBounds(100, 150, 200, 30);
        panel.add(listarReservasButton);

        JButton detalhesObraButton = new JButton("Detalhes da Obra");
        detalhesObraButton.setBounds(100, 200, 200, 30);
        panel.add(detalhesObraButton);

        JButton visualizarEmprestimoButton = new JButton("Visualizar Empréstimo");
        visualizarEmprestimoButton.setBounds(100, 250, 200, 30);
        panel.add(visualizarEmprestimoButton);

        JButton visualizarReservaButton = new JButton("Visualizar Reservas");
        visualizarReservaButton.setBounds(100, 300, 200, 30);
        panel.add(visualizarReservaButton);

        JButton buscarEmprestimoButton = new JButton("Buscar Empréstimo");
        buscarEmprestimoButton.setBounds(100, 350, 200, 30);
        panel.add(buscarEmprestimoButton);

        JButton buscarObraButton = new JButton("Buscar Obra");
        buscarObraButton.setBounds(100, 400, 200, 30);
        panel.add(buscarObraButton);

        JButton verificarReservaButton = new JButton("Verificar Reserva de Livro");
        verificarReservaButton.setBounds(100, 450, 200, 30);
        panel.add(verificarReservaButton);

        JButton depositarTrabalhoButton = new JButton("Depositar Trabalho");
        depositarTrabalhoButton.setBounds(100, 500, 200, 30);
        panel.add(depositarTrabalhoButton);

        listarAcervoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder acervo = new StringBuilder("Acervo:\n");
                for (Livro l : livros) {
                    acervo.append(l.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, acervo.toString());
            }
        });

        listarEmprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emprestimos.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Não há empréstimos registrados.");
                    return;
                }

                StringBuilder detalhesEmprestimos = new StringBuilder("Detalhes dos Empréstimos:\n");
                for (Emprestimos emprestimo : emprestimos) {
                    detalhesEmprestimos.append("ID: ").append(emprestimo.getId()).append("\n")
                            .append("Livro: ").append(emprestimo.getLivros().getTitulo()).append("\n")
                            .append("Usuário: ").append(emprestimo.getPessoa().getNome()).append("\n")
                            .append("Data do Empréstimo: ").append(emprestimo.getDataEmprestimo()).append("\n")
                            .append("Data de Devolução: ").append(emprestimo.getDataDevolucao()).append("\n")
                            .append("Devolvido: ").append(emprestimo.isDevolvido() ? "Sim" : "Não").append("\n")
                            .append("-----------------------------------\n");
                }

                // Exibe os detalhes em uma janela de diálogo
                JOptionPane.showMessageDialog(null, detalhesEmprestimos.toString());
            }
        });

        listarReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para exibir as reservas
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Cria uma área de texto para mostrar os detalhes das reservas
                JTextArea reservasTextArea = new JTextArea(20, 40);
                reservasTextArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(reservasTextArea);
                panel.add(scrollPane);

                // Coleta os detalhes das reservas
                StringBuilder detalhes = new StringBuilder();
                for (Reserva reserva : reservas) {
                    detalhes.append("ID: ").append(reserva.getId()).append("\n")
                            .append("Livro: ").append(reserva.getLivros().getTitulo()).append("\n")
                            .append("Usuário: ").append(reserva.getPessoa().getNome()).append("\n")
                            .append("Data da Reserva: ").append(reserva.getDataReserva()).append("\n")
                            .append("Hora da Reserva: ").append(reserva.getHoraReserva()).append("\n")
                            .append("Status: ").append(reserva.getStatus()).append("\n")
                            .append("-------------\n");
                }

                // Atualiza o JTextArea com os detalhes das reservas
                reservasTextArea.setText(detalhes.toString());

                // Exibe o diálogo com os detalhes das reservas
                JOptionPane.showMessageDialog(null, panel, "Listar Reservas", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        detalhesObraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para entrada de dados
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextField livroTituloField = new JTextField(20);
                panel.add(new JLabel("Digite o título do livro:"));
                panel.add(livroTituloField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Detalhes da Obra",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String livroTitulo = livroTituloField.getText();
                    Livro livroDetalhes = null;

                    // Busca o livro pelo título
                    for (Livro livro : livros) {
                        if (livro.getTitulo().equalsIgnoreCase(livroTitulo)) {
                            livroDetalhes = livro;
                            break;
                        }
                    }

                    if (livroDetalhes == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        return;
                    }

                    // Exibe os detalhes do livro
                    String detalhes = "Título: " + livroDetalhes.getTitulo() + "\n" +
                            "Autor: " + livroDetalhes.getAutores() + "\n" +
                            "Editora: " + livroDetalhes.getEditora() + "\n" +
                            "Edição: " + livroDetalhes.getEdicao() + "\n" +
                            "Número de Páginas: " + livroDetalhes.getNumFolhas() + "\n" +
                            "Disponível: " + (livroDetalhes.isDisponivel() ? "Sim" : "Não");

                    JOptionPane.showMessageDialog(null, detalhes, "Detalhes do Livro", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        visualizarEmprestimoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para exibir os empréstimos
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Cria uma área de texto para mostrar os detalhes dos empréstimos
                JTextArea emprestimosTextArea = new JTextArea(20, 40);
                emprestimosTextArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(emprestimosTextArea);
                panel.add(scrollPane);

                // Coleta os detalhes dos empréstimos
                StringBuilder detalhes = new StringBuilder();
                for (Emprestimos emprestimo : emprestimos) {
                    detalhes.append("ID: ").append(emprestimo.getId()).append("\n")
                            .append("Data do Empréstimo: ").append(emprestimo.getDataEmprestimo()).append("\n")
                            .append("Livro: ").append(emprestimo.getLivros().getTitulo()).append("\n")
                            .append("Usuário: ").append(emprestimo.getPessoa().getNome()).append("\n")
                            .append("Data de Devolução: ").append(emprestimo.getDataDevolucao()).append("\n")
                            .append("Devolvido: ").append(emprestimo.isDevolvido() ? "Sim" : "Não").append("\n")
                            .append("-------------\n");
                }

                // Atualiza o JTextArea com os detalhes dos empréstimos
                emprestimosTextArea.setText(detalhes.toString());

                // Exibe o diálogo com os detalhes dos empréstimos
                JOptionPane.showMessageDialog(null, panel, "Visualizar Empréstimos", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // Adicione o botão para verificar reservas de um livro

        // Adicione o ActionListener para o novo botão
        verificarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para entrada de dados
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextField livroTituloField = new JTextField(20);
                panel.add(new JLabel("Digite o título do livro:"));
                panel.add(livroTituloField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Verificar Reserva de Livro",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String livroTitulo = livroTituloField.getText();
                    Livro livroEncontrado = null;

                    // Busca o livro pelo título
                    for (Livro livro : livros) {
                        if (livro.getTitulo().equalsIgnoreCase(livroTitulo)) {
                            livroEncontrado = livro;
                            break;
                        }
                    }

                    if (livroEncontrado == null) {
                        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
                        return;
                    }

                    // Verifica se o livro está reservado
                    boolean livroReservado = false;
                    for (Reserva reserva : reservas) {
                        if (reserva.getLivros().equals(livroEncontrado) && reserva.isAtiva()) {
                            livroReservado = true;
                            break;
                        }
                    }

                    String mensagem = livroReservado ? "O livro está reservado." : "O livro não está reservado.";
                    JOptionPane.showMessageDialog(null, mensagem, "Status da Reserva", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        depositarTrabalhoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria o painel para coletar informações do trabalho
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                // Campos para inserção de dados
                JTextField tituloField = new JTextField(20);
                JTextField faculdadeField = new JTextField(20);
                JTextField dataConclusaoField = new JTextField(20);
                JTextField alunoField = new JTextField(20);
                JTextField orientadorField = new JTextField(20);
                JTextField cursoField = new JTextField(20);
                JTextField localArquivoField = new JTextField(20);
                JTextField scoreField = new JTextField(20);
                JTextField votosField = new JTextField(20);

                // Adiciona os campos ao painel
                panel.add(new JLabel("Título do Trabalho:"));
                panel.add(tituloField);
                panel.add(new JLabel("Faculdade:"));
                panel.add(faculdadeField);
                panel.add(new JLabel("Data de Conclusão:"));
                panel.add(dataConclusaoField);
                panel.add(new JLabel("Aluno:"));
                panel.add(alunoField);
                panel.add(new JLabel("Orientador:"));
                panel.add(orientadorField);
                panel.add(new JLabel("Curso:"));
                panel.add(cursoField);
                panel.add(new JLabel("Local do Arquivo:"));
                panel.add(localArquivoField);
                panel.add(new JLabel("Score:"));
                panel.add(scoreField);
                panel.add(new JLabel("Quantidade de Votos:"));
                panel.add(votosField);

                // Exibe o diálogo para coleta de dados
                int result = JOptionPane.showConfirmDialog(null, panel, "Inserir Detalhes do Trabalho",
                        JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Cria um novo trabalho com os dados inseridos
                        String titulo = tituloField.getText();
                        String faculdadeNome = faculdadeField.getText();
                        String dataConclusao = dataConclusaoField.getText();
                        String alunoNome = alunoField.getText();
                        String orientadorNome = orientadorField.getText();
                        String cursoNome = cursoField.getText();
                        int score = Integer.parseInt(scoreField.getText());
                        int quantidadeVotos = Integer.parseInt(votosField.getText());
                        Faculdade faculdade = new Faculdade(faculdadeNome);
                        Aluno aluno = new Aluno(alunoNome);
                        Orientador orientador = new Orientador(orientadorNome);
                        Curso curso = new Curso(cursoNome); 

                        // Cria um novo trabalho
                        Trabalho trabalho = new Trabalho(titulo, faculdade, dataConclusao, aluno, orientador, curso, score, quantidadeVotos);

                        trabalhos.add(trabalho);
                        control.saveTrabalhosToFile(TRABALHO_FILE, trabalhos);
                        

                        JOptionPane.showMessageDialog(null, "Trabalho registrado com sucesso!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Por favor, insira valores válidos para score e quantidade de votos.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buscarObraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria um painel para coletar o título da obra
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JTextField tituloField = new JTextField(20);
                panel.add(new JLabel("Digite o título da obra:"));
                panel.add(tituloField);

                // Exibe o diálogo para coletar o título da obra
                int result = JOptionPane.showConfirmDialog(null, panel, "Buscar Obra", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String titulo = tituloField.getText().trim();

                    if (titulo.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "O título não pode estar vazio.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Procura o livro com o título fornecido
                    Livro livroEncontrado = null;
                    for (Livro livro : livros) { // Supondo que 'livros' é a lista de livros disponíveis
                        if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                            livroEncontrado = livro;
                            break;
                        }
                    }

                    // Exibe os detalhes do livro encontrado ou uma mensagem de erro se não
                    // encontrado
                    if (livroEncontrado != null) {
                        String detalhes = "Título: " + livroEncontrado.getTitulo() + "\n" +
                                "Autor: " + livroEncontrado.getAutores() + "\n" +
                                "Editora: " + livroEncontrado.getEditora() + "\n" +
                                "Edição: " + livroEncontrado.getEdicao() + "\n" +
                                "Número de Folhas: " + livroEncontrado.getNumFolhas() + "\n" +
                                "Disponível: " + (livroEncontrado.isDisponivel() ? "Sim" : "Não");

                        JOptionPane.showMessageDialog(null, detalhes, "Detalhes da Obra",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Obra não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaUsuario().setVisible(true);
        });

    }
}
