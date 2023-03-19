package br.com.ulkiorra.DAO;

import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoDAO {
    Curso create(Curso curso);
    Curso update(Curso curso);
    void delete(Curso codigo);
    List<Curso> findAll();
    Optional<Curso> findById(Long codigo);
    /*
    Com o Optional, se a matrícula não retorna nada, não dará erro!
     */
    List<Curso> findByCurso(Areas area);
    List<Curso> findBySigla(String sigla);
}
