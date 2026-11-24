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
    private final String insert = "insert into pedidos (idCliente, status, valorTotal) values (?, ?, ?)";
    private final String select = "select * from pedidos";
    private final String selectById = "select * from pedidos where id=?";
    private final String selectAbertoByCliente = "select * from pedidos where idCliente=? and status='Aberto' order by id desc limit 1";
    private final String update = "update pedidos set idCliente=?, status=?, valorTotal=? where id=?";
    private final String delete = "delete from pedidos WHERE id=?";

    private Pedido montarPedido(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        long idCliente = resultSet.getLong("idCliente");
        String status = resultSet.getString("status");
        double valorTotal = resultSet.getDouble("valorTotal");
        Pedido p = new Pedido(id, clienteDao.getById(idCliente));
        if (status != null) {
            p.setStatus(status);
        }
        p.setValorTotal(valorTotal);
        return p;
    }

    @Override
    public void add(Pedido pedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, pedido.getCliente().getId());
            preparedStatement.setString(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorTotal());
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
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(select);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Pedido> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(montarPedido(resultSet));
            }
            return list;
        }
    }

    @Override
    public Pedido getById(long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectById)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new Exception("Pedido não encontrado com id=" + id);
                }
                return montarPedido(resultSet);
            }
        }
    }

    public Pedido getPedidoAbertoPorCliente(long idCliente) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAbertoByCliente)) {
            preparedStatement.setLong(1, idCliente);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }
                return montarPedido(resultSet);
            }
        }
    }

    @Override
    public void update(Pedido pedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setLong(1, pedido.getCliente().getId());
            preparedStatement.setString(2, pedido.getStatus());
            preparedStatement.setDouble(3, pedido.getValorTotal());
            preparedStatement.setLong(4, pedido.getId());
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
