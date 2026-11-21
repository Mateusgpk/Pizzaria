package model.Dao;

import model.Forma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class FormaDao implements Dao<Forma>{

    private ConnectionFactory connectionFactory;
    private final String insert = "insert into formas (lado,tipoforma) values (?,?)";
    private final String select = "select * from formas";
    private final String selectById= "select * from formas where id=?";
    private final String update = "update formas set lado=? tipoforma=? WHERE id=?";
    private final String delete = "delete from formas WHERE id=?";

    @Override
    public void add(Forma forma) throws Exception {
        Long id;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);)
        {
            preparedStatement.setDouble(1, forma.getDimensao());
            preparedStatement.setDouble(2, forma.getNumbForma());
            preparedStatement.execute();
            ResultSet resultSet =preparedStatement.getResultSet();
            resultSet.next();
            long i = resultSet.getLong(1);
            forma.setId(i);
        }

    }

    @Override
    public List<Forma> getAll() throws Exception {
        return List.of();
    }

    @Override
    public Forma getById(long id) throws Exception {
        return null;
    }

    @Override
    public void update(Forma forma) throws Exception {

    }

    @Override
    public void delete(Forma forma) throws Exception {

    }

    @Override
    public void deleteAll() throws Exception {

    }
}
