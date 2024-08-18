package Faculdade;

import java.time.LocalDate;

public class Trabalho {
    private long id;
    private String titTrabalho;
    private String faculdade;
    private LocalDate dataConclusao;
    private String aluno;
    private String orientador;
    private String curso;
    private String localArquivo;
    private int score;
    private int quantidadedeVotos;
  
    public Trabalho(String titTrabalho, String faculdade, LocalDate dataConclusao, String aluno, String orientador,
            String curso, String localArquivo, int score, int quantidadedeVotos) {
        this.titTrabalho = titTrabalho;
        this.faculdade = faculdade;
        this.dataConclusao = dataConclusao;
        this.aluno = aluno;
        this.orientador = orientador;
        this.curso = curso;
        this.localArquivo = localArquivo;
        this.score = score;
        this.quantidadedeVotos = quantidadedeVotos;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitTrabalho() {
        return titTrabalho;
    }
    public void setTitTrabalho(String titTrabalho) {
        this.titTrabalho = titTrabalho;
    }
    public String getFaculdade() {
        return faculdade;
    }
    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }
    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
    public String getAluno() {
        return aluno;
    }
    public void setAluno(String aluno) {
        this.aluno = aluno;
    }
    public String getOrientador() {
        return orientador;
    }
    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public String getLocalArquivo() {
        return localArquivo;
    }
    public void setLocalArquivo(String localArquivo) {
        this.localArquivo = localArquivo;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getQuantidadedeVotos() {
        return quantidadedeVotos;
    }
    public void setQuantidadedeVotos(int quantidadedeVotos) {
        this.quantidadedeVotos = quantidadedeVotos;
    }
    
}
