package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import Controller.Controller;
import Obras.Livro;

public class GerenciadorLivro extends JFrame {
    Controller control;
    public static final String LIVROS_FILE = "C:\\temp\\livros.txt";
    public static ArrayList<Livro> livros;

    private JTextField tituloField, autoresField, areaField, anoField, editoraField, edicaoField, numFolhasField;
    private JCheckBox digitalCheckBox, disponivelCheckBox;

    public GerenciadorLivro() {
        setTitle("Gerenciar Livros");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel);

        livros = new ArrayList<>();

        // Formulário de Cadastro de Livro
        JLabel tituloLabel = new JLabel("Título:");
        panel.add(tituloLabel);
        tituloField = new JTextField(20);
        panel.add(tituloField);

        JLabel autoresLabel = new JLabel("Autores:");
        panel.add(autoresLabel);
        autoresField = new JTextField(20);
        panel.add(autoresField);

        JLabel areaLabel = new JLabel("Área:");
        panel.add(areaLabel);
        areaField = new JTextField(20);
        panel.add(areaField);

        JLabel anoLabel = new JLabel("Ano:");
        panel.add(anoLabel);
        anoField = new JTextField(20);
        panel.add(anoField);

        JLabel editoraLabel = new JLabel("Editora:");
        panel.add(editoraLabel);
        editoraField = new JTextField(20);
        panel.add(editoraField);

        JLabel edicaoLabel = new JLabel("Edição:");
        panel.add(edicaoLabel);
        edicaoField = new JTextField(20);
        panel.add(edicaoField);

        JLabel numFolhasLabel = new JLabel("Número de Folhas:");
        panel.add(numFolhasLabel);
        numFolhasField = new JTextField(20);
        panel.add(numFolhasField);

        JLabel digitalLabel = new JLabel("Digital:");
        panel.add(digitalLabel);
        digitalCheckBox = new JCheckBox();
        panel.add(digitalCheckBox);

        JLabel disponivelLabel = new JLabel("Disponível:");
        panel.add(disponivelLabel);
        disponivelCheckBox = new JCheckBox();
        panel.add(disponivelCheckBox);

        // Botões para Gerenciamento
        JButton cadastrarButton = new JButton("Cadastrar Livro");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                String autores = autoresField.getText();
                String area = areaField.getText();
                String ano = anoField.getText();
                String editora = editoraField.getText();
                String edicao = edicaoField.getText();
                int numFolhas = Integer.parseInt(numFolhasField.getText());
                boolean digital = digitalCheckBox.isSelected();
                boolean disponivel = disponivelCheckBox.isSelected();

                Livro livro = new Livro(titulo, autores, area, editora, ano, edicao, numFolhas, disponivel, digital);
                livros.add(livro);
                control.saveLivrosToFile(LIVROS_FILE, livros);
                clearFields();
            }
        });
        panel.add(cadastrarButton);

        JButton removerButton = new JButton("Remover Livro");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                livros.removeIf(l -> l.getTitulo().equals(titulo));
                control.saveLivrosToFile(LIVROS_FILE, livros);
                clearFields();
            }
        });
        panel.add(removerButton);

        JButton atualizarButton = new JButton("Atualizar Livro");
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                for (Livro l : livros) {
                    if (l.getTitulo().equals(titulo)) {
                        l.setAutores(autoresField.getText());
                        l.setArea(areaField.getText());
                        l.setAno(anoField.getText());
                        l.setEditora(editoraField.getText());
                        l.setEdicao(edicaoField.getText());
                        l.setNumFolhas(Integer.parseInt(numFolhasField.getText()));
                        l.setDigital(digitalCheckBox.isSelected());
                        l.setDisponivel(disponivelCheckBox.isSelected());
                        control.saveLivrosToFile(LIVROS_FILE, livros);
                        break;
                    }
                }
                clearFields();
            }
        });
        panel.add(atualizarButton);
    }

    private void clearFields() {
        tituloField.setText("");
        autoresField.setText("");
        areaField.setText("");
        anoField.setText("");
        editoraField.setText("");
        edicaoField.setText("");
        numFolhasField.setText("");
        digitalCheckBox.setSelected(false);
        disponivelCheckBox.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GerenciadorLivro().setVisible(true);
        });
    }
}
