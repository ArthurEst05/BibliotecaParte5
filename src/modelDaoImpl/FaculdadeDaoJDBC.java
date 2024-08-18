package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Faculdade.Faculdade;
import modelDao.FaculdadeDao;

public class FaculdadeDaoJDBC implements FaculdadeDao {
    private Connection conn;

    public FaculdadeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Faculdade obj) {
        String sql = "INSERT INTO faculdade (nome, estado, cidade) VALUES (?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEstado());
            st.setString(3, obj.getCidade());
            
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
    public void update(Faculdade obj) {
        String sql = "UPDATE faculdade SET nome = ?, estado = ?, cidade = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getEstado());
            st.setString(3, obj.getCidade());
            st.setInt(4, obj.getId());
            
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM faculdade WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Faculdade findById(Integer id) {
        String sql = "SELECT * FROM faculdade WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateFaculdade(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Faculdade> findAll() {
        String sql = "SELECT * FROM faculdade";
        List<Faculdade> faculdades = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                faculdades.add(instantiateFaculdade(rs));
            }
            return faculdades;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Faculdade instantiateFaculdade(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String estado = rs.getString("estado");
        String cidade = rs.getString("cidade");
        
        Faculdade faculdade = new Faculdade(nome);
        faculdade.setId(id);
        faculdade.setEstado(estado);
        faculdade.setCidade(cidade);
        
        return faculdade;
    }
}
