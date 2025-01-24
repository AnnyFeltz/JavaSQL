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
    public boolean updateProduto(Produto p) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "UPDATE ESTOQUE_PRODUTO SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ? WHERE id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getDescricao());
            pstm.setDouble(3, p.getPreco());
            pstm.setInt(4, p.getQuantidadeEstoque());
            pstm.setInt(6, p.getId());

            int res = pstm.executeUpdate();

            return res == 1;  // Retorna true se uma linha foi atualizada, false caso contrário

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;  // Retorna false em caso de erro
        }
    }

    //procurar produto por id
    public Produto getProdutoAtualizar(int id) {
        Produto produto = null; // Inicializando a variável produto

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            // SQL corrigido para fazer SELECT e não UPDATE
            String sql = "SELECT * FROM ESTOQUE_PRODUTO WHERE id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            // Definir o parâmetro de ID no SQL
            pstm.setInt(1, id);

            // Executar a consulta
            ResultSet rs = pstm.executeQuery();

            // Verificar se encontrou o produto
            if (rs.next()) {
                // Criar o objeto Produto a partir do resultado da consulta
                produto = new Produto(
                        rs.getInt("id"),
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
            String sql = "SELECT * FROM ESTOQUE_VENDA";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Date dataVenda = rs.getDate("data_venda");
                String formaPagamento = rs.getString("forma_pagamento");
                double valorTotal = rs.getDouble("valor_total");
                int idCliente = rs.getInt("id_cliente");

                Venda v = new Venda(dataVenda, formaPagamento, valorTotal, idCliente);
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
}
