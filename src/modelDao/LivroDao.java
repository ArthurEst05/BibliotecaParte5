package modelDao;

import java.util.List;

import Obras.Livro;

public interface LivroDao {
    void insert(Livro obj);
    void update(Livro obj);
    void deleteById(Integer id);
    Livro findById(Integer id);
    Livro findByTitulo(String titulo);
    List<Livro> findAll();

}
