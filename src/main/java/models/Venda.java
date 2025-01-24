package models;

import java.util.Date;
import java.util.List;

public class Venda {

    //atributos
    private int id;
    private Date dataVenda;
    private String formaPagamento;
    private double valorTotal;
    private int idCliente;
    private List<ItemVenda> itensVenda;

    //construtor
    public Venda(int id, Date dataVenda, String formaPagamento, double valorTotal, int idCliente, List<ItemVenda> itensVenda) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
        this.itensVenda = itensVenda;
    }

    //#region Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }
    //#endregion
}