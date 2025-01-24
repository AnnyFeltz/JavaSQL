package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    private List<Produto> produto;
    private List<Venda> venda;

    public Manager() {
        produto = new ArrayList<>();
        venda = new ArrayList<>();
    }

    //#region Produto

    //adicionar
    public void addProduto(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "INSERT INTO ESTOQUE_PRODUTO(nome, descricao, preco, quantidade_estoque, ativo) VALUES (?,?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getDescricao());
            pstm.setDouble(3, p.getPreco());
            pstm.setInt(4, p.getQuantidadeEstoque());
            pstm.setBoolean(5, p.isAtivo());

            //frufru
            int res = pstm.executeUpdate();
            if (res == 1) {
                System.out.println("Inserido!");
            } else {
                System.out.println("Deu ruim!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.produto.add(p);
    }

    //atualizar
    public void updateProduto(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "";
            PreparedStatement pstm = con.prepareStatement(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //pegar/listar 
    public List<Produto> getProduto() {
        this.produto.clear();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            String sql = "SELECT * FROM ESTOQUE_PRODUTO WHERE ativo = true";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                int quantidadeEstoque = rs.getInt("quantidade_estoque");
                boolean ativo = rs.getBoolean("ativo");

                Produto p = new Produto(nome, descricao, preco, quantidadeEstoque, ativo);
                this.produto.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.produto;
    }

    //desativar produto
    
    //#endregion

    //#region Venda

    //pegar/listar
    public List<Venda> getVenda() {
        this.venda.clear();
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

        return this.venda;
    }
    //#endregion
}

