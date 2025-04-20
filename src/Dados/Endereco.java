package Dados;

import java.io.Serializable;

public class Endereco implements Serializable{
    private String cidade;
    private String estado;
    private String bairro;
    private String rua;
    private int numero;
    private String complemento;

    public Endereco(String cidade, String estado, String bairro, String rua, int numero, String complemento) {
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Endereco(String cidade, String estado, String bairro, String rua, int numero) {
        this(cidade, estado, bairro, rua, numero, null);
    }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    @Override
    public String toString() {
        return rua + ", " + numero + 
            (complemento != null ? " (" + complemento + ")" : "") + 
            " - " + bairro + ", " + cidade + " - " + estado;
    }

}
