package View;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    private Controller control;

    public TelaUsuario(Controller control) {
        this.control = control;

        setTitle("Tela Usuario");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        addButton(panel, "Depositar Trabalho", 50, e -> depositarTrabalho());
        addButton(panel, "Listar Acervo", 100, e -> listarAcervo());
        addButton(panel, "Listar Empréstimos", 150, e -> listarEmprestimos());
        addButton(panel, "Listar Reservas", 200, e -> listarReservas());
        addButton(panel, "Detalhes da Obra", 250, e -> detalhesObra());
        addButton(panel, "Visualizar Empréstimo", 300, e -> visualizarEmprestimo());
        addButton(panel, "Fazer Reserva", 350, e -> fazerReserva());
    }

    private void addButton(JPanel panel, String text, int yPosition, ActionListener action) {
        JButton button = new JButton(text);
        button.setBounds(100, yPosition, 200, 30);
        button.addActionListener(action);
        panel.add(button);
    }

    private void depositarTrabalho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField tituloField = new JTextField(20);
        JTextField faculdadeIdField = new JTextField(20);
        JTextField dataConclusaoField = new JTextField(20);
        JTextField alunoIdField = new JTextField(20);
        JTextField orientadorIdField = new JTextField(20);
        JTextField cursoIdField = new JTextField(20);

        panel.add(new JLabel("Título do Trabalho:"));
        panel.add(tituloField);
        panel.add(new JLabel("ID da Faculdade:"));
        panel.add(faculdadeIdField);
        panel.add(new JLabel("Data de Conclusão:"));
        panel.add(dataConclusaoField);
        panel.add(new JLabel("ID do Aluno:"));
        panel.add(alunoIdField);
        panel.add(new JLabel("ID do Orientador:"));
        panel.add(orientadorIdField);
        panel.add(new JLabel("ID do Curso:"));
        panel.add(cursoIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Depositar Trabalho", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String titulo = tituloField.getText().trim();
                int faculdadeId = Integer.parseInt(faculdadeIdField.getText().trim());
                String dataConclusao = dataConclusaoField.getText().trim();
                int alunoId = Integer.parseInt(alunoIdField.getText().trim());
                int orientadorId = Integer.parseInt(orientadorIdField.getText().trim());
                int cursoId = Integer.parseInt(cursoIdField.getText().trim());

                Faculdade faculdade = control.getFaculdadeById(faculdadeId);
                Aluno aluno = (Aluno) control.getPessoaById(alunoId);
                Orientador orientador = control.getOrientadorById(orientadorId);
                Curso curso = control.getCursoById(cursoId);

                Trabalho trabalho = new Trabalho(titulo, faculdade, dataConclusao, aluno, orientador, curso, 0, 0);
                control.addTrabalho(trabalho);

                JOptionPane.showMessageDialog(null, "Trabalho depositado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira IDs válidos para Faculdade, Aluno, Orientador e Curso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void listarAcervo() {
        List<Livro> livros = control.getAllLivros();
        StringBuilder acervo = new StringBuilder("Lista de Obras:\n");
        for (Livro livro : livros) {
            acervo.append(livro.getTitulo()).append("\n");
        }
        JOptionPane.showMessageDialog(null, acervo.toString(), "Acervo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarEmprestimos() {
        List<Emprestimos> emprestimos = control.getAllEmprestimos();
        StringBuilder listaEmprestimos = new StringBuilder("Lista de Empréstimos:\n");
        for (Emprestimos emprestimo : emprestimos) {
            listaEmprestimos.append("Livro: ").append(emprestimo.getLivros().getTitulo())
                    .append(", Usuário: ").append(emprestimo.getPessoa().getNome())
                    .append(", Devolvido: ").append(emprestimo.isDevolvido() ? "Sim" : "Não").append("\n");
        }
        JOptionPane.showMessageDialog(null, listaEmprestimos.toString(), "Empréstimos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listarReservas() {
        List<Reserva> reservas = control.getAllReservas();
        
        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma reserva encontrada.", "Reservas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        StringBuilder listaReservas = new StringBuilder("Lista de Reservas:\n");
        reservas.forEach(reserva -> 
            listaReservas.append("Livro: ").append(reserva.getLivro().getTitulo())
                          .append(", Usuário: ").append(reserva.getUsuario().getNome())
                          .append(", Data da Reserva: ").append(reserva.getDataReserva())
                          .append(", Status: ").append(reserva.getStatus())
                          .append("\n")
        );
    
        JOptionPane.showMessageDialog(null, listaReservas.toString(), "Reservas", JOptionPane.INFORMATION_MESSAGE);
    }
    

    private void detalhesObra() {
        JTextField tituloField = new JTextField(20);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Digite o título do livro:"));
        inputPanel.add(tituloField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Buscar Detalhes da Obra", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String titulo = tituloField.getText().trim();
            Livro livroEncontrado = control.getLivroByTitulo(titulo);

            if (livroEncontrado != null) {
                String detalhes = "Título: " + livroEncontrado.getTitulo() + "\n" +
                        "Autores: " + livroEncontrado.getAutores() + "\n" +
                        "Área: " + livroEncontrado.getArea() + "\n" +
                        "Ano: " + livroEncontrado.getAno() + "\n" +
                        "Editora: " + livroEncontrado.getEditora() + "\n" +
                        "Edição: " + livroEncontrado.getEdicao() + "\n" +
                        "Número de Páginas: " + livroEncontrado.getNumFolhas() + "\n" +
                        "Disponível: " + (livroEncontrado.isDisponivel() ? "Sim" : "Não") + "\n" +
                        "Digital: " + (livroEncontrado.isDigital() ? "Sim" : "Não");

                JOptionPane.showMessageDialog(null, detalhes, "Detalhes do Livro", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Livro não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void visualizarEmprestimo() {
        JTextField idField = new JTextField(20);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JLabel("Digite o ID do empréstimo:"));
        inputPanel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Buscar Empréstimo", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                Emprestimos emprestimoEncontrado = control.getEmprestimoById(id);

                if (emprestimoEncontrado != null) {
                    String detalhes = "ID do Empréstimo: " + emprestimoEncontrado.getId() + "\n" +
                            "Livro: " + emprestimoEncontrado.getLivros().getTitulo() + "\n" +
                            "Usuário: " + emprestimoEncontrado.getPessoa().getNome() + "\n" +
                            "Data de Empréstimo: " + emprestimoEncontrado.getDataEmprestimo() + "\n" +
                            "Data de Devolução: " + emprestimoEncontrado.getDataDevolucao() + "\n" +
                            "Devolvido: " + (emprestimoEncontrado.isDevolvido() ? "Sim" : "Não");

                    JOptionPane.showMessageDialog(null, detalhes, "Detalhes do Empréstimo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Empréstimo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

     private void fazerReserva() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField livroIdField = new JTextField(20);
        JTextField usuarioIdField = new JTextField(20);

        panel.add(new JLabel("ID do Livro:"));
        panel.add(livroIdField);
        panel.add(new JLabel("ID do Usuário:"));
        panel.add(usuarioIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Fazer Reserva", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int livroId = Integer.parseInt(livroIdField.getText().trim());
                int usuarioId = Integer.parseInt(usuarioIdField.getText().trim());

                Livro livro = control.getLivroById(livroId);
                Pessoa usuario = control.getPessoaById(usuarioId);

                if (livro != null && usuario != null) {
                    Reserva reserva = new Reserva(usuario, livro, LocalDate.now());
                    control.addReserva(reserva);

                    JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Livro ou Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira IDs válidos para Livro e Usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
