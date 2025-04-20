package Pessoas;

import java.util.Date;
import Dados.Endereco;

public class Gerente extends Funcionario{
    private Date dataIngressoGerencia;
    private int agencia;
    private boolean cursoFormacaoBasico;
    private static double comissaoGerencia = 1000.0;

    public Gerente(String CPF, String nome, Endereco end, String est_civil, Date data_nasc,
                   String carteiraTrabalho, String RG, String sexo,
                   double salarioBase, int anoIngresso,
                   Date dataIngressoGerencia, int agencia, boolean cursoFormacaoBasico) {
        super(CPF, nome, end, est_civil, data_nasc, carteiraTrabalho, RG, sexo, "Gerente", salarioBase, anoIngresso);
        this.dataIngressoGerencia = dataIngressoGerencia;
        this.agencia = agencia;
        this.cursoFormacaoBasico = cursoFormacaoBasico;
    }

    @Override
    public double calcularSalario() {
        return super.calcularSalario() + comissaoGerencia;
    }

    // Getters e setters
    public static void setComissaoGerencia(double comissao) {
        comissaoGerencia = comissao;
    }

    public static double getComissaoGerencia() {
        return comissaoGerencia;
    }

    public Date getDataIngressoGerencia() {
        return dataIngressoGerencia;
    }

    public void setDataIngressoGerencia(Date data) {
        this.dataIngressoGerencia = data;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public boolean hasCursoFormacaoBasico() {
        return cursoFormacaoBasico;
    }

    public void setCursoFormacaoBasico(boolean curso) {
        this.cursoFormacaoBasico = curso;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
            "\tData Ingresso Gerência: " + (dataIngressoGerencia != null ? dataIngressoGerencia : "Não informada") + "\n" +
            "\tAgência: " + agencia + "\n" +
            "\tCurso Formação Básico: " + (cursoFormacaoBasico ? "Sim" : "Não") + "\n" +
            "\tComissão de Gerência: " + comissaoGerencia;
    }

}
