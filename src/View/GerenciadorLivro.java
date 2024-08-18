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
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Componentes da tela para adicionar um novo livro
        JTextField tituloField = new JTextField(20);
        JTextField autorField = new JTextField(20);
        JTextField areaField = new JTextField(20);
        JTextField editoraField = new JTextField(20);
        JTextField anoField = new JTextField(20);
        JTextField edicaoField = new JTextField(20);
        JTextField numFolhasField = new JTextField(20);
        JCheckBox disponivelCheckBox = new JCheckBox("Disponível");
        JCheckBox digitalCheckBox = new JCheckBox("Digital");

        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Autor:"));
        panel.add(autorField);
        panel.add(new JLabel("Área:"));
        panel.add(areaField);
        panel.add(new JLabel("Editora:"));
        panel.add(editoraField);
        panel.add(new JLabel("Ano:"));
        panel.add(anoField);
        panel.add(new JLabel("Edição:"));
        panel.add(edicaoField);
        panel.add(new JLabel("Número de Folhas:"));
        panel.add(numFolhasField);
        panel.add(disponivelCheckBox);
        panel.add(digitalCheckBox);

        JButton salvarButton = new JButton("Salvar Livro");
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = tituloField.getText();
                    String autor = autorField.getText();
                    String area = areaField.getText();
                    String editora = editoraField.getText();
                    String ano = anoField.getText();
                    String edicao = edicaoField.getText();
                    int numFolhas = Integer.parseInt(numFolhasField.getText());
                    boolean disponivel = disponivelCheckBox.isSelected();
                    boolean digital = digitalCheckBox.isSelected();

                    // Criação do objeto Livro
                    Livro livro = new Livro(titulo, autor, area, editora, ano, edicao, numFolhas, disponivel, digital);

                    // Salvando o livro no controlador
                    control.addLivro(livro);
                    JOptionPane.showMessageDialog(null, "Livro salvo com sucesso!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o número de folhas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(salvarButton);

        JButton listarButton = new JButton("Listar Livros");
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Livro> livros = control.getAllLivros();
                StringBuilder listaLivros = new StringBuilder("Livros:\n");
                for (Livro livro : livros) {
                    listaLivros.append(livro.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, listaLivros.toString(), "Lista de Livros", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(listarButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(null); 
            new GerenciadorLivro(controller).setVisible(true);
        });
    }
}
