package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import Obras.Livro;

public class DialogObrasDigitais extends JDialog {
    public DialogObrasDigitais(Frame owner, ArrayList<Livro> livros) {
        super(owner, "Obras Digitais", true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(owner);

        // Cria uma área de texto para exibir as obras digitais
        JTextArea taObrasDigitais = new JTextArea();
        taObrasDigitais.setEditable(false);
        
        // Adiciona as obras digitais à área de texto
        StringBuilder sb = new StringBuilder("Obras Digitais:\n");
        for (Livro livro : livros) {
            if (livro.isDigital()) { // Supondo que a classe Livro tem o método isDigital()
                sb.append(livro.toString()).append("\n");
            }
        }
        
        taObrasDigitais.setText(sb.toString());
        add(new JScrollPane(taObrasDigitais), BorderLayout.CENTER);
        
        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        add(btnFechar, BorderLayout.SOUTH);
    }
}
