package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Controller.Controller;
import Obras.Livro;

public class GerenciadorLivro extends JFrame {

    private Controller control;

    public GerenciadorLivro(Controller control) {
        this.control = control;
        setTitle("Gerenciador de Livros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Componentes da tela
        JTextField tituloField = new JTextField(20);
        JTextField autorField = new JTextField(20);
        JTextField isbnField = new JTextField(20);

        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Autor:"));
        panel.add(autorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);

        JButton salvarButton = new JButton("Salvar Livro");
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                String autor = autorField.getText();

                // Aqui estou assumindo que a classe Livro tem um construtor que aceita esses parâmetros
                Livro livro = new Livro(titulo, autor);
                control.addLivro(livro);
                JOptionPane.showMessageDialog(null, "Livro salvo com sucesso!");
            }
        });
        panel.add(salvarButton);

        JButton listarButton = new JButton("Listar Livros");
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Livro> livros = control.getAllLivros();
                for (Livro livro : livros) {
                    System.out.println("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutores());
                }
            }
        });
        panel.add(listarButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(null); // Substitua por uma conexão real
            new GerenciadorLivro(controller).setVisible(true);
        });
    }
}