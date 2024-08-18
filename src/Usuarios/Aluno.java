package Usuarios;


public class Aluno extends Pessoa {
    private static final long serialVersionUID = 1L;

    private String instituicaoEducacional;
    private String matricula;

    public Aluno(){}
    public Aluno(String nome, int idade, String sexo, String telefone, String senha) {
        super(nome, idade, sexo, telefone, senha);
        setTipo("Aluno");
    }

    
    public Aluno(String nome, int idade, String sexo, String cpf, String telefone, String senha) {
        super(nome, idade, sexo, cpf, telefone, senha);
        setTipo("Aluno");
    }


    public Aluno(String nome, String senha) {
        super(nome, senha);
        setTipo("Aluno");
    }

    public Aluno(String nome) {
        super(nome);
        setTipo("Aluno");
    }
    

    public Aluno(String nome, int idade, String sexo, String telefone, String instituicaoEducacional,
                     String matricula, String senha) {
        super(nome, idade, sexo, telefone, senha);
        this.instituicaoEducacional = instituicaoEducacional;
        this.matricula = matricula;
        setTipo("Aluno");
    }
    
    public String getInstituicaoEducacional() {
        return instituicaoEducacional;
    }
    public void setInstituicaoEducacional(String instituicaoEducacional) {
        this.instituicaoEducacional = instituicaoEducacional;
    }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
      public String getTipo() {
        return "Aluno";
    }

    @Override
    public String toString() {
        return "Aluno [instituicaoEducacional=" + instituicaoEducacional + ", matricula=" + matricula + ", getId()="
                + getId() + ", getNome()=" + getNome() + ", getInstituicaoEducacional()=" + getInstituicaoEducacional()
                + ", getIdade()=" + getIdade() + ", getSexo()=" + getSexo() + ", getMatricula()=" + getMatricula()
                + ", getTelefone()=" + getTelefone() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

}
