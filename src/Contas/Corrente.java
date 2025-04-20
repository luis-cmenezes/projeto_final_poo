package Contas;

import java.util.Date;
import Excecoes.SaldoInsuficienteException;

public class Corrente extends Conta {
    private double cheque_esp;
    private double tx_adm;

    public Corrente(String senha, int nroConta, Date abertura, double cheque_esp, double tx_adm) {
        super(senha, nroConta, abertura);
        this.cheque_esp = cheque_esp;
        this.tx_adm = tx_adm;
    }

    public double getChequeEsp() {
        return cheque_esp;
    }

    public void setChequeEsp(double cheque_esp, boolean gerenteAut) {
        checkGerenteAutenticado(gerenteAut);
        this.cheque_esp = cheque_esp;
    }

    public double getTxAdm() {
        return tx_adm;
    }

    public void setTxAdm(double tx_adm, boolean gerenteAut) {
        checkGerenteAutenticado(gerenteAut);
        this.tx_adm = tx_adm;
    }

    @Override
    protected void verificarSaldoSaque(double valor) {
        if (valor > saldo + cheque_esp) {
            throw new SaldoInsuficienteException(valor, saldo, cheque_esp);
        }
    }

    @Override
    protected void verificarSaldoPagamento(double valor) {
        if (valor > saldo + cheque_esp) {
            throw new SaldoInsuficienteException(valor, saldo, cheque_esp);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ",\n" +
            "\tcheque_esp" + cheque_esp + ",\n" +
            "\ttx_adm" + tx_adm + "\n";
    }
}
