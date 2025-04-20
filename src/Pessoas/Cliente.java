package Pessoas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Contas.Conta;
import Dados.Endereco;

public class Cliente extends Pessoa {
    private String escolaridade;
    private final int num_agenc_cad;
    private List<Conta> contas = new ArrayList<>();

    public Cliente(String CPF, String nome, Endereco end, String est_civil, Date data_nasc, String escolaridade, int num_agenc_cad) {
        super(CPF, nome, end, est_civil, data_nasc);
        this.escolaridade = escolaridade;
        this.num_agenc_cad = num_agenc_cad;
    }

    public Cliente(String nome, String CPF, int num_agenc_cad) {
        super(CPF, nome, null, null, null);
        this.num_agenc_cad = num_agenc_cad;
    }

    public Cliente(int num_agenc_cad) {
        // CPF dummy
        super("12345678909", "Default", null, null, null);
        this.num_agenc_cad = num_agenc_cad;
    }

    // Getters e Setters
    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    public int getNumAgencCad() {
        return num_agenc_cad;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void addConta(Conta conta) {
        contas.add(conta);
    }
}
