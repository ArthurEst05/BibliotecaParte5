package modelDao;

import java.util.List;

import Faculdade.Curso;


public interface CursoDao {
    void insert(Curso obj);
    void update(Curso obj);
    void deleteById(Integer id);
    Curso findById(Integer id);
    List<Curso> findAll();
}
