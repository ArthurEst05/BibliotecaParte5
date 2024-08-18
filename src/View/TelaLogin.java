package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Controller.Controller;
import DB.DB;
import Usuarios.Pessoa;

public class TelaLogin extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private Controller control; // Usar GeneralController

    public TelaLogin(Controller control) {
        this.control = control; // Inicialize o controlador
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String senha = new String(senhaField.getPassword());

                Pessoa pessoa = control.getPessoaByUsernameAndPassword(usuario, senha);

                if (pessoa != null) {
                    String tipo = pessoa.getTipo();

                    switch (tipo) {
                        case "Funcionario":
                            JOptionPane.showMessageDialog(null, "Login bem-sucedido como Funcionário!");
                            dispose();
                            TelaFuncionario telaFuncionario = new TelaFuncionario(control);
                            telaFuncionario.setVisible(true);
                            break;
                        case "Aluno":
                            JOptionPane.showMessageDialog(null, "Login bem-sucedido como Aluno!");
                            dispose();
                            TelaUsuario telaAluno = new TelaUsuario(control);
                            telaAluno.setVisible(true);
                            break;
                        case "Orientador":
                            JOptionPane.showMessageDialog(null, "Login bem-sucedido como Orientador!");
                            dispose();
                            TelaUsuario telaOrientador = new TelaUsuario(control);
                            telaOrientador.setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Tipo de usuário desconhecido.");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                }
            }
        });
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioLabel.setBounds(10, 20, 80, 25);
        panel.add(usuarioLabel);

        usuarioField = new JTextField(20);
        usuarioField.setBounds(100, 20, 165, 25);
        panel.add(usuarioField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(10, 50, 80, 25);
        panel.add(senhaLabel);

        senhaField = new JPasswordField(20);
        senhaField.setBounds(100, 50, 165, 25);
        panel.add(senhaField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        panel.add(loginButton);
    }

    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        Connection conn = DB.getConnection();
        
        if (conn != null) {
            Controller controller = new Controller(conn);
            TelaLogin telaLogin = new TelaLogin(controller);
            telaLogin.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    });
}
}
