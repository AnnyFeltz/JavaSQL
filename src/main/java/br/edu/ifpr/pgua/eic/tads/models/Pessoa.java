package br.edu.ifpr.pgua.eic.tads.models;

public class Pessoa {
    //atributos
    private String nome;
    private String email;
    private String telefone;

    //controlador
    public Pessoa(String nome, String email, String telefone){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }   

    //getters & setters

    //nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    //cpf
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //telefone
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
