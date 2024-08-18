package modelDaoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Emprest.Emprestimos;
import modelDao.EmprestimoDao;
import Obras.Livro;
import Usuarios.Pessoa;
import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;

public class EmprestimoDaoJDBC implements EmprestimoDao {
    private Connection conn;

    public EmprestimoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Emprestimos emprestimo) {
        String sql = "INSERT INTO Emprestimo (pessoa_id, livro_id, data_emprestimo, data_devolucao, devolvido) VALUES (?, ?, ?, ?, ?)";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emprestimo.getPessoa().getId());
            stmt.setInt(2, emprestimo.getLivros().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.setBoolean(5, emprestimo.isDevolvido());
    
            int affectedRows = stmt.executeUpdate();
    
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        emprestimo.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Erro ao inserir o empréstimo, nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o empréstimo", e);
        }
    }

    @Override
    public void update(Emprestimos obj) {
        String sql = "UPDATE emprestimo SET data_emprestimo = ?, livro_id = ?, pessoa_id = ?, data_devolucao = ?, devolvido = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(obj.getDataEmprestimo()));
            st.setInt(2, obj.getLivros().getId());
            st.setInt(3, obj.getPessoa().getId());
            st.setDate(4, Date.valueOf(obj.getDataDevolucao()));
            st.setBoolean(5, obj.isDevolvido());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o empréstimo", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar o empréstimo", e);
        }
    }

    @Override
    public Emprestimos findById(Integer id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateEmprestimos(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empréstimo por ID", e);
        }
        return null;
    }

    @Override
    public List<Emprestimos> findAll() {
        String sql = "SELECT * FROM emprestimo";
        List<Emprestimos> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateEmprestimos(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os empréstimos", e);
        }
        return list;
    }

    private Emprestimos instantiateEmprestimos(ResultSet rs) throws SQLException {
        Livro livro = findLivroById(rs.getInt("livro_id"));
        Pessoa pessoa = findPessoaById(rs.getInt("pessoa_id"));
        
        if (pessoa == null) {
            throw new SQLException("Pessoa não encontrada com o ID: " + rs.getInt("pessoa_id"));
        }
        
        Emprestimos emprestimos = new Emprestimos(
            pessoa,
            livro,
            rs.getDate("data_emprestimo").toLocalDate()
        );
        emprestimos.setId(rs.getInt("id"));
        emprestimos.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
        emprestimos.setDevolvido(rs.getBoolean("devolvido"));
        return emprestimos;
    }

    private Livro findLivroById(int id) throws SQLException {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setAutores(rs.getString("autores"));
                    livro.setDisponivel(rs.getBoolean("disponivel"));
                    return livro;
                }
            }
        }
        return null;
    }

    private Pessoa findPessoaById(int id) throws SQLException {
    String sql = "SELECT * FROM pessoa WHERE id = ?";
    try (PreparedStatement st = conn.prepareStatement(sql)) {
        st.setInt(1, id);
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                Pessoa pessoa = null;
                switch (tipo) {
                    case "Aluno":
                        pessoa = new Aluno(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("sexo"),
                            rs.getString("cpf"),
                            rs.getString("telefone"),
                            rs.getString("senha")
                        );
                        break;
                    case "Funcionario":
                        pessoa = new Funcionario(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("sexo"),
                            rs.getString("telefone"),
                            rs.getString("cargo"),
                            rs.getDouble("salario"),
                            rs.getString("endereco"),
                            rs.getString("senha")
                        );
                        break;
                    case "Orientador":
                        pessoa = new Orientador(
                            rs.getString("nome"),
                            rs.getInt("idade"),
                            rs.getString("sexo"),
                            rs.getString("telefone"),
                            rs.getString("disciplina"),
                            rs.getString("grau_academico"),
                            rs.getString("senha"),
                            rs.getString("email")
                        );
                        break;
                    default:
                        throw new SQLException("Tipo de pessoa desconhecido: " + tipo);
                }
                pessoa.setId(id);
                return pessoa;
            }
        }
    }
        return null;
    }
}
