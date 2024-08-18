package modelDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Emprest.Emprestimos;
import Emprest.Reserva;
import Obras.Livro;
import Usuarios.Pessoa;
import modelDao.LivroDao;
import modelDao.PessoaDao;
import modelDao.ReservaDao;
import modelDao.EmprestimoDao;

public class ReservaDaoJDBC implements ReservaDao {
    private Connection conn;

    public ReservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
public void insert(Reserva obj) {
    String sql = "INSERT INTO reserva (livro_id, pessoa_id, data_reserva, hora_reserva, status) VALUES (?, ?, ?, ?, ?)";
    try (PreparedStatement st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        st.setInt(1, obj.getLivro().getId());
        st.setInt(2, obj.getUsuario().getId());
        st.setDate(3, java.sql.Date.valueOf(obj.getDataReserva()));
        st.setTime(4, java.sql.Time.valueOf(LocalTime.now()));
        st.setString(5, obj.getStatus());

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
    public void update(Reserva obj) {
        String sql = "UPDATE reserva SET livro_id = ?, pessoa_id = ?, data_reserva = ?, status = ?, emprestimo_id = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, obj.getLivro().getId());
            st.setInt(2, obj.getUsuario().getId());
            st.setDate(3, java.sql.Date.valueOf(obj.getDataReserva()));
            st.setString(4, obj.getStatus());
            if (obj.getEmprestimoFuturo() != null) {
                st.setInt(5, obj.getEmprestimoFuturo().getId());
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
            }
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM reserva WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reserva findById(Integer id) {
        String sql = "SELECT * FROM reserva WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateReserva(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reserva> findAll() {
        String sql = "SELECT * FROM reserva";
        List<Reserva> reservas = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                reservas.add(instantiateReserva(rs));
            }
            return reservas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Reserva instantiateReserva(ResultSet rs) throws SQLException {
        int livroId = rs.getInt("livro_id");
        int pessoaId = rs.getInt("pessoa_id");
        LocalDate dataReserva = rs.getDate("data_reserva").toLocalDate();
        String status = rs.getString("status");

        Reserva reserva = new Reserva(findPessoaById(pessoaId), findLivroById(livroId), dataReserva);
        reserva.setId(rs.getInt("id"));
        reserva.setStatus(status);

        int emprestimoId = rs.getInt("emprestimo_id");
        if (emprestimoId != 0) {
            reserva.setEmprestimoFuturo(findEmprestimoById(emprestimoId));
        }

        return reserva;
    }

    private Pessoa findPessoaById(int id) {
        PessoaDao pessoaDao = new PessoaDaoJDBC(conn);
        return pessoaDao.findById(id);
    }

    private Livro findLivroById(int id) {
        LivroDao livroDao = new LivroDaoJDBC(conn);
        return livroDao.findById(id);
    }

    private Emprestimos findEmprestimoById(int id) {
        EmprestimoDao emprestimoDao = new EmprestimoDaoJDBC(conn);
        return emprestimoDao.findById(id);
    }
}
