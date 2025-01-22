package models;

public class ItemVenda {
    private int id;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;
    private int idVenda;
    private Produto produto;

    // Construtores, getters e setters
    public ItemVenda(int id, int quantidade, double precoUnitario, int idVenda, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.idVenda = idVenda;
        this.produto = produto;
        this.subtotal = quantidade * precoUnitario; // Calcular subtotal na hora da criação
    }

    // Método para calcular o subtotal
    public double calcularSubtotal() {
        return quantidade * precoUnitario;
    }

    //#region Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subtotal = calcularSubtotal();  // Atualiza o subtotal quando a quantidade mudar
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
        this.subtotal = calcularSubtotal();  // Atualiza o subtotal quando o preço mudar
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    //#endregion
}
