package modelDao;

import java.util.List;

import Usuarios.Pessoa;


public interface PessoaDao {
    void insert(Pessoa obj);
    void update(Pessoa obj);
    void deleteById(Integer id);
    Pessoa findById(Integer id);
    List<Pessoa> findAll();
}
