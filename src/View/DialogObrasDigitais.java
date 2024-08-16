package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Obras.Livro;

public class DialogObrasDigitais extends JDialog {

    private JTable obrasTable;
    private List<Livro> livros;

    public DialogObrasDigitais(JFrame parent, List<Livro> livros) {
        super(parent, "Obras Digitais", true);
        this.livros = livros;

        setSize(600, 400);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        obrasTable = new JTable();
        updateObrasTable();
        panel.add(new JScrollPane(obrasTable));

        JButton acessarButton = new JButton("Acessar Obra Digital");
        acessarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = obrasTable.getSelectedRow();
                if (selectedRow != -1) {
                    Livro livro = livros.get(selectedRow);
                    JOptionPane.showMessageDialog(DialogObrasDigitais.this, 
                            "Acessando a obra digital: " + livro.getTitulo(),
                            "Acesso à Obra Digital", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(DialogObrasDigitais.this,
                            "Selecione uma obra para acessar.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(acessarButton);

        add(panel);
    }

    private void updateObrasTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Título");
        model.addColumn("Autor");

        for (Livro livro : livros) {
            model.addRow(new Object[]{livro.getTitulo(), livro.getAutores()});
        }

        obrasTable.setModel(model);
    }
}