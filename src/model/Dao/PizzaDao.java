package model.Dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PizzaDao implements Dao<Pizza>{
    private final SaboresDao saboresDao;

    public PizzaDao() {
        this.saboresDao = new SaboresDao();
    }
    private ConnectionFactory connectionFactory;
    private final String insert ="""
        INSERT INTO pizza ( cms, tipoForma, idPedido,dimensao)
        VALUES (?, ?, ?, ?)
    """;
    private final String insertSabores = """
                INSERT INTO PizzaSabores (idPizza, idSabor)
                VALUES (?, ?)
            """;
    private final String select = "select * from pizza";
    private final String selectByIdPedido= "select * from pizza where idPedido=?";
    private final String update = "update pizza set tipoForma=?, dimensao=? WHERE id=?";
    private final String delete = "delete from pizza WHERE id=?";
    private final String deleteByIdPedido = "delete from pizza where idPedido=?";


    @Override
    public void add(Pizza pizza) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);)
        {
            preparedStatement.setDouble(1, pizza.getCms());
            preparedStatement.setLong(2,pizza.getTipoForma());
            preparedStatement.setLong(3,pizza.getIdPedido());
            preparedStatement.setDouble(4,pizza.getDimensao());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    pizza.setId(rs.getLong(1));
                }
            }
        }
        for (Sabores sabor : pizza.getSabores() ) {
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(insertSabores, Statement.RETURN_GENERATED_KEYS);) {
                preparedStatement.setLong(1, pizza.getId());
                preparedStatement.setLong(2, sabor.getId());
                preparedStatement.executeUpdate();
            }
        }
    }

    public void deleteByIdPedido(Long idPedido) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdPedido)) {
            preparedStatement.setLong(1, idPedido);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Pizza> getAll() throws Exception {
        return List.of();
    }


    public List<Pizza> getAllPizzasPedido(Long idPedido) throws Exception {
        try(Connection connection=ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectByIdPedido);

        ){

            preparedStatement.setLong(1,idPedido);
            ResultSet resultSet =preparedStatement.executeQuery();
            List<Pizza> list = new ArrayList();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                Forma forma=criarForma(resultSet.getInt("tipoForma"),resultSet.getDouble("dimensao"));
                list.add(new Pizza( id,resultSet.getLong("idPedido"),saboresDao.getAllPizza(id),forma));
            }
            return list;
        }

    }

    public Forma criarForma(int tipo, double dimensao) {
        return switch (tipo) {
            case 1 -> new Circulo(dimensao);
            case 2 -> new Quadrado(dimensao);
            case 3 -> new Triangulo(dimensao);
            default -> throw new IllegalArgumentException("Forma inválida");
        };
    }

    @Override
    public Pizza getById(long id) throws Exception {
        return null;
    }

    @Override
    public void update(Pizza pizza) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(update);
        ){
            preparedStatement.setInt(1,pizza.getTipoForma());
            preparedStatement.setDouble(2,pizza.getDimensao());
            preparedStatement.setLong(3,pizza.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Pizza pizza) throws Exception {
        try
                (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement preparedStatement=connection.prepareStatement(delete);)
        {
            preparedStatement.setLong(1,pizza.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {

    }
}
