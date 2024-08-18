package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Faculdade.Trabalho;
import modelDao.TrabalhoDao;

public class TrabalhoDaoJDBC implements TrabalhoDao {
    private Connection conn;

    public TrabalhoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Trabalho obj) {
        String sql = "INSERT INTO trabalho (titTrabalho, faculdade, dataConclusao, aluno, orientador, curso, localArquivo, score, quantidadedeVotos) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getTitTrabalho());
            st.setString(2, obj.getFaculdade());
            st.setDate(3, java.sql.Date.valueOf(obj.getDataConclusao()));
            st.setString(4, obj.getAluno());
            st.setString(5, obj.getOrientador());
            st.setString(6, obj.getCurso());
            st.setString(7, obj.getLocalArquivo());
            st.setInt(8, obj.getScore());
            st.setInt(9, obj.getQuantidadedeVotos());
    
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getLong(1)); 
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Trabalho obj) {
        String sql = "UPDATE trabalho SET titTrabalho = ?, faculdade = ?, dataConclusao = ?, aluno = ?, orientador = ?, curso = ?, localArquivo = ?, score = ?, quantidadedeVotos = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getTitTrabalho());
            st.setString(2, obj.getFaculdade());
            st.setDate(3, java.sql.Date.valueOf(obj.getDataConclusao()));
            st.setString(4, obj.getAluno());
            st.setString(5, obj.getOrientador());
            st.setString(6, obj.getCurso());
            st.setString(7, obj.getLocalArquivo());
            st.setInt(8, obj.getScore());
            st.setInt(9, obj.getQuantidadedeVotos());
            st.setLong(10, obj.getId());
    
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM trabalho WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trabalho findById(Integer id) {
        String sql = "SELECT * FROM trabalho WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateTrabalho(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Trabalho> findAll() {
        String sql = "SELECT * FROM trabalho";
        List<Trabalho> trabalhos = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                trabalhos.add(instantiateTrabalho(rs));
            }
            return trabalhos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Trabalho instantiateTrabalho(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String titTrabalho = rs.getString("titTrabalho");
        String faculdade = rs.getString("faculdade");
        LocalDate dataConclusao = rs.getDate("dataConclusao").toLocalDate(); 
        String aluno = rs.getString("aluno");
        String orientador = rs.getString("orientador");
        String curso = rs.getString("curso");
        String localArquivo = rs.getString("localArquivo");
        int score = rs.getInt("score");
        int quantidadedeVotos = rs.getInt("quantidadedeVotos");
    
        Trabalho trabalho = new Trabalho(titTrabalho, faculdade, dataConclusao, aluno, orientador, curso, localArquivo, score, quantidadedeVotos);
        trabalho.setId(id);  
        return trabalho;
    }
}
