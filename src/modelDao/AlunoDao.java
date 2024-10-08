package modelDao;

import java.util.List;

import Usuarios.Aluno;

public interface AlunoDao {
    void insert(Aluno obj);
    void update(Aluno obj);
    void deleteById(Integer id);
    Aluno findById(Integer id);
    List<Aluno> findAll();
}
