package modelDao;

import java.util.List;

import Faculdade.Trabalho;


public interface TrabalhoDao {
    void insert(Trabalho obj);
    void update(Trabalho obj);
    void deleteById(Integer id);
    Trabalho findById(Integer id);
    List<Trabalho> findAll();
}
