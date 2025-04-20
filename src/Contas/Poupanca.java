package Contas;

import java.util.Date;
import Dados.TransacaoInfos;

public class Poupanca extends Conta {
    private double rend_mes;

    public Poupanca(String senha, int nroConta, Date abertura, double rend_mes) {
        super(senha, nroConta, abertura);
        this.rend_mes = rend_mes;
    }

    // Método para aplicar o rendimento mensal
    public void aplicarRendimento() {
        double rendimento = saldo * rend_mes;
        saldo += rendimento;
        ultimaMov = new Date();
        historico.add(new TransacaoInfos(ultimaMov, rendimento, "automatico", nroConta, null, "rendimento"));
    }

    public double getRendMes() {
        return rend_mes;
    }

    public void setRendMes(double rend_mes, boolean gerenteAut) {
        checkGerenteAutenticado(gerenteAut);
        this.rend_mes = rend_mes;
    }

    @Override
    public String toString() {
        return super.toString() + ",\n" +
            "\trend_mes=" + rend_mes + "\n";
    }

}
