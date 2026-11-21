package model.Dao;

import model.Cliente;
import model.Sabores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SaboresDao implements Dao<Sabores>{
    private ConnectionFactory connectionFactory;
    private final String insert = "insert into sabores (nome,idTipoSabor) values (?,?)";
    private final String select = "select * from sabores";
    private final String selectById= "select * from sabores where id=?";
    private final String selectSaborPizza= "select s.id,s.nome,s.idTipoSabor from PizzaSabores p join sabores s on p.idSabor=s.id where idPizza=?";
    private final String update = "update sabores set nome=?, idTipoSabor=? WHERE id=?";
    private final String delete = "delete from sabores WHERE id=?";

    private final TipoSaboresDao tipoSaboresDao;

    public SaboresDao() {
        this.tipoSaboresDao = TipoSaboresDao.getTipoSaboresDao();
    }
    @Override
    public void add(Sabores sabor) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setString(1,sabor.getNome());
            preparedStatement.setLong(2,sabor.getTipoSabor().getId());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    sabor.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public List<Sabores> getAll() throws Exception {
        try(    Connection connection=ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                ResultSet resultSet =preparedStatement.executeQuery();
        ){
            List<Sabores> list = new ArrayList();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                Long idTipoSabor = resultSet.getLong("idTipoSabor");
                list.add(new Sabores(id, tipoSaboresDao.getById(idTipoSabor),nome));
            }
            return list;
        }
    }
    public List<Sabores> getAllPizza(Long id) throws Exception {
        try(    Connection connection=ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectSaborPizza);

        ){
            preparedStatement.setLong(1,id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){

                List<Sabores> list = new ArrayList();
                while (resultSet.next()) {
                    long idSabor = resultSet.getLong("id");
                    String nome = resultSet.getString("nome");
                    Long idTipoSabor = resultSet.getLong("idTipoSabor");
                    list.add(new Sabores(idSabor, tipoSaboresDao.getById(idTipoSabor),nome));

                }
                if (list.isEmpty()) {
                    throw new Exception("Sabores não encontrado com id=" + id);
                }
                return list;
            }

        }
    }

    @Override
    public Sabores getById(long id) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectById);

        ){
            preparedStatement.setLong(1,id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (!resultSet.next()) {
                    throw new Exception("Sabor não encontrado com id=" + id);
                }
                return new Sabores(resultSet.getLong("id"),tipoSaboresDao.getById(resultSet.getLong("idTipoSabor")),resultSet.getString("nome"));
            }

        }
    }

    @Override
    public void update(Sabores sabor) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(update);
        ){
            preparedStatement.setString(1,sabor.getNome());
            preparedStatement.setLong(2,sabor.getTipoSabor().getId());
            preparedStatement.setLong(3,sabor.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Sabores sabor) throws Exception {
        try
                (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement preparedStatement=connection.prepareStatement(delete);)
        {
            preparedStatement.setLong(1,sabor.getId());
            preparedStatement.executeUpdate();
        }
    }
    @Override
    public void deleteAll() throws Exception {

    }
}
