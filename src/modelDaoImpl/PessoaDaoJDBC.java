package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Usuarios.Aluno;
import Usuarios.Funcionario;
import Usuarios.Orientador;
import Usuarios.Pessoa;
import modelDao.PessoaDao;

public class PessoaDaoJDBC implements PessoaDao {
    Connection conn;
    public PessoaDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Pessoa obj) {
       String sql = "INSERT INTO Pessoa (nome, idade, sexo, cpf, telefone, senha, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, obj.getNome());
        stmt.setInt(2, obj.getIdade());
        stmt.setString(3, obj.getSexo());
        stmt.setString(4, obj.getCpf());
        stmt.setString(5, obj.getTelefone());
        stmt.setString(6, obj.getSenha());
        stmt.setString(7, obj.getTipo());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
            }
        } else {
            throw new SQLException("Erro ao inserir a pessoa, nenhuma linha afetada.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void update(Pessoa obj) {
        String sql = "UPDATE Pessoa SET nome = ?, idade = ?, sexo = ?, cpf = ?, telefone = ?, senha = ?, tipo = ? WHERE id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, obj.getNome());
        stmt.setInt(2, obj.getIdade());
        stmt.setString(3, obj.getSexo());
        stmt.setString(4, obj.getCpf());
        stmt.setString(5, obj.getTelefone());
        stmt.setString(6, obj.getSenha());
        stmt.setString(7, obj.getTipo());
        stmt.setInt(8, obj.getId());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Atualização falhou, nenhum registro foi modificado.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM Pessoa WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
    
            int affectedRows = stmt.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Exclusão falhou, nenhum registro foi deletado.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public Pessoa findById(Integer id) {
        String sql = "SELECT * FROM Pessoa WHERE id = ?";
    Pessoa pessoa = null;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String sexo = rs.getString("sexo");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String tipo = rs.getString("tipo");

                switch (tipo) {
                    case "Aluno":
                        pessoa = new Aluno(nome, idade, sexo, cpf, telefone, senha);
                        break;
                    case "Funcionario":
                        pessoa = new Funcionario(nome, idade, sexo, telefone, senha, 0.0, "", cpf);
                        break;
                    case "Orientador":
                        pessoa = new Orientador(nome, idade, sexo, telefone, senha);
                        break;
                    default:
                        throw new SQLException("Tipo de pessoa desconhecido: " + tipo);
                }
                pessoa.setId(rs.getInt("id"));
                pessoa.setCpf(cpf);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return pessoa;
    }

    @Override
    public List<Pessoa> findAll() {
        String sql = "SELECT * FROM Pessoa";
        List<Pessoa> pessoas = new ArrayList<>();
    
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
    
            while (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String sexo = rs.getString("sexo");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String tipo = rs.getString("tipo");
    
                Pessoa pessoa = null;
                switch (tipo) {
                    case "Aluno":
                        pessoa = new Aluno(nome, idade, sexo, telefone, cpf, senha);
                        break;
                    case "Funcionario":
                        pessoa = new Funcionario(nome, idade, sexo, telefone, senha, 0.0, "", cpf);
                        break;
                    case "Orientador":
                        pessoa = new Orientador(nome, idade, sexo, telefone, senha);
                        break;
                    default:
                        throw new SQLException("Tipo de pessoa desconhecido: " + tipo);
                }
                pessoa.setId(rs.getInt("id"));
                pessoa.setCpf(cpf);
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return pessoas;
    }
public Pessoa getPessoaByUsernameAndPassword(String username, String password) throws SQLException {
    String sql = "SELECT * FROM Pessoa WHERE cpf = ? AND senha = ?";
    Pessoa pessoa = null;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, username);
        stmt.setString(2, password);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                String sexo = rs.getString("sexo");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String senha = rs.getString("senha");
                String tipo = rs.getString("tipo");

                switch (tipo) {
                    case "Aluno":
                        pessoa = new Aluno(nome, idade, sexo, cpf, telefone, senha);
                        break;
                    case "Funcionario":
                        pessoa = new Funcionario(nome, idade, sexo, telefone, senha, 0.0, "", cpf);
                        break;
                    case "Orientador":
                        pessoa = new Orientador(nome, idade, sexo, telefone, senha);
                        break;
                    default:
                        throw new SQLException("Tipo de pessoa desconhecido: " + tipo);
                }
                pessoa.setId(rs.getInt("id"));
                pessoa.setCpf(cpf);
            }
        }
    }

    return pessoa;
}

public Pessoa findByUsernameAndPassword(String username, String password) throws SQLException {
    String sql = "SELECT * FROM Pessoa WHERE nome = ? AND senha = ?";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setString(1, username);
        statement.setString(2, password);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return mapResultSetToPessoa(resultSet);
            }
        }
    }
    return null;
}

public Pessoa mapResultSetToPessoa(ResultSet rs) throws SQLException {
    String tipo = rs.getString("tipo");

    Pessoa pessoa = null;

    switch (tipo) {
        case "Funcionario":
            pessoa = new Funcionario();
            break;
        case "Aluno":
            pessoa = new Aluno();
            break;
        case "Orientador":
            pessoa = new Orientador();
            break;
        default:
            throw new IllegalArgumentException("Tipo de pessoa desconhecido: " + tipo);
    }

    pessoa.setId(rs.getInt("id"));
    pessoa.setNome(rs.getString("nome"));
    pessoa.setSenha(rs.getString("senha"));

    return pessoa;
}


}
