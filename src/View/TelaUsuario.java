package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import Obras.Livro;
import Usuarios.Aluno;
import Usuarios.Orientador;
import Faculdade.Curso;
import Faculdade.Faculdade;
import Faculdade.Trabalho;

public class TelaUsuario extends JFrame {
    private Controller control;
    private List<Livro> livros;
    private List<Emprestimos> emprestimos;
    private List<Reserva> reservas;

    public TelaUsuario(Controller control) {
        this.control = control;
        this.livros = control.getAllLivros();
        this.emprestimos = control.getAllEmprestimos();
        this.reservas = control.getAllReservas();

        setTitle("Tela Usuario");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton listarAcervoButton = new JButton("Listar Acervo");
        listarAcervoButton.setBounds(100, 50, 200, 30);
        panel.add(listarAcervoButton);

        JButton listarEmprestimosButton = new JButton("Listar Empréstimos");
        listarEmprestimosButton.setBounds(100, 100, 200, 30);
        panel.add(listarEmprestimosButton);

        JButton listarReservasButton = new JButton("Listar Reservas");
        listarReservasButton.setBounds(100, 150, 200, 30);
        panel.add(listarReservasButton);

        JButton detalhesObraButton = new JButton("Detalhes da Obra");
        detalhesObraButton.setBounds(100, 200, 200, 30);
        panel.add(detalhesObraButton);

        JButton visualizarEmprestimoButton = new JButton("Visualizar Empréstimo");
        visualizarEmprestimoButton.setBounds(100, 250, 200, 30);
        panel.add(visualizarEmprestimoButton);

        JButton visualizarReservaButton = new JButton("Visualizar Reserva");
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
                List<Livro> livros = control.getAllLivros();
                StringBuilder acervo = new StringBuilder("Lista de Obras:\n");
                for (Livro livro : livros) {
                    acervo.append(livro.getTitulo()).append("\n");
                }
                JOptionPane.showMessageDialog(null, acervo.toString(), "Acervo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        listarEmprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Emprestimos> emprestimos = control.getAllEmprestimos();
                StringBuilder listaEmprestimos = new StringBuilder("Lista de Empréstimos:\n");
                for (Emprestimos emprestimo : emprestimos) {
                    listaEmprestimos.append("Livro: ").append(emprestimo.getLivros().getTitulo())
                            .append(", Usuário: ").append(emprestimo.getPessoa().getNome())
                            .append(", Devolvido: ").append(emprestimo.isDevolvido() ? "Sim" : "Não").append("\n");
                }
                JOptionPane.showMessageDialog(null, listaEmprestimos.toString(), "Empréstimos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        listarReservasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Reserva> reservas = control.getAllReservas();
                StringBuilder listaReservas = new StringBuilder("Lista de Reservas:\n");
                for (Reserva reserva : reservas) {
                    listaReservas.append("Livro: ").append(reserva.getLivros().getTitulo())
                            .append(", Usuário: ").append(reserva.getPessoa().getNome()).append("\n");
                }
                JOptionPane.showMessageDialog(null, listaReservas.toString(), "Reservas", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        verificarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField idField = new JTextField(20);
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
                inputPanel.add(new JLabel("Digite o ID da reserva:"));
                inputPanel.add(idField);
        
                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Verificar Reserva", JOptionPane.OK_CANCEL_OPTION);
        
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int id = Integer.parseInt(idField.getText().trim());
                        Reserva reservaEncontrada = control.getReservaById(id);
        
                        if (reservaEncontrada != null) {
                            String detalhes = "Título: " + reservaEncontrada.getLivros().getTitulo() + "\\n" +
                                    "Usuário: " + reservaEncontrada.getPessoa().getNome();
        
                            JOptionPane.showMessageDialog(null, detalhes, "Detalhes da Reserva", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Reserva não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor, insira um ID válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

       depositarTrabalhoButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField tituloField = new JTextField(20);
        JTextField faculdadeIdField = new JTextField(20);  // Espera o ID da faculdade
        JTextField dataConclusaoField = new JTextField(20);
        JTextField alunoIdField = new JTextField(20);  // Espera o ID do aluno
        JTextField orientadorIdField = new JTextField(20);  // Espera o ID do orientador
        JTextField cursoIdField = new JTextField(20);  // Espera o ID do curso

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

                // Salvando o trabalho no banco de dados usando o controlador
                Trabalho trabalho = new Trabalho(titulo, faculdade, dataConclusao, aluno, orientador, curso, 0, 0);
                control.addTrabalho(trabalho);

                JOptionPane.showMessageDialog(null, "Trabalho depositado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira IDs válidos para Faculdade, Aluno, Orientador e Curso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});
    }
}