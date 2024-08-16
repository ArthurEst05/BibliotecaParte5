package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Usuarios.Funcionario;
import modelDao.FuncionarioDao;

public class FuncionarioDaoJDBC implements FuncionarioDao {
    private Connection conn;

    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Funcionario obj) {
        String sql = "INSERT INTO funcionario (nome, idade, sexo, telefone, cargo, salario, endereco, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, obj.getIdade());
            stmt.setString(3, obj.getSexo());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCargo());
            stmt.setDouble(6, obj.getSalario());
            stmt.setString(7, obj.getEndereço());
            stmt.setString(8, obj.getSenha());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Funcionario", e);
        }
    }

    @Override
    public void update(Funcionario obj) {
        String sql = "UPDATE funcionario SET nome = ?, idade = ?, sexo = ?, telefone = ?, cargo = ?, salario = ?, endereco = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, obj.getNome());
            stmt.setInt(2, obj.getIdade());
            stmt.setString(3, obj.getSexo());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getCargo());
            stmt.setDouble(6, obj.getSalario());
            stmt.setString(7, obj.getEndereço());
            stmt.setString(8, obj.getSenha());
            stmt.setInt(9, obj.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating Funcionario", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Funcionario", e);
        }
    }

    @Override
    public Funcionario findById(Integer id) {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instantiateFuncionario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding Funcionario by id", e);
        }
        return null;
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> list = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(instantiateFuncionario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all Funcionarios", e);
        }
        return list;
    }

    private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario(
                rs.getString("nome"),
                rs.getInt("idade"),
                rs.getString("sexo"),
                rs.getString("telefone"),
                rs.getString("cargo"),
                rs.getDouble("salario"),
                rs.getString("endereco"),
                rs.getString("senha")
        );
        funcionario.setId(rs.getInt("id"));
        return funcionario;
    }
}
