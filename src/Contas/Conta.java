package Contas;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Dados.TransacaoInfos;
import Excecoes.SenhaIncorretaException;
import Excecoes.SaldoInsuficienteException;
import Excecoes.GerenteNaoAutenticadoException;
import Excecoes.ValorTransacaoInvalidoException;

public abstract class Conta implements Serializable{
    protected boolean ativa;
    protected final String senha;
    protected final int nroConta;
    protected double saldo;
    protected Date abertura;
    protected Date ultimaMov;
    protected List<TransacaoInfos> historico = new ArrayList<>();

    public Conta(String senha, int nroConta, Date abertura) {
        this.ativa = true;
        this.senha = senha;
        this.nroConta = nroConta;
        this.saldo = 0;
        this.abertura = abertura;
    }

    private void autenticar(String senhaDigitada) {
        if (!senha.equals(senhaDigitada)) throw new SenhaIncorretaException();
    }

    protected void verificarSaldoPagamento(double valor) {
        if (valor > saldo) {
            throw new SaldoInsuficienteException(valor, saldo);
        }
    }

    protected void verificarSaldoSaque(double valor) {
        if (valor > saldo) {
            throw new SaldoInsuficienteException(valor, saldo);
        }
    }

    private void verificarValorTransacao(double valor, String tipo)
    {
        if (valor <= 0)
        {
            throw new ValorTransacaoInvalidoException(valor, tipo);
        }
    }

    public boolean sacar(double valor, String canal, String senha) {
        verificarValorTransacao(valor, "saque");
        autenticar(senha);
        verificarSaldoSaque(valor);
        saldo -= valor;
        ultimaMov = new Date();
        historico.add(new TransacaoInfos(ultimaMov, valor, canal, nroConta, null, "saque"));
        return true;
    }

    public boolean depositar(double valor, String canal, String senha) {
        verificarValorTransacao(valor, "depósito");
        autenticar(senha);
        saldo += valor;
        ultimaMov = new Date();
        historico.add(new TransacaoInfos(ultimaMov, valor, canal, nroConta, null, "deposito"));

        return true;
    }

    public double consultar(String canal, String senha) {
        autenticar(senha);
        ultimaMov = new Date();
        historico.add(new TransacaoInfos(ultimaMov, null, canal, nroConta, null, "consulta"));
        return saldo;
    }

    public boolean pagar(double valor, int contaDest, String canal, String senha) {
        verificarValorTransacao(valor, "pagamento");
        autenticar(senha);
        verificarSaldoPagamento(valor);
        saldo -= valor;
        ultimaMov = new Date();
        historico.add(new TransacaoInfos(ultimaMov, valor, canal, nroConta, contaDest, "pagamento"));
        return true;
    }

    public boolean isAtiva() { return ativa; }
    public int getNroConta() { return nroConta; }
    public String getSenha() { return senha; }
    public Date getAbertura() { return abertura; }
    public Date getUltimaMov() { return ultimaMov; }
    public List<TransacaoInfos> getHistorico() { return historico; }

    public void changeAtiva(String senha) {
        autenticar(senha);
        this.ativa = !ativa;
    }

    protected void checkGerenteAutenticado(boolean gerenteAutenticado)
    {
        if(!gerenteAutenticado)
        {
            throw new GerenteNaoAutenticadoException();
        }
    }

    @Override
    public String toString() {
        return "Conta\n" +
            "\tnroConta" + nroConta + ",\n" +
            "\tativa" + ativa + ",\n" +
            "\tabertura=" + abertura + ",\n" +
            "\tultimaMov=" + ultimaMov + "\n";
    }

}
