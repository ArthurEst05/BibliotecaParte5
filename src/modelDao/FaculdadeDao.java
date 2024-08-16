package modelDao;

import java.util.List;

import Faculdade.Faculdade;


public interface FaculdadeDao {
    void insert(Faculdade obj);
    void update(Faculdade obj);
    void deleteById(Integer id);
    Faculdade findById(Integer id);
    List<Faculdade> findAll();
}
