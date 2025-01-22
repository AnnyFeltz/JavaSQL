package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoManager {

    private List<Produto> produto;

    public ProdutoManager() {
        produto = new ArrayList<>();
    }

    public void add(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "";
            PreparedStatement pstm = con.prepareStatement(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.produto.add(p);
    }

    public void update(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "";
            PreparedStatement pstm = con.prepareStatement(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Produto> getProduto() {
        this.produto.clear();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.produto;
    }

}
}
