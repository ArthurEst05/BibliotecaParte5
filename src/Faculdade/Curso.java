package Faculdade;

public class Curso {
    private AreaConhecimento areaConhecimento;
    private String tituloCurso;
    private int id;


    public Curso(String tituloCurso) {
        this.tituloCurso = tituloCurso;
    }
    
    public AreaConhecimento getAreaConhecimento() {
        return areaConhecimento;
    }
    public void setAreaConhecimento(AreaConhecimento areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }
    public String getTituloCurso() {
        return tituloCurso;
    }
    public void setTituloCurso(String tituloCurso) {
        this.tituloCurso = tituloCurso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
