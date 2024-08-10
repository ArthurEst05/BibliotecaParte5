package Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import Emprest.Emprestimos;
import Emprest.Reserva;
import Faculdade.Curso;
import Faculdade.Faculdade;
import Faculdade.Trabalho;
import Obras.Livro;
import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;


public class Controller {

  public static final String USUARIOS_FILE = "C:\\temp\\usuarios.txt";
    public static final String LIVROS_FILE = "C:\\temp\\livros.txt";
    public static final String EMPRESTIMOS_FILE = "C:\\temp\\emprestimos.txt";
    public static final String RESERVA_FILE = "C:\\temp\\reservas.txt";
    public static final String TRABALHO_FILE = "C:\\temp\\trabalhos.txt";

    public static ArrayList<Emprestimos> emprestimos;
    public static ArrayList<Livro> livros;
    public static ArrayList<Pessoa> usuarios;
    public static ArrayList<Reserva> reservas;
    public static ArrayList<Trabalho> trabalhos;
    public static void saveUsuariosToFile(String filename, ArrayList<Pessoa> list) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (Pessoa p : list) {
                writer.write(p.getNome() + ";" + p.getSenha() + ";" + p.getTipo() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }

    public static ArrayList<Pessoa> loadUsuariosFromFile(String filename) {
        ArrayList<Pessoa> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");

                String nome = data[0];
                String senha = data[1];
                String tipo = data[2];

                Pessoa p = null;
                switch (tipo) {
                    case "Estudante":
                        p = new Aluno(nome, senha);
                        break;
                    case "Funcionário":
                        p = new Funcionario(nome, senha);
                        break;
                    case "Professor":
                        p = new Orientador(nome, senha);
                        break;
                    default:
                        System.out.println("Tipo de usuário desconhecido: " + tipo);
                        continue;
                }

                list.add(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado, criando um novo.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
        return list;
    }

    public static Pessoa findUsuarioByName(String nome) {
        for (Pessoa u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) {
                return u;
            }
        }
        return null;
    }

public static void saveLivrosToFile(String filename, ArrayList<Livro> livros) {
    try (FileWriter writer = new FileWriter(filename, false)) {
        for (Livro livro : livros) {
            writer.write(livro.getTitulo() + ";" + livro.getAutores() + "\n");
        }
        writer.flush();
    } catch (IOException e) {
        System.err.println("Erro ao salvar livros no arquivo: " + e.getMessage());
    }
}

 public static void saveReservasToFile(String filename, ArrayList<Reserva> reservas) {
    try (FileWriter writer = new FileWriter(filename, false)) {
        for (Reserva reserva : reservas) {
            writer.write(reserva.getPessoa().getNome() + ";" + reserva.getLivros().getTitulo() + ";" +
                    reserva.getDataReserva().toString() + ";" + reserva.getHoraReserva().toString() + "\n");
        }
        writer.flush();
    } catch (IOException e) {
        System.err.println("Erro ao salvar reservas no arquivo: " + e.getMessage());
    }
}

public void saveEmprestimosToFile(String filePath, ArrayList<Emprestimos> emprestimos) {
    try (FileWriter writer = new FileWriter(filePath)) {
        for (Emprestimos emp : emprestimos) {
            writer.write(emp.getId() + "," +
                         emp.getDataDoEmprestimo() + "," +
                         emp.getHoraDoEmprestimo() + "," +
                         emp.getLivros().getTitulo() + "," +
                         emp.getPessoa().getNome() + "," +
                         emp.getDataEmprestimo() + "," +
                         emp.getDataDevolucao() + "," +
                         emp.isDevolvido() + "\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

 public static ArrayList<Livro> loadLivrosFromFile(String filename) {
    ArrayList<Livro> livros = new ArrayList<>();
    try (Scanner scanner = new Scanner(new FileReader(filename))) {
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(";");
            String titulo = data[0];
            String autor = data[1];
            livros.add(new Livro(titulo, autor));
        }
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo de livros não encontrado, criando um novo.");
    } catch (Exception e) {
        System.out.println("Erro ao carregar livros: " + e.getMessage());
    }
    return livros;
}
 public static Pessoa findUsuarioByName(String nomeUsuario,ArrayList<Pessoa> usuarios) {
    for (Pessoa usuario : usuarios) { 
        if (usuario.getNome().equalsIgnoreCase(nomeUsuario)) {
            return usuario;
        }
    }
    return null; // ou lançar uma exceção se o usuário não for encontrado
}

 public static Livro findLivroByTitle(String tituloLivro, ArrayList<Livro> livros) {
    for (Livro livro : livros) {
        if (livro.getTitulo().equalsIgnoreCase(tituloLivro)) {
            return livro;
        }
    }
    return null; 
}

 public static ArrayList<Reserva> loadReservasFromFile(String filename) {
    ArrayList<Reserva> reservas = new ArrayList<>();
    try (Scanner scanner = new Scanner(new FileReader(filename))) {
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(";");
            String nomeUsuario = data[0];
            String tituloLivro = data[1];
            LocalDate dataReserva = LocalDate.parse(data[2]);
            LocalTime horaReserva = LocalTime.parse(data[3]);
            Pessoa usuario = findUsuarioByName(nomeUsuario);
            Livro livro = findLivroByTitle(tituloLivro, livros);
            reservas.add(new Reserva(usuario, livro, dataReserva, horaReserva));
        }
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo de reservas não encontrado, criando um novo.");
    } catch (Exception e) {
        System.out.println("Erro ao carregar reservas: " + e.getMessage());
    }
    return reservas;
}

 public static ArrayList<Emprestimos> loadEmprestimosFromFile(String filename) {
    ArrayList<Emprestimos> emprestimos = new ArrayList<>();
    try (Scanner scanner = new Scanner(new FileReader(filename))) {
        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(";");
            String nomeUsuario = data[0];
            String tituloLivro = data[1];
            LocalDate dataEmprestimo = LocalDate.parse(data[2]);
            boolean devolvido = Boolean.parseBoolean(data[3]);
            Pessoa usuario = findUsuarioByName(nomeUsuario);
            Livro livro = findLivroByTitle(tituloLivro, livros);
            Emprestimos emprestimo = new Emprestimos(usuario, livro, dataEmprestimo);
            emprestimo.setDevolvido(devolvido);
            emprestimos.add(emprestimo);
        }
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo de empréstimos não encontrado, criando um novo.");
    } catch (Exception e) {
        System.out.println("Erro ao carregar empréstimos: " + e.getMessage());
    }
    return emprestimos;
}

 public static void saveTrabalhosToFile(String filename, ArrayList<Trabalho> trabalhos) {
    try (FileWriter writer = new FileWriter(filename, false)) {
        for (Trabalho trabalho : trabalhos) {
            writer.write(
                    trabalho.getTitTrabalho() + ";" +
                            trabalho.getFaculdade().getNome() + ";" +
                            trabalho.getDataConclusão() + ";" +
                            trabalho.getAluno().getNome() + ";" +
                            trabalho.getOrientador().getNome() + ";" +
                            trabalho.getCurso().getTituloCurso() + ";" +
                            trabalho.getScore() + ";" +
                            trabalho.getQuantidadedeVotos() + "\n");
        }
        writer.flush();
    } catch (IOException e) {
        System.err.println("Erro ao salvar trabalhos no arquivo: " + e.getMessage());
    }
}

 public static ArrayList<Trabalho> loadTrabalhosFromFile(String filename) {
    ArrayList<Trabalho> list = new ArrayList<>();
    try (Scanner scanner = new Scanner(new FileReader(filename))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(";");

            String titulo = data[0];
            String faculdadeNome = data[1];
            String dataConclusao = data[2];
            String alunoNome = data[3];
            String orientadorNome = data[4];
            String cursoNome = data[5];
            int score = Integer.parseInt(data[6]);
            int quantVotos = Integer.parseInt(data[7]);

            Trabalho trabalho = new Trabalho(
                    titulo,
                    new Faculdade(faculdadeNome),
                    dataConclusao,
                    new Aluno(alunoNome),
                    new Orientador(orientadorNome),
                    new Curso(cursoNome),
                    score,
                    quantVotos);

            list.add(trabalho);
        }
    } catch (FileNotFoundException e) {
        System.out.println("Arquivo não encontrado, criando um novo.");
    } catch (Exception e) {
        System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
    }
    return list;

}
}
