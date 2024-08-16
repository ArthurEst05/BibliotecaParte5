package Obras;

import java.io.Serializable;

public class Livro extends Obra implements Serializable{
    private static final long serialVersionUID = 1L;

    private String editora;
    private String edicao;
    private int numFolhas;
    private boolean disponivel;

    public Livro(String titulo, String autores) {
        super(titulo, autores);
    }
    
    public Livro(){}

    public Livro(String titulo, String autores, String area, String ano, boolean digital) {
        super(titulo, autores, area, ano, digital);
    }

    public Livro(String titulo, String autores, String area,String editora, String ano, String edicao, int numFolhas, boolean disponivel, boolean digital) {
        super(titulo, autores, area, ano, digital);
        this.editora = editora;
        this.edicao = edicao;
        this.numFolhas = numFolhas;
        this.disponivel = true;
    }

    public String getEditora() {
        return editora;
    }
    public void setEditora(String editora) {
        this.editora = editora;
    }
    public String getEdicao() {
        return edicao;
    }
    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }
    public int getNumFolhas() {
        return numFolhas;
    }
    public void setNumFolhas(int numFolhas) {
        this.numFolhas = numFolhas;
    }

    public void abrirLivro(){
        System.out.println("Livro está aberto para leitura");
    }

    public void fecharLivro(){
        System.out.println("Livro está fechado para leitura");
    }

    public void visualizarObra(Livro livro){
        System.out.println("Lendo livro");
    }
    
    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro [editora=" + editora + ", edicao=" + edicao + ", numFolhas=" + numFolhas + ", disponivel="
                + disponivel + ", getEditora()=" + getEditora() + ", getEdicao()=" + getEdicao() + ", getTitulo()="
                + getTitulo() + ", getNumFolhas()=" + getNumFolhas() + ", getAutores()=" + getAutores() + ", getArea()="
                + getArea() + ", getAno()=" + getAno() + ", isDisponivel()=" + isDisponivel() + ", getId()=" + getId()
                + ", isDigital()=" + isDigital() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }    
}
