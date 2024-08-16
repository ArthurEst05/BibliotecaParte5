package Faculdade;

public class Faculdade {
private int id;
private String nome;
private String estado;
private String cidade;


public Faculdade(String nome) {
    this.nome = nome;
}


public String getNome() {
    return nome;
}
public void setNome(String nome) {
    this.nome = nome;
}
public String getEstado() {
    return estado;
}
public void setEstado(String estado) {
    this.estado = estado;
}
public String getCidade() {
    return cidade;
}
public void setCidade(String cidade) {
    this.cidade = cidade;
}
public int getId() {
    return id;
}
public void setId(int id) {
    this.id = id;
}


public Faculdade findById(long id2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
}


}
