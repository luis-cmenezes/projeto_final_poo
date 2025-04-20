package Pessoas;

import java.io.Serializable;
import java.util.Date;
import Dados.Endereco;

public abstract class Pessoa implements Serializable {
    private final String CPF, nome;
    private Endereco end;
    private String est_civil;
    private final Date data_nasc;

    public Pessoa(String CPF, String nome, Endereco end, String est_civString, Date data_nasc) {
        if (validarCPF(CPF))
        {
            this.CPF = CPF;
            this.nome = nome;
            this.end = end;
            this.est_civil = est_civString;
            this.data_nasc = data_nasc;
        }else
        {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    // Getters
    public String getCPF() {
        return CPF;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return end;
    }

    public String getEstCivil() {
        return est_civil;
    }

    public Date getDataNasc() {
        return data_nasc;
    }

    // Setters
    public void setEndereco(Endereco end) {
        this.end = end;
    }

    public void setEstCivil(String est_civil) {
        this.est_civil = est_civil;
    }

    // Função de checagem de CPF (Simples)
    public static boolean validarCPF(String CPF) {
        // Verifica se o CPF tem o formato correto (somente números)
        if (CPF == null || CPF.length() != 11 || !CPF.matches("[0-9]+")) {
            return false;
        }

        // Lógica básica de validação de CPF (simplificada)
        int soma = 0;
        int peso = 10;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(CPF.charAt(i)) * peso--;
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 >= 10) digito1 = 0;
        
        soma = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(CPF.charAt(i)) * peso--;
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 >= 10) digito2 = 0;

        return CPF.charAt(9) == (char) (digito1 + '0') && CPF.charAt(10) == (char) (digito2 + '0');
    }

    @Override
    public String toString() {
        return nome + "\n" +
               "\tCPF: " + CPF + "\n" +
               "\tEstado Civil: " + (est_civil != null ? est_civil : "Não informado") + "\n" +
               "\tData de Nascimento: " + (data_nasc != null ? data_nasc : "Não informada") + "\n" +
               "\tEndereço: " + (end != null ? end : "Não informado");
    }

}
