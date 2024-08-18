package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Controller.Controller;
import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;

public class GerenciadorUsuario extends JFrame {
    Controller control;
    // Campos para Aluno
    private JTextField nomeFieldAluno, idadeFieldAluno, sexoFieldAluno, telefoneFieldAluno, senhaFieldAluno;
    private JTextField instituicaoFieldAluno, matriculaFieldAluno;
    
    // Campos para Funcionário
    private JTextField nomeFieldFuncionario, idadeFieldFuncionario, sexoFieldFuncionario, telefoneFieldFuncionario, senhaFieldFuncionario;
    private JTextField cargoFieldFuncionario, salarioFieldFuncionario, enderecoFieldFuncionario;
    
    // Campos para Orientador
    private JTextField nomeFieldOrientador, idadeFieldOrientador, sexoFieldOrientador, telefoneFieldOrientador, senhaFieldOrientador;
    private JTextField disciplinaFieldOrientador, grauAcademicoFieldOrientador, emailFieldOrientador;

    public GerenciadorUsuario(Controller control) {
        this.control = control;
        setTitle("Gerenciar Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Abas para diferentes tipos de usuários
        JTabbedPane tabbedPane = new JTabbedPane();

        // Abas para Aluno, Funcionário e Orientador
        JPanel alunoPanel = createAlunoPanel();
        JPanel funcionarioPanel = createFuncionarioPanel();
        JPanel orientadorPanel = createOrientadorPanel();

        tabbedPane.addTab("Aluno", alunoPanel);
        tabbedPane.addTab("Funcionário", funcionarioPanel);
        tabbedPane.addTab("Orientador", orientadorPanel);

        add(tabbedPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));

        JButton cadastrarButton = new JButton("Cadastrar Usuário");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = "";
                    int idade = 0;
                    String sexo = "", telefone = "", senha = "";
                    Pessoa usuario = null;

                    
                    int selectedTab = tabbedPane.getSelectedIndex();
                    if (selectedTab == 0) { // Aba Aluno
                        nome = nomeFieldAluno.getText().trim();
                        idade = Integer.parseInt(idadeFieldAluno.getText().trim());
                        sexo = sexoFieldAluno.getText().trim();
                        telefone = telefoneFieldAluno.getText().trim();
                        senha = senhaFieldAluno.getText().trim();
                        String instituicao = instituicaoFieldAluno.getText().trim();
                        String matricula = matriculaFieldAluno.getText().trim();
                        usuario = new Aluno(nome, idade, sexo, telefone, instituicao, matricula, senha);
                    } else if (selectedTab == 1) { // Aba Funcionário
                        nome = nomeFieldFuncionario.getText().trim();
                        idade = Integer.parseInt(idadeFieldFuncionario.getText().trim());
                        sexo = sexoFieldFuncionario.getText().trim();
                        telefone = telefoneFieldFuncionario.getText().trim();
                        senha = senhaFieldFuncionario.getText().trim();
                        String cargo = cargoFieldFuncionario.getText().trim();
                        double salario = Double.parseDouble(salarioFieldFuncionario.getText().trim());
                        String endereco = enderecoFieldFuncionario.getText().trim();
                        usuario = new Funcionario(nome, idade, sexo, telefone, cargo, salario, endereco, senha);
                    } else if (selectedTab == 2) { // Aba Orientador
                        nome = nomeFieldOrientador.getText().trim();
                        idade = Integer.parseInt(idadeFieldOrientador.getText().trim());
                        sexo = sexoFieldOrientador.getText().trim();
                        telefone = telefoneFieldOrientador.getText().trim();
                        senha = senhaFieldOrientador.getText().trim();
                        String disciplina = disciplinaFieldOrientador.getText().trim();
                        String grauAcademico = grauAcademicoFieldOrientador.getText().trim();
                        String email = emailFieldOrientador.getText().trim();
                        usuario = new Orientador(nome, idade, sexo, telefone, disciplina, grauAcademico, senha, email);
                    }

                    if (usuario != null) {
                        control.addPessoa(usuario);
                        clearFields(selectedTab);
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao identificar o tipo de usuário.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira valores numéricos válidos para idade e salário.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage());
                }
            }
        });

        
        buttonPanel.add(cadastrarButton);
  

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createAlunoPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        nomeFieldAluno = new JTextField(20);
        idadeFieldAluno = new JTextField(20);
        sexoFieldAluno = new JTextField(20);
        telefoneFieldAluno = new JTextField(20);
        senhaFieldAluno = new JTextField(20);
        instituicaoFieldAluno = new JTextField(20);
        matriculaFieldAluno = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeFieldAluno);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeFieldAluno);
        panel.add(new JLabel("Sexo:"));
        panel.add(sexoFieldAluno);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneFieldAluno);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaFieldAluno);
        panel.add(new JLabel("Instituição Educacional:"));
        panel.add(instituicaoFieldAluno);
        panel.add(new JLabel("Matrícula:"));
        panel.add(matriculaFieldAluno);

        return panel;
    }

    private JPanel createFuncionarioPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        nomeFieldFuncionario = new JTextField(20);
        idadeFieldFuncionario = new JTextField(20);
        sexoFieldFuncionario = new JTextField(20);
        telefoneFieldFuncionario = new JTextField(20);
        senhaFieldFuncionario = new JTextField(20);
        cargoFieldFuncionario = new JTextField(20);
        salarioFieldFuncionario = new JTextField(20);
        enderecoFieldFuncionario = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeFieldFuncionario);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeFieldFuncionario);
        panel.add(new JLabel("Sexo:"));
        panel.add(sexoFieldFuncionario);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneFieldFuncionario);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaFieldFuncionario);
        panel.add(new JLabel("Cargo:"));
        panel.add(cargoFieldFuncionario);
        panel.add(new JLabel("Salário:"));
        panel.add(salarioFieldFuncionario);
        panel.add(new JLabel("Endereço:"));
        panel.add(enderecoFieldFuncionario);

        return panel;
    }

    private JPanel createOrientadorPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        nomeFieldOrientador = new JTextField(20);
        idadeFieldOrientador = new JTextField(20);
        sexoFieldOrientador = new JTextField(20);
        telefoneFieldOrientador = new JTextField(20);
        senhaFieldOrientador = new JTextField(20);
        disciplinaFieldOrientador = new JTextField(20);
        grauAcademicoFieldOrientador = new JTextField(20);
        emailFieldOrientador = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeFieldOrientador);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeFieldOrientador);
        panel.add(new JLabel("Sexo:"));
        panel.add(sexoFieldOrientador);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneFieldOrientador);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaFieldOrientador);
        panel.add(new JLabel("Disciplina:"));
        panel.add(disciplinaFieldOrientador);
        panel.add(new JLabel("Grau Acadêmico:"));
        panel.add(grauAcademicoFieldOrientador);
        panel.add(new JLabel("Email:"));
        panel.add(emailFieldOrientador);

        return panel;
    }

    private void clearFields(int selectedTab) {
        if (selectedTab == 0) {
            nomeFieldAluno.setText("");
            idadeFieldAluno.setText("");
            sexoFieldAluno.setText("");
            telefoneFieldAluno.setText("");
            senhaFieldAluno.setText("");
            instituicaoFieldAluno.setText("");
            matriculaFieldAluno.setText("");
        } else if (selectedTab == 1) {
            nomeFieldFuncionario.setText("");
            idadeFieldFuncionario.setText("");
            sexoFieldFuncionario.setText("");
            telefoneFieldFuncionario.setText("");
            senhaFieldFuncionario.setText("");
            cargoFieldFuncionario.setText("");
            salarioFieldFuncionario.setText("");
            enderecoFieldFuncionario.setText("");
        } else if (selectedTab == 2) {
            nomeFieldOrientador.setText("");
            idadeFieldOrientador.setText("");
            sexoFieldOrientador.setText("");
            telefoneFieldOrientador.setText("");
            senhaFieldOrientador.setText("");
            disciplinaFieldOrientador.setText("");
            grauAcademicoFieldOrientador.setText("");
            emailFieldOrientador.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(null); 
            new GerenciadorUsuario(controller).setVisible(true);
        });
    }
}
