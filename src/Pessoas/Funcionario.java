package Pessoas;

import java.util.Calendar;
import java.util.Date;
import Dados.Endereco;

public class Funcionario extends Pessoa {
    private final String carteiraTrabalho, RG;
    private String sexo, cargo;
    private double salarioBase;
    private final int anoIngresso;

    public Funcionario(String CPF, String nome, Endereco end, String est_civil, Date data_nasc,
                       String carteiraTrabalho, String RG, String sexo, String cargo,
                       double salarioBase, int anoIngresso) {
        super(CPF, nome, end, est_civil, data_nasc);
        this.carteiraTrabalho = carteiraTrabalho;
        this.RG = RG;
        this.sexo = sexo;
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.anoIngresso = anoIngresso;
    }

    public double calcularSalario() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        return (anoAtual - anoIngresso) > 15 ? salarioBase * 1.1 : salarioBase;
    }

    // Getters
    public String getCarteiraTrabalho() { return carteiraTrabalho; }
    public String getRG() { return RG; }
    public String getSexo() { return sexo; }
    public String getCargo() { return cargo; }
    public double getSalarioBase() { return salarioBase; }
    public int getAnoIngresso() { return anoIngresso; }

    // Setters
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "\tRG: " + RG + "\n" +
               "\tCarteira de Trabalho: " + carteiraTrabalho + "\n" +
               "\tSexo: " + (sexo != null ? sexo : "Não informado") + "\n" +
               "\tCargo: " + (cargo != null ? cargo : "Não informado") + "\n" +
               "\tSalário Base: " + salarioBase + "\n" +
               "\tAno de Ingresso: " + anoIngresso + "\n" +
               "\tSalário Atual: " + calcularSalario();
    }

}
