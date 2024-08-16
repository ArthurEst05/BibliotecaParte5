package modelDao;

import java.util.List;

import Usuarios.Orientador;

public interface OrientadorDao {
    void insert(Orientador obj);
    void update(Orientador obj);
    void deleteById(Integer id);
    Orientador findById(Integer id);
    List<Orientador> findAll();

}
