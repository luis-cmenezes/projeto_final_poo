package Dados;
import java.io.Serializable;
import java.util.Date;

public class TransacaoInfos implements Serializable {
    private final Date dataRealiz;
    private final Double valor;
    private final String canal;
    private final int contaRealiz;
    private final Integer contaDest;
    private final String tipo;

    public TransacaoInfos(Date dataRealiz, Double valor, String canal, int contaRealiz, Integer contaDest, String tipo) {
        this.dataRealiz = dataRealiz;
        this.valor = valor;
        this.canal = canal;
        this.contaRealiz = contaRealiz;
        this.contaDest = contaDest;
        this.tipo = tipo;
    }

    public Date getDataRealiz() { return dataRealiz; }
    public Double getValor() { return valor; }
    public String getCanal() { return canal; }
    public int getContaRealiz() { return contaRealiz; }
    public Integer getContaDest() { return contaDest; }
    public String getTipo() { return tipo; }

    public String toString() {
        switch (tipo) {
            case "consulta":
                return String.format("Consulta realizada em %s via %s pela conta %d", dataRealiz, canal, contaRealiz);
            case "saque":
                return String.format("Saque de %.2f em %s via %s pela conta %d", valor, dataRealiz, canal, contaRealiz);
            case "deposito":
                return String.format("Depósito de %.2f em %s via %s na conta %d", valor, dataRealiz, canal, contaRealiz);
            case "pagamento":
                return String.format("Pagamento de %.2f da conta %d para conta %d em %s via %s",
                        valor, contaRealiz, contaDest, dataRealiz, canal);
            default:
                return "Transação desconhecida";
        }
    }
}
