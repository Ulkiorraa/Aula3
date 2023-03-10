package br.com.ulkiorra.DAO;

import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Cursos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements IAlunoDAO {

    @Override
    public Aluno create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO Alunos" + "(nome, maioridade, curso, sexo)" + "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, aluno.getNome());
            statement.setBoolean(2, aluno.isMaioridade());
            statement.setString(3, aluno.getCurso().toString());
            statement.setString(4, aluno.getSexo());
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return aluno;
    }

    @Override
    public Aluno update(Aluno aluno) {
        return null;
    }

    @Override
    public void delete(Long matricula) {

    }

    @Override
    public List<Aluno> findAll() {
        return null;
    }

    @Override
    public Optional<Aluno> findById(Long matricula) {
        return Optional.empty();
    }

    @Override
    public List<Aluno> findByCurso(Cursos curso) {
        return null;
    }
}
