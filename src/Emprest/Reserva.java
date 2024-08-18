package Emprest;

import java.time.LocalDate;

import Obras.Livro;
import Usuarios.Pessoa;

public class Reserva {
    private int id;
    private Emprestimos emprestimoFuturo; // Relacionamento com o futuro empréstimo
    private Livro livro;
    private Pessoa usuario;
    private LocalDate dataReserva;
    private String status;

    public Reserva(Pessoa usuario, Livro livro, LocalDate dataReserva) {
        if (livro.isDisponivel()) {
            throw new IllegalArgumentException("Livro está disponível, faça um empréstimo em vez de uma reserva.");
        }
        this.usuario = usuario;
        this.livro = livro;
        this.dataReserva = dataReserva;
        this.status = "Ativa";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Emprestimos getEmprestimoFuturo() {
        return emprestimoFuturo;
    }

    public void setEmprestimoFuturo(Emprestimos emprestimoFuturo) {
        this.emprestimoFuturo = emprestimoFuturo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Pessoa getUsuario() {
        return usuario;
    }

    public void setUsuario(Pessoa usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void cancelar() {
        this.status = "Cancelada";
    }

    public boolean isAtiva() {
        return "Ativa".equals(this.status);
    }
}
