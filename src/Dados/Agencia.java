package Dados;
import java.io.Serializable;
import java.util.*;

import Contas.Conta;
import Pessoas.Funcionario;
import Pessoas.Gerente;

public class Agencia implements Serializable {
    private final int numero;
    private final String nome_fic;
    private final Endereco end;
    private List<Funcionario> funcionarios = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();
    private Gerente gerente;

    public Agencia(int numero, String nome_fic, Endereco end) {
        this.numero = numero;
        this.nome_fic = nome_fic;
        this.end = end;
    }

    // "Sets"
    public void adicionarFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    public void removerFuncionario(int f) {
        funcionarios.remove(f);
    }

    public void setGerente(Gerente g) {
        this.gerente = g;
    }

    public void adicionarConta(Conta c) {
        contas.add(c);
    }

    // Gets
    public int getNumero() {
        return numero;
    }
    
    public String getNome_fic() {
        return nome_fic;
    }
    
    public Endereco getEnd() {
        return end;
    }
    
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    
    public List<Conta> getContas() {
        return contas;
    }
    
    public Gerente getGerente() {
        return gerente;
    }    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agência ").append(numero).append(" - ").append(nome_fic).append("\n");
        sb.append("\tEndereço: ").append(end).append("\n");
        sb.append("\tGerente: ").append(gerente != null ? (gerente.getCPF() + " - " + gerente.getNome()) : "Nenhum").append("\n");
        sb.append("\tFuncionários:\n");
        for (Funcionario f : funcionarios)
            sb.append("  \t\t- ").append(f.getCPF()).append(" - ").append(f.getNome()).append("\n");
        sb.append("\tContas:\n");
        for (Conta c : contas)
            sb.append("  \t\t- ").append(c.getNroConta()).append("\n");
        return sb.toString();
    }

}
