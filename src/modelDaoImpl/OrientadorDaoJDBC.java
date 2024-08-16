package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Usuarios.Orientador;
import modelDao.OrientadorDao;

public class OrientadorDaoJDBC implements OrientadorDao {
    private Connection conn;

    public OrientadorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Orientador obj) {
        String sql = "INSERT INTO pessoa (nome, idade, sexo, telefone, disciplina, grauAcademico, senha, email, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdade());
            st.setString(3, obj.getSexo());
            st.setString(4, obj.getTelefone());
            st.setString(5, obj.getDisciplina());
            st.setString(6, obj.getGrauAcademico());
            st.setString(7, obj.getSenha());
            st.setString(8, obj.getEmail());
            st.setString(9, obj.getTipo());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Orientador", e);
        }
    }

    @Override
    public void update(Orientador obj) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ?, sexo = ?, telefone = ?, disciplina = ?, grauAcademico = ?, senha = ?, email = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setInt(2, obj.getIdade());
            st.setString(3, obj.getSexo());
            st.setString(4, obj.getTelefone());
            st.setString(5, obj.getDisciplina());
            st.setString(6, obj.getGrauAcademico());
            st.setString(7, obj.getSenha());
            st.setString(8, obj.getEmail());
            st.setInt(9, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Orientador", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Orientador", e);
        }
    }

    @Override
    public Orientador findById(Integer id) {
        String sql = "SELECT * FROM pessoa WHERE id = ? AND tipo = 'Orientador'";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateOrientador(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Orientador by id", e);
        }
        return null;
    }

    @Override
    public List<Orientador> findAll() {
        String sql = "SELECT * FROM pessoa WHERE tipo = 'Orientador'";
        List<Orientador> list = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateOrientador(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Orientadores", e);
        }
        return list;
    }

    private Orientador instantiateOrientador(ResultSet rs) throws SQLException {
        Orientador orientador = new Orientador(
            rs.getString("nome"),
            rs.getInt("idade"),
            rs.getString("sexo"),
            rs.getString("telefone"),
            rs.getString("disciplina"),
            rs.getString("grauAcademico"),
            rs.getString("senha"),
            rs.getString("email")
        );
        orientador.setId(rs.getInt("id"));
        return orientador;
    }
}
