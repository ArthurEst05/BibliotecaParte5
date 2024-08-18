package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Faculdade.Curso;
import modelDao.CursoDao;

public class CursoDaoJDBC implements CursoDao {
    private Connection conn;

    public CursoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Curso obj) {
        String sql = "INSERT INTO curso (titulo_curso) VALUES (?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getTituloCurso());
            
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
    public void update(Curso obj) {
        String sql = "UPDATE curso SET titulo_curso = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getTituloCurso());
            st.setInt(2, obj.getId());
            
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM curso WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Curso findById(Integer id) {
        String sql = "SELECT * FROM curso WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateCurso(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findAll() {
        String sql = "SELECT * FROM curso";
        List<Curso> cursos = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                cursos.add(instantiateCurso(rs));
            }
            return cursos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Curso instantiateCurso(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String tituloCurso = rs.getString("titulo_curso");
        
        Curso curso = new Curso(tituloCurso);
        curso.setId(id);
        
        return curso;
    }
}
