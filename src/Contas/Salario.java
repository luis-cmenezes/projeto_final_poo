package Contas;

import java.util.Date;

import Excecoes.LimiteInsuficienteException;

public class Salario extends Conta {
    private double saque_lim; 
    private double transf_lim;

    public Salario(String senha, int nroConta, Date abertura, double saque_lim, double transf_lim) {
        super(senha, nroConta, abertura);
        this.saque_lim = saque_lim;
        this.transf_lim = transf_lim;
    }

    @Override
    protected void verificarSaldoSaque(double valor) {
        super.verificarSaldoSaque(valor);
        if (valor > saque_lim) {
            throw new LimiteInsuficienteException(valor, saque_lim , "Saque");
        }
    }

    @Override
    protected void verificarSaldoPagamento(double valor) {
        super.verificarSaldoPagamento(valor);
        if (valor > transf_lim) {
            throw new LimiteInsuficienteException(valor, transf_lim, "Pagamento");
        }
    }

    public double getSaqueLim() {
        return saque_lim;
    }

    public void setSaqueLim(double saque_lim, boolean gerenteAut) {
        checkGerenteAutenticado(gerenteAut);
        this.saque_lim = saque_lim;
    }

    public double getTransfLim() {
        return transf_lim;
    }

    public void setTransfLim(double transf_lim, boolean gerenteAut) {
        checkGerenteAutenticado(gerenteAut);
        this.transf_lim = transf_lim;
    }

    @Override
    public String toString() {
        return super.toString() + ",\n" +
            "\tsaque_lim" + saque_lim + ",\n" +
            "\ttransf_lim" + transf_lim + "\n";
    }

}
