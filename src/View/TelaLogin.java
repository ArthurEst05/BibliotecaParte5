package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import Usuarios.Pessoa;


public class TelaLogin extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private Pessoa usuarioLogado;

    public Pessoa mostrar() {
        this.setVisible(true);
        return usuarioLogado;
    }
    

    public TelaLogin() {
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

                String tipoUsuario = autenticar(usuario, senha);

                if (usuario.equals("adm") && senha.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                }
        
                switch (tipoUsuario) {
                    case "funcionario":
                        JOptionPane.showMessageDialog(null, "Login bem-sucedido como Funcionário!");
                        dispose();
                        TelaFuncionario telaFuncionario = new TelaFuncionario();
                        telaFuncionario.setVisible(true);
                        break;
                    case "comum":
                        JOptionPane.showMessageDialog(null, "Login bem-sucedido como Usuário Comum!");
                        dispose();
                        TelaUsuario telaUsuario = new TelaUsuario();
                        telaUsuario.setVisible(true);
                        break;
                    case "invalido":
                        JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
                        break;
                }
            }
        });
    }

    private String autenticar(String usuario, String senha) {
        // Simulação de autenticação
        // Aqui você deve verificar o usuário e senha com base nos dados reais
        if (usuario.equals("adm") && senha.equals("1234")) {
            return "funcionario";
        } else if (usuario.equals("usuario") && senha.equals("senha")) {
            return "comum";
        } else {
            return "invalido";
        }
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
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.setVisible(true);
        });
    }
}
