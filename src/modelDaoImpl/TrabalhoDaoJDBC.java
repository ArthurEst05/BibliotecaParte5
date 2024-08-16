package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Faculdade.Curso;
import Faculdade.Faculdade;
import Faculdade.Trabalho;
import Usuarios.Aluno;
import Usuarios.Orientador;
import modelDao.AlunoDao;
import modelDao.CursoDao;
import modelDao.FaculdadeDao;
import modelDao.OrientadorDao;
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
            st.setLong(2, obj.getFaculdade().getId()); // Assumindo que Faculdade tem um método getId()
            st.setString(3, obj.getDataConclusão());
            st.setLong(4, obj.getAluno().getId()); // Assumindo que Aluno tem um método getId()
            st.setLong(5, obj.getOrientador().getId()); // Assumindo que Orientador tem um método getId()
            st.setLong(6, obj.getCurso().getId()); // Assumindo que Curso tem um método getId()
            st.setString(7, obj.getLocalArquivo());
            st.setInt(8, obj.getScore());
            st.setInt(9, obj.getQuantidadedeVotos());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getLong(1)); // Atualiza o ID gerado para o objeto
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
            st.setLong(2, obj.getFaculdade().getId());
            st.setString(3, obj.getDataConclusão());
            st.setLong(4, obj.getAluno().getId());
            st.setLong(5, obj.getOrientador().getId());
            st.setLong(6, obj.getCurso().getId());
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
        // Recuperar os dados do ResultSet e criar uma instância de Trabalho
        long id = rs.getLong("id");
        String titTrabalho = rs.getString("titTrabalho");
        Faculdade faculdade = findFaculdadeById(rs.getInt("faculdade"));
        String dataConclusao = rs.getString("dataConclusao");
        Aluno aluno = findAlunoById(rs.getInt("aluno"));
        Orientador orientador = findOrientadorById(rs.getInt("orientador"));
        Curso curso = findCursoById(rs.getInt("curso"));
        String localArquivo = rs.getString("localArquivo");
        int score = rs.getInt("score");
        int quantidadedeVotos = rs.getInt("quantidadedeVotos");

        return new Trabalho(id, titTrabalho, faculdade, dataConclusao, aluno, orientador, curso, localArquivo, score,
                quantidadedeVotos);
    }

    private Faculdade findFaculdadeById(int id) {
        FaculdadeDao faculdadeDao = new FaculdadeDaoJDBC(conn);
        return faculdadeDao.findById(id);
    }

    private Aluno findAlunoById(int id) {
        AlunoDao alunoDao = new AlunoDaoJDBC(conn);
        return alunoDao.findById(id);
    }

    private Orientador findOrientadorById(int id) {
        OrientadorDao orientadorDao = new OrientadorDaoJDBC(conn);
        return orientadorDao.findById(id);

    }

    private Curso findCursoById(int id) {
        CursoDao cursoDao = new CursoDaoJDBC(conn);
        return cursoDao.findById(id);
    }
}
