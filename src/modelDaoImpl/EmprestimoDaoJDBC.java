package modelDaoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Emprest.Emprestimos;
import modelDao.EmprestimoDao;
import Obras.Livro;
import Usuarios.Pessoa;
import Usuarios.Funcionario;  // Exemplo de possível subclasse
import Usuarios.Orientador;   // Exemplo de possível subclasse

public class EmprestimoDaoJDBC implements EmprestimoDao {
    private Connection conn;

    public EmprestimoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Emprestimos obj) {
        String sql = "INSERT INTO emprestimo (data_emprestimo, hora_emprestimo, livro_id, pessoa_id, data_devolucao, devolvido) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setDate(1, Date.valueOf(obj.getDataEmprestimo()));
            st.setString(2, obj.getHoraDoEmprestimo());
            st.setInt(3, obj.getLivros().getId());  // Assumindo que o Livro tem um método getId()
            st.setInt(4, obj.getPessoa().getId());  // Assumindo que Pessoa tem um método getId()
            st.setDate(5, Date.valueOf(obj.getDataDevolucao()));
            st.setBoolean(6, obj.isDevolvido());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Emprestimo", e);
        }
    }

    @Override
    public void update(Emprestimos obj) {
        String sql = "UPDATE emprestimo SET data_emprestimo = ?, hora_emprestimo = ?, livro_id = ?, pessoa_id = ?, data_devolucao = ?, devolvido = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDate(1, Date.valueOf(obj.getDataEmprestimo()));
            st.setString(2, obj.getHoraDoEmprestimo());
            st.setInt(3, obj.getLivros().getId());
            st.setInt(4, obj.getPessoa().getId());
            st.setDate(5, Date.valueOf(obj.getDataDevolucao()));
            st.setBoolean(6, obj.isDevolvido());
            st.setInt(7, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Emprestimo", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Emprestimo", e);
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
            throw new RuntimeException("Error finding Emprestimo by id", e);
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
            throw new RuntimeException("Error finding all Emprestimos", e);
        }
        return list;
    }

    private Emprestimos instantiateEmprestimos(ResultSet rs) throws SQLException {
        Livro livro = findLivroById(rs.getInt("livro_id"));
        Pessoa pessoa = findPessoaById(rs.getInt("pessoa_id"));
    
        Emprestimos emprestimos = new Emprestimos(
            pessoa,
            livro,
            rs.getDate("data_emprestimo").toLocalDate()
        );
        emprestimos.setId(rs.getInt("id"));
        emprestimos.setHoraDoEmprestimo(rs.getString("hora_emprestimo"));
        emprestimos.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());  // Ajuste se necessário
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
                    livro.setTitulo(rs.getString("titulo"));  // Assumindo que Livro tem esses métodos
                    livro.setAutores(rs.getString("autor"));    // Ajuste conforme necessário
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
                    if ("Funcionario".equals(tipo)) {
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
                    } else if ("Orientador".equals(tipo)) {
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
                    }
                    pessoa.setId(id);
                    return pessoa;
                }
            }
        }
        return null;
    }
}
