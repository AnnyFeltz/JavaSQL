package br.edu.ifpr.pgua.eic.tads.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cadastro {
    //atributos
    private List<Pessoa> pessoas;

    //controlador
    public Cadastro(){
        this.pessoas = new ArrayList<>();
    }

    //metodos

    //adicionar pessoa
    public void add(Pessoa p){
        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {
        
            System.out.println("Conectado!!");
            String sql = "INSERT INTO oo_contatos(nome, email, telefone) VALUES (?,?,?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getEmail());
            pstm.setString(3, p.getTelefone());

            int res = pstm.executeUpdate();

            if (res == 1){
                System.out.println("Inserido!!");
            } else {


                System.out.println("Deu ruim...");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.pessoas.add(p);
    }

    //getters & setters

    //lista de pessoas
    public List<Pessoa> getPessoas() {
        this.pessoas.clear();

        try (Connection con = DriverManager.getConnection("jdbc:mysql://wagnerweinert.com.br:3306/tads24_ana", "tads24_ana", "tads24_ana")) {

            String sql = "SELECT * FROM oo_contatos";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet result = pstm.executeQuery();

            while (result.next()){
                String nome  = result.getString("nome");
                String email  = result.getString("email");
                String telefone  = result.getString("telefone");

                Pessoa p = new Pessoa(nome, email, telefone);
                pessoas.add(p);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return this.pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

}