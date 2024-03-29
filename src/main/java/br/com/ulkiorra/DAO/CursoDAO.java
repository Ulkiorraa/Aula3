package br.com.ulkiorra.DAO;

import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;
import br.com.ulkiorra.util.Alerts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CursoDAO implements ICursoDAO {
    Alerts alerts = new Alerts();

    @Override
    public Curso create(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO curso" + "(nome, sigla, area)" + "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, curso.getNome());
            statement.setString(2, curso.getSigla());
            statement.setString(3, curso.getArea().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long codigoGerado = resultSet.getLong(1);
            curso.setCodigo(codigoGerado);
            return curso;
        } catch (SQLException e) {
            alerts.mostrarMensagemDeErro("Erro de Criação!", null, e.getMessage());
        }
        return null;
    }

    @Override
    public Curso update(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE curso SET " + "nome = ?, sigla = ?, area = ?" + "WHERE codigo = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, curso.getNome());
            statement.setString(2, curso.getSigla());
            statement.setString(3, curso.getArea().toString());
            statement.setLong(4, curso.getCodigo());
            statement.executeUpdate();
            return curso;
        } catch (SQLException e) {
            alerts.mostrarMensagemDeErro("Erro de Update!", null, e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Curso codigo) {
        AlunoDAO alunoDAO = new AlunoDAO();
        List<Aluno> list = alunoDAO.findAll();
        for (Aluno a : list) {
            if (Objects.equals(a.getCurso(), codigo.getNome())) {
                alerts.mostrarMensagemDeErro("Erro!", "Aluno cadastrado no curso!", "Você tem ao menos um aluno cadastrado no curso o mesmo não pode ser deletado!");
                return;
            }
        }
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM curso WHERE codigo =?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, codigo.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findAll() {
        String query = "SELECT * FROM curso";
        List<Curso> list = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Areas.valueOf(resultSet.getString("area")));
                list.add(curso);
            }
            return list;
        } catch (SQLException e) {
            alerts.mostrarMensagemDeErro("Erro de FindAll!", null, e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<Curso> findById(Long codigo) {
        String query = "SELECT * FROM curso WHERE codigo = ?";
        Curso curso = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, codigo);
            statement.executeQuery();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                curso = new Curso(
                        resultSet.getLong("codigo"),
                        resultSet.getString("nome"),
                        resultSet.getString("sigla"),
                        Areas.valueOf(resultSet.getString("area"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(curso);
    }

    @Override
    public List<Curso> findByCurso(Areas area) {
        return null;
    }

    @Override
    public List<Curso> findBySigla(String sigla) {
        return null;
    }
}
