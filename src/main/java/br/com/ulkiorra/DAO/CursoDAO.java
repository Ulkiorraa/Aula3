package br.com.ulkiorra.DAO;

import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class CursoDAO implements ICursoDAO {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }

    @Override
    public void delete(Long codigo) {

    }

    @Override
    public List<Curso> findAll() {
        return null;
    }

    @Override
    public Optional<Curso> findById(Long codigo) {
        return Optional.empty();
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
