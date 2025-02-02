package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {

    private List<Produto> produto;
    private List<Venda> venda;
    private List<ItemVenda> itemVenda;

    public Manager() {
        produto = new ArrayList<>();
        venda = new ArrayList<>();
        itemVenda = new ArrayList<>();
    }

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
            String sqlProduto = "UPDATE ESTOQUE_PRODUTO SET nome = ?, descricao = ?, preco = ?, quantidade_estoque = ? WHERE id_produto = ?";
            PreparedStatement pstmProduto = con.prepareStatement(sqlProduto);

            pstmProduto.setString(1, p.getNome());
            pstmProduto.setString(2, p.getDescricao());
            pstmProduto.setDouble(3, p.getPreco());
            pstmProduto.setInt(4, p.getQuantidadeEstoque());
            pstmProduto.setInt(5, p.getId());

            int resProduto = pstmProduto.executeUpdate();

            if (resProduto == 1) {
                String sqlItemVenda = "UPDATE ESTOQUE_ITEM_VENDA SET PRECO_UNITARIO = ?, SUBTOTAL = PRECO_UNITARIO * QUANTIDADE WHERE ID_PRODUTO = ?";
                PreparedStatement pstmItemVenda = con.prepareStatement(sqlItemVenda);

                pstmItemVenda.setDouble(1, p.getPreco());
                pstmItemVenda.setInt(2, p.getId());

                int resItemVenda = pstmItemVenda.executeUpdate();

                String sqlTotalVenda = "UPDATE ESTOQUE_VENDA v SET v.VALOR_TOTAL = (SELECT SUM(ei.SUBTOTAL) FROM ESTOQUE_ITEM_VENDA ei WHERE ei.ID_VENDA = v.ID_VENDA) WHERE v.ID_VENDA IN (SELECT DISTINCT ID_VENDA FROM ESTOQUE_ITEM_VENDA WHERE ID_PRODUTO = ?)";
                PreparedStatement pstmTotalVenda = con.prepareStatement(sqlTotalVenda);

                pstmTotalVenda.setInt(1, p.getId());
                int resTotalVenda = pstmTotalVenda.executeUpdate();

                return resProduto == 1;
            }

            return false;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //procurar produto por id
    public Produto getProdutoAtualizar(int id) {
        Produto produto = null;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");
            String sql = "SELECT * FROM ESTOQUE_PRODUTO WHERE id_produto = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
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

        return produto; 
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

    //pegar produtos vendidos
    public List<Map<String, Object>> getProdutoVendido(int idVenda) {
        List<Map<String, Object>> produtosDaVenda = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            System.out.println("Conectado!");

            String sql = "SELECT p.id_produto, p.nome, iv.quantidade, p.preco, iv.subtotal "
                    + "FROM ESTOQUE_ITEM_VENDA iv "
                    + "JOIN ESTOQUE_PRODUTO p ON iv.id_produto = p.id_produto "
                    + "WHERE iv.id_venda = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, idVenda);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Map<String, Object> produtoDetalhado = new HashMap<>();
                produtoDetalhado.put("idProduto", rs.getInt("id_produto"));
                produtoDetalhado.put("nome", rs.getString("nome"));
                produtoDetalhado.put("quantidade", rs.getInt("quantidade"));
                produtoDetalhado.put("preco", rs.getDouble("preco"));
                produtoDetalhado.put("subtotal", rs.getDouble("subtotal"));

                produtosDaVenda.add(produtoDetalhado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos da venda: " + e.getMessage());
        }

        return produtosDaVenda;
    }

    //deletar venda
    public boolean deleteVenda(int id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {
            String sqlItens = "DELETE FROM ESTOQUE_ITEM_VENDA WHERE id_venda = ?";
            PreparedStatement pstmItens = con.prepareStatement(sqlItens);
            pstmItens.setInt(1, id);
            pstmItens.executeUpdate();

            String sqlVenda = "DELETE FROM ESTOQUE_VENDA WHERE id_venda = ?";
            PreparedStatement pstmVenda = con.prepareStatement(sqlVenda);
            pstmVenda.setInt(1, id);
            int rowsAffected = pstmVenda.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar venda: " + e.getMessage());
            return false;
        }
    }

    //deletar produto
    public boolean deleteProduto(int id) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {
    
            String sqlVendasComProduto = "SELECT DISTINCT ei.ID_VENDA FROM ESTOQUE_ITEM_VENDA ei WHERE ei.ID_PRODUTO = ?";
            PreparedStatement pstmVendasComProduto = con.prepareStatement(sqlVendasComProduto);
            pstmVendasComProduto.setInt(1, id);
            ResultSet rsVendas = pstmVendasComProduto.executeQuery();
    
            List<Integer> vendasIds = new ArrayList<>();
            while (rsVendas.next()) {
                vendasIds.add(rsVendas.getInt("ID_VENDA"));
            }
    
            String sqlDeleteItemVenda = "DELETE FROM ESTOQUE_ITEM_VENDA WHERE ID_PRODUTO = ?";
            PreparedStatement pstmDeleteItemVenda = con.prepareStatement(sqlDeleteItemVenda);
            pstmDeleteItemVenda.setInt(1, id);
            int rowsAffectedItens = pstmDeleteItemVenda.executeUpdate(); 
    
            String sqlDeleteProduto = "DELETE FROM ESTOQUE_PRODUTO WHERE ID_PRODUTO = ?";
            PreparedStatement pstmDeleteProduto = con.prepareStatement(sqlDeleteProduto);
            pstmDeleteProduto.setInt(1, id);
            int rowsAffectedProduto = pstmDeleteProduto.executeUpdate();
    
            if (!vendasIds.isEmpty()) {
                StringBuilder vendasInClause = new StringBuilder();
                for (int i = 0; i < vendasIds.size(); i++) {
                    vendasInClause.append("?");
                    if (i < vendasIds.size() - 1) {
                        vendasInClause.append(", ");
                    }
                }
    
                String sqlAtualizaVendas = "UPDATE ESTOQUE_VENDA v SET v.VALOR_TOTAL = (SELECT SUM(ei.SUBTOTAL) FROM ESTOQUE_ITEM_VENDA ei WHERE ei.ID_VENDA = v.ID_VENDA) WHERE v.ID_VENDA IN (" + vendasInClause + ")";
                PreparedStatement pstmAtualizaVendas = con.prepareStatement(sqlAtualizaVendas);
    
                for (int i = 0; i < vendasIds.size(); i++) {
                    pstmAtualizaVendas.setInt(i + 1, vendasIds.get(i));
                }
    
                int rowsAffectedVendas = pstmAtualizaVendas.executeUpdate();
    
                return rowsAffectedItens > 0 && rowsAffectedProduto > 0 && rowsAffectedVendas > 0;
            } else {
                return false;
            }
    
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar as tabelas referenciadas: " + e.getMessage());
            return false;
        }
    }
    

}
