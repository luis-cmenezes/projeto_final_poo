package Excecoes;

public class LimiteInsuficienteException extends ExcecaoDeTransacao {
    public LimiteInsuficienteException(double valor, double limite, String tipo) {
        super(tipo + " de R$ " + valor + " excede o limite de R$ " + limite);
    }
}

