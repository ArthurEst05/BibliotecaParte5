
package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JButton btnLogin;
    private JButton btnListarAcervo;
    private JButton btnListarEmprestimos;
    private JButton btnListarReservas;
    private JButton btnCadastrarObra;
    private JButton btnCadastrarUsuario;

    public TelaPrincipal() {
        setTitle("Sistema de Biblioteca");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnLogin = new JButton("Login");
        btnListarAcervo = new JButton("Listar Acervo");
        btnListarEmprestimos = new JButton("Listar Empréstimos");
        btnListarReservas = new JButton("Listar Reservas");
        btnCadastrarObra = new JButton("Cadastrar Obra");
        btnCadastrarUsuario = new JButton("Cadastrar Usuário");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(btnLogin);
        panel.add(btnListarAcervo);
        panel.add(btnListarEmprestimos);
        panel.add(btnListarReservas);
        panel.add(btnCadastrarObra);
        panel.add(btnCadastrarUsuario);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de login
            }
        });

        btnListarAcervo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de listar acervo
            }
        });

        btnListarEmprestimos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de listar empréstimos
            }
        });

        btnListarReservas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de listar reservas
            }
        });

        btnCadastrarObra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de cadastrar obra
            }
        });

        btnCadastrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para abrir tela de cadastrar usuário
            }
        });
    }

    public static void main(String[] args) {
        TelaPrincipal telaPrincipal = new TelaPrincipal();
        telaPrincipal.setVisible(true);
    }
}
