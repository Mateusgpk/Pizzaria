package model.Dao;

import model.Cliente;
import model.Pedido;
import model.Sabores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao implements Dao<Pedido>{

    private final ClienteDao clienteDao;

    public PedidoDao() {
        this.clienteDao = ClienteDao.getClientDaoSql();
    }
    private ConnectionFactory connectionFactory;
    private final String insert = "insert into pedidos (idCliente) values (?)";
    private final String select = "select * from pedidos";
    private final String selectById= "select * from pedidos where id=?";
    private final String update = "update pedidos set idCliente=?, status=? where id=?";
    private final String delete = "delete from pedidos WHERE id=?";

    @Override
    public void add(Pedido pedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        ){
            preparedStatement.setLong(1,pedido.getCliente().getId());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public List<Pedido> getAll() throws Exception {
        try(    Connection connection=ConnectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                ResultSet resultSet =preparedStatement.executeQuery();
        ){
            List<Pedido> list = new ArrayList();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idCliente = resultSet.getLong("idCliente");
                String status = resultSet.getString("status");
                Pedido p =new Pedido(id,clienteDao.getById(idCliente));
                if (status != null) {
                    p.setStatus(status);
                }
                list.add(p);
            }
            return list;
        }
    }

    @Override
    public Pedido getById(long id) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectById);

        ){
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (!resultSet.next()) {
                    throw new Exception("Cliente não encontrado com id=" + id);
                }
                return new Pedido(resultSet.getLong("id"),clienteDao.getById(resultSet.getLong("idCliente")));
            }

        }
    }

    @Override
    public void update(Pedido pedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setLong(1, pedido.getCliente().getId());
            preparedStatement.setString(2, pedido.getStatus());
            preparedStatement.setLong(3, pedido.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Pedido pedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setLong(1, pedido.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {

    }
}
