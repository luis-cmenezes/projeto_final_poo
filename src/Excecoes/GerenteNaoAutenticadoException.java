package Excecoes;

public class GerenteNaoAutenticadoException extends RuntimeException {
    public GerenteNaoAutenticadoException() {
        super("Esta operação somente pode ser realizada com um gerente autenticado.");
    }
}
