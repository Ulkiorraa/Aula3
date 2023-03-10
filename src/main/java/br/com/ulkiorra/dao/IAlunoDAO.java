package br.com.ulkiorra.dao;

import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Cursos;

import java.util.List;
import java.util.Optional;

//define todos os métodos que AlunoDAO possuirá
public interface IAlunoDAO {
    Aluno create(Aluno aluno);
    Aluno update(Aluno aluno);
    void delete(Long matricula);
    List<Aluno> findAll();
    Optional<Aluno> findById(Long matricula);
    /*
    Com o Optional, se a matrícula não retorna nada, não dará erro!
     */
    List<Aluno> findByCurso(Cursos curso);
}
