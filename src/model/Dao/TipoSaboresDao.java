package model.Dao;

import model.Cliente;
import model.TipoSabor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TipoSaboresDao implements Dao<TipoSabor>{

    private ConnectionFactory connectionFactory;
    private final String insert = "insert into TipoSabores (nome,precocm) values (?,?)";
    private final String select = "select * from TipoSabores";
    private final String selectById= "select * from TipoSabores where id=?";
    private final String update = "update TipoSabores set precocm=? WHERE id=?";

    private TipoSaboresDao(){
    }
    private static TipoSaboresDao dao;
    public static TipoSaboresDao getTipoSaboresDao(){
        if(dao==null)
            return dao = new TipoSaboresDao();
        else
            return dao;
    }

    @Override
    public void add(TipoSabor tipoSabor) throws Exception {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);){
            preparedStatement.setString(1,tipoSabor.getNome());
            preparedStatement.setDouble(2,tipoSabor.getPreco());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    tipoSabor.setId(rs.getLong(1));
                }
            }
        }

    }

    @Override
    public List<TipoSabor> getAll() throws Exception {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(select);
             ResultSet resultSet =preparedStatement.executeQuery();){
            List<TipoSabor> listTipoSabores = new ArrayList();
            while(resultSet.next()){
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                Double preco = resultSet.getDouble("precocm");
                listTipoSabores.add(new TipoSabor(id, nome, preco));
            }
            return listTipoSabores;
        }
    }

    @Override
    public TipoSabor getById(long id) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        ){
            preparedStatement.setLong(1,id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (!resultSet.next()) {
                    throw new Exception("tipoSabor não encontrado com id=" + id);
                }
                return new TipoSabor(resultSet.getLong("id"),resultSet.getString("nome"),resultSet.getDouble("precocm"));
            }

        }
    }

    @Override
    public void update(TipoSabor tipoSabor) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(update);
        ){
            preparedStatement.setDouble(1,tipoSabor.getPreco());
            preparedStatement.setLong(2,tipoSabor.getId());
            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void delete(TipoSabor objeto) throws Exception {

    }

    @Override
    public void deleteAll() throws Exception {

    }
}
