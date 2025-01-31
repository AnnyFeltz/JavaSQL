package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager {

    private List<Produto> produto;
    private List<Venda> venda;
    private List<ItemVenda> itemVenda;

    public Manager() {
        produto = new ArrayList<>();
        venda = new ArrayList<>();
        itemVenda = new ArrayList<>();
    }

    //#region Produto
    //adicionar
    public void addProduto(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "INSERT INTO ESTOQUE_PRODUTO(nome, descricao, preco, quantidade_estoque) VALUES (?,?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getDescricao());
            pstm.setDouble(3, p.getPreco());
            pstm.setInt(4, p.getQuantidadeEstoque());

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
    public boolean updateProduto(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {
    
            System.out.println("Conectado!");
            // Atualizar o preço do produto
            String sqlProduto = "UPDATE ESTOQUE_PRODUTO SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ? WHERE id_produto = ?";
            PreparedStatement pstmProduto = con.prepareStatement(sqlProduto);
    
            pstmProduto.setString(1, p.getNome());
            pstmProduto.setString(2, p.getDescricao());
            pstmProduto.setDouble(3, p.getPreco());
            pstmProduto.setInt(4, p.getQuantidadeEstoque());
            pstmProduto.setInt(5, p.getId());
    
            int resProduto = pstmProduto.executeUpdate();
    
            if (resProduto == 1) {
                // Atualizar o subtotal de todos os itens de venda relacionados a esse produto
                String sqlItemVenda = "UPDATE ESTOQUE_ITEM_VENDA SET PRECO_UNITARIO = ?, SUBTOTAL = PRECO_UNITARIO * QUANTIDADE WHERE ID_PRODUTO = ?";
                PreparedStatement pstmItemVenda = con.prepareStatement(sqlItemVenda);
    
                pstmItemVenda.setDouble(1, p.getPreco());
                pstmItemVenda.setInt(2, p.getId());
    
                int resItemVenda = pstmItemVenda.executeUpdate();
    
                // Atualizar o total da venda
                String sqlTotalVenda = "UPDATE ESTOQUE_VENDA v SET v.VALOR_TOTAL = (SELECT SUM(ei.SUBTOTAL) FROM ESTOQUE_ITEM_VENDA ei WHERE ei.ID_VENDA = v.ID_VENDA) WHERE v.ID_VENDA IN (SELECT DISTINCT ID_VENDA FROM ESTOQUE_ITEM_VENDA WHERE ID_PRODUTO = ?)";
                PreparedStatement pstmTotalVenda = con.prepareStatement(sqlTotalVenda);
    
                pstmTotalVenda.setInt(1, p.getId());
                int resTotalVenda = pstmTotalVenda.executeUpdate();
    
                return resProduto == 1 && resItemVenda > 0 && resTotalVenda > 0;  // Retorna true se tudo foi atualizado corretamente
            }
    
            return false; // Retorna false se não atualizou o produto
    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    

    //procurar produto por id
    public Produto getProdutoAtualizar(int id) {
        Produto produto = null; // Inicializando a variável produto

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            // SQL corrigido para fazer SELECT e não UPDATE
            String sql = "SELECT * FROM ESTOQUE_PRODUTO WHERE id_produto = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            // Definir o parâmetro de ID no SQL
            pstm.setInt(1, id);

            // Executar a consulta
            ResultSet rs = pstm.executeQuery();

            // Verificar se encontrou o produto
            if (rs.next()) {
                // Criar o objeto Produto a partir do resultado da consulta
                produto = new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade_estoque")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto por ID: " + e.getMessage());
        }

        return produto;  // Retorna o produto ou null se não encontrado
    }

    //pegar/listar 
    public List<Produto> getProduto() {
        this.produto.clear();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            String sql = "SELECT * FROM ESTOQUE_PRODUTO";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_produto");
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                int quantidadeEstoque = rs.getInt("quantidade_estoque");

                Produto p = new Produto(id, nome, descricao, preco, quantidadeEstoque);
                this.produto.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.produto;
    }

    //#endregion
    //#region Venda
    //pegar/listar
    public List<Venda> getVenda() {
        this.venda.clear();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "SELECT * FROM ESTOQUE_VENDA ";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_venda");
                Date dataVenda = rs.getDate("data_venda");
                String formaPagamento = rs.getString("forma_pagamento");
                double valorTotal = rs.getDouble("valor_total");
                int idCliente = rs.getInt("id_cliente");

                Venda v = new Venda(id, dataVenda, formaPagamento, valorTotal, idCliente);
                this.venda.add(v);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.venda;
    }
    //#endregion

    //#region Item_Venda
    //#endregion

    public List<ItemVenda> getProdutoVendido() {
        this.produto.clear();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            String sql = "SELECT * FROM ESTOQUE_ITEM_VENDA";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_item_venda");
                int quantidade = rs.getInt("quantidade");
                double precoUnitario = rs.getInt("precoUnitario");
                int idVenda = rs.getInt("id_venda");
                Produto produto;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.itemVenda;
    }
}
