package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import Controller.Controller;
import Emprest.Emprestimos;
import Obras.Livro;
import Usuarios.Pessoa;

public class DialogEmprestimo extends JDialog {

    private JComboBox<Livro> livroComboBox;
    private JComboBox<Pessoa> usuarioComboBox;
    private JButton emprestarButton;
    private Controller control;

    public DialogEmprestimo(JFrame parent, List<Livro> livros, List<Pessoa> usuarios, List<Emprestimos> emprestimos, Controller control) {
        super(parent, "Realizar Empréstimo", true);
        this.control = control;

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        livroComboBox = new JComboBox<>(livros.toArray(new Livro[0]));
        usuarioComboBox = new JComboBox<>(usuarios.toArray(new Pessoa[0]));
        emprestarButton = new JButton("Emprestar");

        panel.add(new JLabel("Selecione o livro:"));
        panel.add(livroComboBox);
        panel.add(new JLabel("Selecione o usuário:"));
        panel.add(usuarioComboBox);
        panel.add(emprestarButton);

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Livro livroSelecionado = (Livro) livroComboBox.getSelectedItem();
                Pessoa usuarioSelecionado = (Pessoa) usuarioComboBox.getSelectedItem();

                if (livroSelecionado != null && usuarioSelecionado != null) {
                    Emprestimos novoEmprestimo = new Emprestimos(usuarioSelecionado, livroSelecionado, null);
                    emprestimos.add(novoEmprestimo);
                    control.addEmprestimo(novoEmprestimo);

                    livroSelecionado.setDisponivel(false);
                    control.updateLivro(livroSelecionado);

                    JOptionPane.showMessageDialog(DialogEmprestimo.this, "Empréstimo realizado com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(DialogEmprestimo.this, "Selecione um livro e um usuário.");
                }
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}