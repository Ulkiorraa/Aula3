package br.com.ulkiorra.DAO;

import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Cursos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements IAlunoDAO {

    @Override
    public Aluno create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO Alunos" + "(nome, maioridade, curso, sexo)" + "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, aluno.getNome());
            statement.setBoolean(2, aluno.isMaioridade());
            statement.setString(3, aluno.getCurso().toString());
            statement.setString(4, aluno.getSexo());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long matriculaGerada = resultSet.getLong(1);
            aluno.setMatricula(matriculaGerada);
        } catch (SQLException e) {
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
        String query = "SELECT * FROM alunos";
        List<Aluno> list = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Aluno aluno = new Aluno();
                aluno.setMatricula(resultSet.getLong("matricula"));
                aluno.setNome(resultSet.getString("nome"));
                aluno.setMaioridade(resultSet.getBoolean("maioridade"));
                aluno.setCurso(Cursos.valueOf(resultSet.getString("curso")));
                aluno.setSexo(resultSet.getString("sexo"));
                list.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<Aluno> findById(Long matricula) {
        String query = "SELECT * FROM alunos WHERE matricula = ?";
        Aluno aluno = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, matricula);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                aluno = new Aluno(
                        resultSet.getLong("matricula"),
                        resultSet.getString("nome"),
                        resultSet.getBoolean("maioridade"),
                        Cursos.valueOf(resultSet.getString("curso")),
                        resultSet.getString("sexo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(aluno);
    }

    @Override
    public List<Aluno> findByCurso(Cursos curso) {
        String query = "SELECT * FROM alunos WHERE curso = ?";
        List<Aluno> list = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(curso));
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Aluno aluno = new Aluno(
                        resultSet.getLong("matricula"),
                        resultSet.getString("nome"),
                        resultSet.getBoolean("maioridade"),
                        Cursos.valueOf(resultSet.getString("curso")),
                        resultSet.getString("sexo")
                );
                list.add(aluno);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
