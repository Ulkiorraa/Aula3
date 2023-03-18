package br.com.ulkiorra.DAO;

import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements IAlunoDAO {

    @Override
    public Aluno create(Aluno aluno) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO alunos" + "(nome, maioridade, curso, sexo, curso_sigla)" + "VALUES (?, ?, ?, ?, ?)";
            String query2 = "SELECT sigla FROM curso WHERE nome = ?";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1, aluno.getCurso());
            ResultSet resultSet2 = statement2.executeQuery();
            resultSet2.next();
            String sigla = resultSet2.getString("sigla");
            statement.setString(1, aluno.getNome());
            statement.setBoolean(2, aluno.isMaioridade());
            statement.setString(3, aluno.getCurso());
            statement.setString(4, aluno.getSexo());
            statement.setString(5, sigla);
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
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE alunos SET " + "nome = ?, maioridade = ?, curso = ?, sexo = ?" + "WHERE matricula = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, aluno.getNome());
            statement.setBoolean(2, aluno.isMaioridade());
            statement.setString(3, aluno.getCurso());
            statement.setString(4, aluno.getSexo());
            statement.setLong(5, aluno.getMatricula());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return aluno;
    }

    @Override
    public void delete(Long matricula) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM alunos WHERE matricula =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, matricula);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                aluno.setCurso(resultSet.getString("curso"));
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
                        resultSet.getString("curso"),
                        resultSet.getString("sexo")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(aluno);
    }

    @Override
    public List<Aluno> findByCurso(Curso curso) {
        String query = "SELECT * FROM alunos WHERE curso_sigla = ?";
        List<Aluno> list = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, curso.getSigla());
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Aluno aluno = new Aluno(
                        resultSet.getLong("matricula"),
                        resultSet.getString("nome"),
                        resultSet.getBoolean("maioridade"),
                        resultSet.getString("curso"),
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
