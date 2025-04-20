package Excecoes;

public class SaldoInsuficienteException extends ExcecaoDeTransacao {
    public SaldoInsuficienteException(double valorSolicitado, double saldoAtual) {
        super(String.format("Saldo insuficiente. Você tentou sacar/pagar %.2f, mas seu saldo atual é %.2f. Faltam %.2f.", 
                            valorSolicitado, saldoAtual, valorSolicitado - saldoAtual));
    }

    public SaldoInsuficienteException(double valorSolicitado, double saldoAtual, double saldoChequeEspecial) {
        super(String.format("Saldo insuficiente. Você tentou sacar/pagar %.2f, mas seu saldo atual é %.2f e seu cheque especial é %.2f. Faltam %.2f.", 
                            valorSolicitado, saldoAtual, saldoChequeEspecial, valorSolicitado - saldoAtual - saldoChequeEspecial));
    }
}
