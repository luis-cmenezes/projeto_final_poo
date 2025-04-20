package Excecoes;

public class ExcecaoDeTransacao extends RuntimeException {
    public ExcecaoDeTransacao(String msg) {
        super(msg);
    }
}
