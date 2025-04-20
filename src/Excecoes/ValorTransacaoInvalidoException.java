package Excecoes;

public class ValorTransacaoInvalidoException extends ExcecaoDeTransacao {
    public ValorTransacaoInvalidoException(double valor, String tipo) {
        super("Um " + tipo + " não deve ter valor negativo: " + valor);
    }
}

