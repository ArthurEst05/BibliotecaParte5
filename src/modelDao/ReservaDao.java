package modelDao;

import java.util.List;

import Emprest.Reserva;



public interface ReservaDao {
    void insert(Reserva obj);
    void update(Reserva obj);
    void deleteById(Integer id);
    Reserva findById(Integer id);
    List<Reserva> findAll();
}
