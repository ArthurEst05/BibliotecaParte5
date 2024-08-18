package Emprest;
import java.time.LocalDate;

import Obras.Livro;
import Usuarios.Pessoa;

public class Emprestimos {
    private int id;
    private Livro livros;
    private Pessoa usuarios;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido;
    


    public Emprestimos(Pessoa usuarios, Livro livro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        if (!livro.isDisponivel()) { 
            throw new IllegalArgumentException("Livro não está disponível para empréstimo.");
        }
        this.usuarios = usuarios;
        this.livros = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false; 
    }
    

    public Emprestimos(Pessoa usuario, Livro livro, LocalDate dataEmprestimo ) {
        this.usuarios = usuario;
        this.livros = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataEmprestimo.plusDays(14);
        this.devolvido = false;
    }

    public Livro getLivros() {
        return livros;
    }
    public void setLivros(Livro livros) {
        this.livros = livros;
    }
    public Pessoa getPessoa() {
        return usuarios;
    }
    public void setPessoa(Pessoa usuarios) {
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void setDevolvido(boolean devolvido) {
        this.devolvido = devolvido;
    }

    public void devolverLivro() {
        this.livros.setDisponivel(true);
        this.devolvido = true;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
