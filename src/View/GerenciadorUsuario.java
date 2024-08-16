package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import Controller.Controller;
import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;

public class GerenciadorUsuario extends JFrame {
    Controller control;
    public static ArrayList<Pessoa> usuarios;
    private JTextField nomeField, idadeField, sexoField, telefoneField, senhaField;
    private JTextField cargoField, salarioField, enderecoField, instituicaoField, matriculaField;
    private JTextField disciplinaField, grauAcademicoField, emailField;

    public GerenciadorUsuario(Controller control) {
        this.control = control;
        setTitle("Gerenciar Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        add(panel);

        usuarios = new ArrayList<>();

        // Formulário de Cadastro de Usuário
        JLabel nomeLabel = new JLabel("Nome:");
        panel.add(nomeLabel);
        nomeField = new JTextField(20);
        panel.add(nomeField);

        JLabel idadeLabel = new JLabel("Idade:");
        panel.add(idadeLabel);
        idadeField = new JTextField(20);
        panel.add(idadeField);

        JLabel sexoLabel = new JLabel("Sexo:");
        panel.add(sexoLabel);
        sexoField = new JTextField(20);
        panel.add(sexoField);

        JLabel telefoneLabel = new JLabel("Telefone:");
        panel.add(telefoneLabel);
        telefoneField = new JTextField(20);
        panel.add(telefoneField);

        JLabel senhaLabel = new JLabel("Senha:");
        panel.add(senhaLabel);
        senhaField = new JTextField(20);
        panel.add(senhaField);

        // Campos específicos para Aluno
        JLabel instituicaoLabel = new JLabel("Instituição Educacional:");
        panel.add(instituicaoLabel);
        instituicaoField = new JTextField(20);
        panel.add(instituicaoField);

        JLabel matriculaLabel = new JLabel("Matrícula:");
        panel.add(matriculaLabel);
        matriculaField = new JTextField(20);
        panel.add(matriculaField);

        // Campos específicos para Funcionário
        JLabel cargoLabel = new JLabel("Cargo:");
        panel.add(cargoLabel);
        cargoField = new JTextField(20);
        panel.add(cargoField);

        JLabel salarioLabel = new JLabel("Salário:");
        panel.add(salarioLabel);
        salarioField = new JTextField(20);
        panel.add(salarioField);

        JLabel enderecoLabel = new JLabel("Endereço:");
        panel.add(enderecoLabel);
        enderecoField = new JTextField(20);
        panel.add(enderecoField);

        // Campos específicos para Orientador
        JLabel disciplinaLabel = new JLabel("Disciplina:");
        panel.add(disciplinaLabel);
        disciplinaField = new JTextField(20);
        panel.add(disciplinaField);

        JLabel grauAcademicoLabel = new JLabel("Grau Acadêmico:");
        panel.add(grauAcademicoLabel);
        grauAcademicoField = new JTextField(20);
        panel.add(grauAcademicoField);

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);
        emailField = new JTextField(20);
        panel.add(emailField);

        // Botões para Gerenciamento
        JButton cadastrarButton = new JButton("Cadastrar Usuário");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                int idade = Integer.parseInt(idadeField.getText());
                String sexo = sexoField.getText();
                String telefone = telefoneField.getText();
                String senha = senhaField.getText();

                Pessoa usuario;

                if (!instituicaoField.getText().isEmpty()) { // Cadastro como Aluno
                    String instituicao = instituicaoField.getText();
                    String matricula = matriculaField.getText();
                    usuario = new Aluno(nome, idade, sexo, telefone, instituicao, matricula, senha);
                } else if (!cargoField.getText().isEmpty()) { // Cadastro como Funcionário
                    String cargo = cargoField.getText();
                    double salario = Double.parseDouble(salarioField.getText());
                    String endereco = enderecoField.getText();
                    usuario = new Funcionario(nome, idade, sexo, telefone, cargo, salario, endereco, senha);
                } else if (!disciplinaField.getText().isEmpty()) { // Cadastro como Orientador
                    String disciplina = disciplinaField.getText();
                    String grauAcademico = grauAcademicoField.getText();
                    String email = emailField.getText();
                    usuario = new Orientador(nome, idade, sexo, telefone, disciplina, grauAcademico, senha, email);
                } else { // Default to Aluno if no specific fields are filled
                    usuario = new Aluno(nome, idade, sexo, telefone, senha);
                }

                control.addPessoa(usuario);
                clearFields();
            }
        });
        panel.add(cadastrarButton);

        JButton removerButton = new JButton("Remover Usuário");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                Pessoa usuario = control.getPessoaById(Integer.parseInt(nome)); // Assumindo que nome é ID aqui
                if (usuario != null) {
                    control.deletePessoa(usuario.getId());
                }
                clearFields();
            }
        });
        panel.add(removerButton);

        JButton atualizarButton = new JButton("Atualizar Usuário");
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                Pessoa usuario = control.getPessoaById(Integer.parseInt(nome)); // Assumindo que nome é ID aqui
                if (usuario != null) {
                    usuario.setIdade(Integer.parseInt(idadeField.getText()));
                    usuario.setSexo(sexoField.getText());
                    usuario.setTelefone(telefoneField.getText());
                    usuario.setSenha(senhaField.getText());
                    control.updatePessoa(usuario);
                }
                clearFields();
            }
        });
        panel.add(atualizarButton);
    }

    private void clearFields() {
        nomeField.setText("");
        idadeField.setText("");
        sexoField.setText("");
        telefoneField.setText("");
        senhaField.setText("");
        cargoField.setText("");
        salarioField.setText("");
        enderecoField.setText("");
        instituicaoField.setText("");
        matriculaField.setText("");
        disciplinaField.setText("");
        grauAcademicoField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(null); // Substitua por uma conexão real
            new GerenciadorUsuario(controller).setVisible(true);
        });
    }
}