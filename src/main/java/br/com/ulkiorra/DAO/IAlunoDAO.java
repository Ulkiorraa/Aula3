package br.com.ulkiorra.DAO;

import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Curso;

import java.util.List;
import java.util.Optional;

//define todos os métodos que AlunoDAO possuirá
public interface IAlunoDAO {
    Aluno create(Aluno aluno);
    Aluno update(Aluno aluno);
    void delete(Aluno matricula);
    List<Aluno> findAll();
    Optional<Aluno> findById(Long matricula);
    /*
    Com o Optional, se a matrícula não retorna nada, não dará erro!
     */
    List<Aluno> findByCurso(Curso curso);
}
