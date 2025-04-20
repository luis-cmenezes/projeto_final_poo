package Excecoes;

public class SenhaIncorretaException extends ExcecaoDeTransacao {
    public SenhaIncorretaException() {
        super("Senha incorreta.");
    }
}
