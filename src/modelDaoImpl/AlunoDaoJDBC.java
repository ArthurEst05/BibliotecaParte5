package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Usuarios.Aluno;
import modelDao.AlunoDao;

public class AlunoDaoJDBC implements AlunoDao {
    private Connection conn;

    public AlunoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Aluno obj) {
        String sql = "INSERT INTO Pessoa (nome, idade, sexo, cpf, telefone, senha, tipo, instituicaoEducacional, matricula) VALUES (?, ?, ?, ?, ?, ?, 'Aluno', ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, obj.getIdade());
            stmt.setString(3, obj.getSexo());
            stmt.setString(4, obj.getCpf());
            stmt.setString(5, obj.getTelefone());
            stmt.setString(6, obj.getSenha());
            stmt.setString(7, obj.getInstituicaoEducacional());
            stmt.setString(8, obj.getMatricula());

            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        obj.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Failed to insert aluno, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void update(Aluno obj) {
        String sql = "UPDATE Pessoa SET nome = ?, idade = ?, sexo = ?, telefone = ?, senha = ?, instituicaoEducacional = ?, matricula = ? WHERE id = ? AND tipo = 'Aluno'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, obj.getIdade());
            stmt.setString(3, obj.getSexo());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getSenha());
            stmt.setString(6, obj.getInstituicaoEducacional());
            stmt.setString(7, obj.getMatricula());
            stmt.setInt(8, obj.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Pessoa WHERE id = ? AND tipo = 'Aluno'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public Aluno findById(Integer id) {
        String sql = "SELECT * FROM Pessoa WHERE id = ? AND tipo = 'Aluno'";
        Aluno aluno = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    aluno = new Aluno(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("sexo"),
                        rs.getString("telefone"),
                        rs.getString("instituicaoEducacional"),
                        rs.getString("matricula"),
                        rs.getString("senha")
                    );
                    aluno.setId(rs.getInt("id"));
                    aluno.setCpf(rs.getString("cpf"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return aluno;
    }

    @Override
    public List<Aluno> findAll() {
        String sql = "SELECT * FROM Pessoa WHERE tipo = 'Aluno'";
        List<Aluno> alunos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("sexo"),
                    rs.getString("telefone"),
                    rs.getString("instituicaoEducacional"),
                    rs.getString("matricula"),
                    rs.getString("senha")
                );
                aluno.setId(rs.getInt("id"));
                aluno.setCpf(rs.getString("cpf"));

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alunos;
    }
}
