package model.Dao;
import model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao implements Dao<Cliente>{

    private ConnectionFactory connectionFactory;
    private final String insert = "insert into clientes (nome,sobrenome,telefone) values (?,?,?)";
    private final String select = "select * from clientes";
    private final String selectById= "select * from clientes where id=?";
    private final String update = "update clientes set nome=?, sobrenome=?, telefone=? WHERE id=?";
    private final String delete = "delete from clientes WHERE id=?";

    public ClienteDao(){

    }

    private static ClienteDao dao;

    public static ClienteDao getClientDaoSql(){
        if(dao==null)
            return dao = new ClienteDao();
        else
            return dao;
    }

    @Override
    public void add(Cliente cliente) throws Exception{
        try (
                Connection connection=ConnectionFactory.getConnection();
                PreparedStatement stmtAdiciona = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);)
        {
            stmtAdiciona.setString(1,cliente.getNome());
            stmtAdiciona.setString(2,cliente.getSobrenome());
            stmtAdiciona.setString(3, cliente.getTelefone());

            stmtAdiciona.executeUpdate();
            try (ResultSet rs = stmtAdiciona.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        }
    }


    @Override
    public List<Cliente> getAll() throws Exception {
        try(    Connection connection=ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                ResultSet resultSet =preparedStatement.executeQuery();
        ){
            List<Cliente> list = new ArrayList();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String nome = resultSet.getString("nome");
                String sobrenome = resultSet.getString("sobrenome");
                String telefone = resultSet.getString("telefone");
                list.add(new Cliente(id, nome, sobrenome, telefone));
            }
            return list;
        }
    }

    @Override
    public Cliente getById(long id) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectById);

        ){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (!resultSet.next()) {
                    throw new Exception("Cliente não encontrado com id=" + id);
                }
                return new Cliente(resultSet.getLong("id"),resultSet.getString("nome"),resultSet.getString("sobrenome"),resultSet.getString("telefone"));
            }

        }


    }

    @Override
    public void update(Cliente cliente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(update);
        ){
            preparedStatement.setString(1,cliente.getNome());
            preparedStatement.setString(2,cliente.getSobrenome());
            preparedStatement.setString(3, cliente.getTelefone());
            preparedStatement.setLong(4,cliente.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Cliente cliente) throws Exception {
        try
            (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement preparedStatement=connection.prepareStatement(delete);)
        {
            preparedStatement.setLong(1,cliente.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {

    }
}
