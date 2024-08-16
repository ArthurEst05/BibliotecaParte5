package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Obras.Livro;
import modelDao.LivroDao;

public class LivroDaoJDBC implements LivroDao {
    private Connection conn;

    public LivroDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Livro obj) {
        String sql = "INSERT INTO livro (titulo, autores, area, ano, editora, edicao, num_folhas, disponivel, digital) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getTitulo());
            st.setString(2, obj.getAutores());
            st.setString(3, obj.getArea());
            st.setString(4, obj.getAno());
            st.setString(5, obj.getEditora());
            st.setString(6, obj.getEdicao());
            st.setInt(7, obj.getNumFolhas());
            st.setBoolean(8, obj.isDisponivel());
            st.setBoolean(9, obj.isDigital());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Livro obj) {
        String sql = "UPDATE livro SET titulo = ?, autores = ?, area = ?, ano = ?, editora = ?, edicao = ?, num_folhas = ?, disponivel = ?, digital = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getTitulo());
            st.setString(2, obj.getAutores());
            st.setString(3, obj.getArea());
            st.setString(4, obj.getAno());
            st.setString(5, obj.getEditora());
            st.setString(6, obj.getEdicao());
            st.setInt(7, obj.getNumFolhas());
            st.setBoolean(8, obj.isDisponivel());
            st.setBoolean(9, obj.isDigital());
            st.setInt(10, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Livro findById(Integer id) {
        String sql = "SELECT * FROM livro WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateLivro(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Livro> findAll() {
        String sql = "SELECT * FROM livro";
        List<Livro> livros = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                livros.add(instantiateLivro(rs));
            }
            return livros;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Livro instantiateLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro(
            rs.getString("titulo"),
            rs.getString("autores"),
            rs.getString("area"),
            rs.getString("ano"),
            rs.getBoolean("digital")
        );
        livro.setId(rs.getInt("id"));
        livro.setEditora(rs.getString("editora"));
        livro.setEdicao(rs.getString("edicao"));
        livro.setNumFolhas(rs.getInt("num_folhas"));
        livro.setDisponivel(rs.getBoolean("disponivel"));
        return livro;
    }
}
