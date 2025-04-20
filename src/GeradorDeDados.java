import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Dados.*;
import Pessoas.*;
import Contas.*;
import Persistencia.*;

public class GeradorDeDados {

    private static Random rand = new Random();
    private static List<Agencia> allAgencias = new ArrayList<>();
    private static List<Cliente> allClientes = new ArrayList<>();

    public static void main(String[] args) {
        gerarAgencias();
        gerarClientesEContasEFun();

        PersistAgencias.salvarAgencias(allAgencias);
        PersistClientes.salvarClientes(allClientes);
    }

    private static void gerarAgencias() {
        for (int i = 0; i < 5; i++) {
            int numero = 1000 + rand.nextInt(9000);
            String nome = "Agência " + (i + 1);
            Endereco endereco = gerarEndereco(i);
            Agencia agencia = new Agencia(numero, nome, endereco);
            allAgencias.add(agencia);
        }
    }

    private static void gerarClientesEContasEFun() {
        for (int i = 0; i < 10; i++) {
            String CPF = gerarCPF();
            String nome = "Cliente " + (i + 1);
            Endereco endereco = gerarEndereco(i);
            String estadoCivil = i % 2 == 0 ? "Solteiro" : "Casado";
            Date dataNascimento = gerarDataNascimento();
            String escolaridade = i % 2 == 0 ? "Superior" : "Médio";
            Agencia agencia = allAgencias.get(rand.nextInt(allAgencias.size()));
            Cliente cliente = new Cliente(CPF, nome, endereco, estadoCivil, dataNascimento, escolaridade, agencia.getNumero());
    
            Conta conta = gerarConta();
            cliente.addConta(conta);
            agencia.adicionarConta(conta);
    
            allClientes.add(cliente);
        }
    
        for (Agencia agencia : allAgencias) {
            Gerente gerente = gerarGerente(agencia.getNumero());
            agencia.setGerente(gerente);
        }

        for (Agencia agencia : allAgencias) {
            for (int i = 0; i < 2; i++) {
                Funcionario func = gerarFuncionario(i);
                agencia.adicionarFuncionario(func);
            }
        }
    }

    private static Gerente gerarGerente(int numeroAgencia) {
        String CPF = gerarCPF();
        String nome = "Gerente " + numeroAgencia;
        Endereco endereco = gerarEndereco(numeroAgencia);
        String estadoCivil = rand.nextBoolean() ? "Solteiro" : "Casado";
        Date dataNascimento = gerarDataNascimento();
        String carteiraTrabalho = "CT-" + rand.nextInt(99999);
        String RG = "RG-" + rand.nextInt(999999);
        String sexo = rand.nextBoolean() ? "Masculino" : "Feminino";
        double salarioBase = 4000 + rand.nextInt(3000);
        int anoIngresso = 2010 + rand.nextInt(10);
        Date dataIngressoGerencia = gerarDataNascimento(); // reutiliza para data aleatória
        boolean cursoBasico = rand.nextBoolean();
    
        return new Gerente(CPF, nome, endereco, estadoCivil, dataNascimento,
                carteiraTrabalho, RG, sexo, salarioBase, anoIngresso,
                dataIngressoGerencia, numeroAgencia, cursoBasico);
    }

    private static Conta gerarConta() {
        String tipo = rand.nextInt(3) == 0 ? "poupanca" : rand.nextInt(2) == 0 ? "salario" : "corrente";
        int nroConta = 100000 + rand.nextInt(900000);
        String senha = gerarSenha();
        Date abertura = new Date();

        switch (tipo) {
            case "poupanca":
                double rendimento = 0.01 + rand.nextDouble() * 0.04;
                return new Poupanca(senha, nroConta, abertura, rendimento);
            case "salario":
                double limiteSaque = 500 + rand.nextDouble() * 2000;
                double limiteTransf = 1000 + rand.nextDouble() * 3000;
                return new Salario(senha, nroConta, abertura, limiteSaque, limiteTransf);
            default:
                double chequeEspecial = 1000 + rand.nextDouble() * 5000;
                double taxa = 10 + rand.nextDouble() * 50;
                return new Corrente(senha, nroConta, abertura, chequeEspecial, taxa);
        }
    }

    private static Funcionario gerarFuncionario(int i) {
        String CPF = gerarCPF();
        String nome = "Funcionario " + (i + 1);
        Endereco endereco = gerarEndereco(i);
        String estadoCivil = i % 2 == 0 ? "Solteiro" : "Casado";
        Date dataNascimento = gerarDataNascimento();
        String carteiraTrabalho = "CT-" + rand.nextInt(99999);
        String RG = "RG-" + rand.nextInt(999999);
        String sexo = i % 2 == 0 ? "Masculino" : "Feminino";
        String cargo = "Atendente";
        double salarioBase = 3000 + rand.nextInt(3000);
        int anoIngresso = 2010 + rand.nextInt(10);

        return new Funcionario(CPF, nome, endereco, estadoCivil, dataNascimento,
                carteiraTrabalho, RG, sexo, cargo, salarioBase, anoIngresso);
    }

    private static Endereco gerarEndereco(int i) {
        return new Endereco("Cidade" + (i % 5 + 1), "Estado" + (i % 5 + 1),
                "Bairro" + (i % 5 + 1), "Rua " + (i % 5 + 1),
                100 + rand.nextInt(900), "Comp " + (i + 1));
    }

    public static String gerarCPF() {
        int[] base = new int[9];
        for (int i = 0; i < 9; i++) base[i] = rand.nextInt(10);

        int d1 = calcularDigito(base, 10);
        int[] baseComD1 = new int[10];
        System.arraycopy(base, 0, baseComD1, 0, 9);
        baseComD1[9] = d1;
        int d2 = calcularDigito(baseComD1, 11);

        StringBuilder sb = new StringBuilder();
        for (int i : base) sb.append(i);
        sb.append(d1).append(d2);
        return sb.toString();
    }

    private static int calcularDigito(int[] nums, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < nums.length; i++) soma += nums[i] * (pesoInicial - i);
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private static String gerarSenha() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 6; i++) senha.append(chars.charAt(rand.nextInt(chars.length())));
        return senha.toString();
    }

    private static Date gerarDataNascimento() {
        int dia = rand.nextInt(28) + 1;
        int mes = rand.nextInt(12) + 1;
        int ano = 1970 + rand.nextInt(30);
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(dia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

