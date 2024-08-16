package modelDao;

import java.util.List;

import Emprest.Emprestimos;

public interface EmprestimoDao {
    void insert(Emprestimos obj);
    void update(Emprestimos obj);
    void deleteById(Integer id);
    Emprestimos findById(Integer id);
    List<Emprestimos> findAll();
}
