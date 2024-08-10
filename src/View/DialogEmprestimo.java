package View;

import javax.swing.*;

import Controller.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import Obras.Livro;
import Usuarios.Pessoa;
import Emprest.Emprestimos;

public class DialogEmprestimo extends JDialog {
    private JTextField tfLivroTitulo;
    private JTextField tfUsuarioNome;
    private JComboBox<String> cbUsuarioTipo;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private ArrayList<Livro> livros;
    private ArrayList<Pessoa> usuarios;
    private ArrayList<Emprestimos> emprestimos;
    private Controller control;
    
    public DialogEmprestimo(Frame owner, ArrayList<Livro> livros, ArrayList<Pessoa> usuarios, ArrayList<Emprestimos> emprestimos, Controller control) {
        super(owner, "Novo Empréstimo", true);
        this.livros = livros;
        this.usuarios = usuarios;
        this.emprestimos = emprestimos;
        this.control = control;
        
        setLayout(new GridLayout(4, 2));
        setSize(300, 200);
        setLocationRelativeTo(owner);

        add(new JLabel("Título do Livro:"));
        tfLivroTitulo = new JTextField();
        add(tfLivroTitulo);

        add(new JLabel("Nome do Usuário:"));
        tfUsuarioNome = new JTextField();
        add(tfUsuarioNome);

        add(new JLabel("Tipo de Usuário:"));
        cbUsuarioTipo = new JComboBox<>(new String[]{"Aluno", "Funcionario", "Orientador"});
        add(cbUsuarioTipo);

        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        add(btnConfirmar);
        add(btnCancelar);

        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleConfirmar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void handleConfirmar() {
        String livroTitulo = tfLivroTitulo.getText();
        String usuarioNome = tfUsuarioNome.getText();
        
        Livro livroEmprestimo = null;
        Pessoa usuarioEmprestimo = null;

        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(livroTitulo)) {
                livroEmprestimo = livro;
                break;
            }
        }

        for (Pessoa usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(usuarioNome)) {
                usuarioEmprestimo = usuario;
                break;
            }
        }

        if (livroEmprestimo == null) {
            JOptionPane.showMessageDialog(this, "Livro não encontrado.");
            return;
        }

        if (usuarioEmprestimo == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            return;
        }

        if (!livroEmprestimo.isDisponivel()) {
            JOptionPane.showMessageDialog(this, "O livro não está disponível para empréstimo.");
            return;
        }

        // Cria o novo empréstimo
        Emprestimos novoEmprestimo = new Emprestimos(usuarioEmprestimo, livroEmprestimo, LocalDate.now());
        emprestimos.add(novoEmprestimo);
        livroEmprestimo.setDisponivel(false); // Atualiza a disponibilidade do livro

        // Salva os dados atualizados
        control.saveEmprestimosToFile(TelaFuncionario.EMPRESTIMOS_FILE, emprestimos);
        control.saveLivrosToFile(TelaFuncionario.LIVROS_FILE, livros);

        JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso.");
        dispose();
    }
}
