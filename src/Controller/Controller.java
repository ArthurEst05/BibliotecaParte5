package Controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Faculdade.Curso;
import Faculdade.Faculdade;
import Faculdade.Trabalho;
import Obras.Livro;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;
import Emprest.Emprestimos;
import Emprest.Reserva;
import modelDao.FaculdadeDao;
import modelDao.LivroDao;
import modelDao.FuncionarioDao;
import modelDao.OrientadorDao;
import modelDao.TrabalhoDao;
import modelDao.ReservaDao;
import modelDao.EmprestimoDao;
import modelDao.CursoDao;
import modelDao.PessoaDao;
import modelDaoImpl.FaculdadeDaoJDBC;
import modelDaoImpl.LivroDaoJDBC;
import modelDaoImpl.FuncionarioDaoJDBC;
import modelDaoImpl.OrientadorDaoJDBC;
import modelDaoImpl.TrabalhoDaoJDBC;
import modelDaoImpl.ReservaDaoJDBC;
import modelDaoImpl.EmprestimoDaoJDBC;
import modelDaoImpl.CursoDaoJDBC;
import modelDaoImpl.PessoaDaoJDBC;

public class Controller {

    private FaculdadeDao faculdadeDao;
    private LivroDao livroDao;
    private FuncionarioDao funcionarioDao;
    private OrientadorDao orientadorDao;
    private TrabalhoDao trabalhoDao;
    private ReservaDao reservaDao;
    private EmprestimoDao emprestimoDao;
    private CursoDao cursoDao;
    private PessoaDao pessoaDao;

    public Controller(Connection conn) {
        this.faculdadeDao = new FaculdadeDaoJDBC(conn);
        this.livroDao = new LivroDaoJDBC(conn);
        this.funcionarioDao = new FuncionarioDaoJDBC(conn);
        this.orientadorDao = new OrientadorDaoJDBC(conn);
        this.trabalhoDao = new TrabalhoDaoJDBC(conn);
        this.reservaDao = new ReservaDaoJDBC(conn);
        this.emprestimoDao = new EmprestimoDaoJDBC(conn);
        this.cursoDao = new CursoDaoJDBC(conn);
        this.pessoaDao = new PessoaDaoJDBC(conn);
    }

    public void addFaculdade(Faculdade faculdade) { faculdadeDao.insert(faculdade); }
    public void updateFaculdade(Faculdade faculdade) { faculdadeDao.update(faculdade); }
    public void deleteFaculdade(int id) { faculdadeDao.deleteById(id); }
    public Faculdade getFaculdadeById(int id) { return faculdadeDao.findById(id); }
    public List<Faculdade> getAllFaculdades() { return faculdadeDao.findAll(); }

    public void addLivro(Livro livro) { livroDao.insert(livro); }
    public void updateLivro(Livro livro) { livroDao.update(livro); }
    public void deleteLivro(int id) { livroDao.deleteById(id); }
    public Livro getLivroById(int id) { return livroDao.findById(id); }
    public List<Livro> getAllLivros() { return livroDao.findAll(); }
    public Livro getLivroByTitulo(String titulo) {
        try {
            return livroDao.findByTitulo(titulo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addFuncionario(Funcionario funcionario) { funcionarioDao.insert(funcionario); }
    public void updateFuncionario(Funcionario funcionario) { funcionarioDao.update(funcionario); }
    public void deleteFuncionario(int id) { funcionarioDao.deleteById(id); }
    public Funcionario getFuncionarioById(int id) { return funcionarioDao.findById(id); }
    public List<Funcionario> getAllFuncionarios() { return funcionarioDao.findAll(); }

    public void addOrientador(Orientador orientador) { orientadorDao.insert(orientador); }
    public void updateOrientador(Orientador orientador) { orientadorDao.update(orientador); }
    public void deleteOrientador(int id) { orientadorDao.deleteById(id); }
    public Orientador getOrientadorById(int id) { return orientadorDao.findById(id); }
    public List<Orientador> getAllOrientadores() { return orientadorDao.findAll(); }

    public void addTrabalho(Trabalho trabalho) { trabalhoDao.insert(trabalho); }
    public void updateTrabalho(Trabalho trabalho) { trabalhoDao.update(trabalho); }
    public void deleteTrabalho(int id) { trabalhoDao.deleteById(id); }
    public Trabalho getTrabalhoById(int id) { return trabalhoDao.findById(id); }
    public List<Trabalho> getAllTrabalhos() { return trabalhoDao.findAll(); }

    public void addReserva(Reserva reserva) { reservaDao.insert(reserva); }
    public void updateReserva(Reserva reserva) { reservaDao.update(reserva); }
    public void deleteReserva(int id) { reservaDao.deleteById(id); }
    public Reserva getReservaById(int id) { return reservaDao.findById(id); }
    public List<Reserva> getAllReservas() { return reservaDao.findAll(); }

    public void addEmprestimo(Emprestimos emprestimo) { emprestimoDao.insert(emprestimo); }
    public void updateEmprestimo(Emprestimos emprestimo) { emprestimoDao.update(emprestimo); }
    public void deleteEmprestimo(int id) { emprestimoDao.deleteById(id); }
    public Emprestimos getEmprestimoById(int id) { return emprestimoDao.findById(id); }
    public List<Emprestimos> getAllEmprestimos() { return emprestimoDao.findAll(); }

    public void addCurso(Curso curso) { cursoDao.insert(curso); }
    public void updateCurso(Curso curso) { cursoDao.update(curso); }
    public void deleteCurso(int id) { cursoDao.deleteById(id); }
    public Curso getCursoById(int id) { return cursoDao.findById(id); }
    public List<Curso> getAllCursos() { return cursoDao.findAll(); }

    public void addPessoa(Pessoa pessoa){ pessoaDao.insert(pessoa); }
    public void updatePessoa(Pessoa pessoa){ pessoaDao.update(pessoa); }
    public void deletePessoa(Integer id){ pessoaDao.deleteById(id); }
    public Pessoa getPessoaById(Integer id){ return pessoaDao.findById(id); }
    public List<Pessoa> getAllPessoas() { return pessoaDao.findAll(); }
    public Pessoa getPessoaByUsernameAndPassword(String username, String password) {
        try {
            return ((PessoaDaoJDBC) pessoaDao).findByUsernameAndPassword(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}