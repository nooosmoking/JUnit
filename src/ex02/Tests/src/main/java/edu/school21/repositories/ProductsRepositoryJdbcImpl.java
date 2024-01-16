package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource source) {
        this.dataSource = source;
    }

    @Override
    public List<Product> findAll() {
        List<Product> produstList = new ArrayList<>();
        String query = "SELECT * FROM shop.products;";
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                int id = result.getInt("identifier");
                String name = result.getString("name");
                int price = result.getInt("price");
                Product currProduct = new Product(id, name, price);
                produstList.add(currProduct);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
        return produstList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = "SELECT * FROM shop.products WHERE identifier = " + id;
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()
        ) {
            ResultSet result = st.executeQuery(query);
            if (!result.next()) {
                return Optional.empty();
            }
            String name = result.getString("name");
            int price = result.getInt("price");
            return Optional.of(new Product(id, name, price));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }

        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE shop.products SET name = ?, price = ? WHERE identifier = ?;";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(query);
        ) {
            st.setString(1, product.getName());
            st.setInt(2, product.getPrice());
            st.setLong(3, product.getId());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO shop.products (name , price) VALUES (?, ?);";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(query);
        ) {
            st.setString(1, product.getName());
            st.setInt(2, product.getPrice());
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM shop.products WHERE identifier = ?;";
        try (Connection con = dataSource.getConnection();
             PreparedStatement st = con.prepareStatement(query);
        ) {
            st.setLong(1, id);
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.exit(-1);
        }
    }
}
