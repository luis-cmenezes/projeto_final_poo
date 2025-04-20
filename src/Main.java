import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import Dados.*;
import Pessoas.*;
import Contas.*;
import Excecoes.*;
import Persistencia.*;

public class Main {
    private static List<Cliente> allClientes = new ArrayList<>();
    private static List<Agencia> allAgencias = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        allAgencias = PersistAgencias.carregarAgencias();
        allClientes = PersistClientes.carregarClientes();
        while (true) {
            limparTela();
            System.out.println("\n1. Listar Agências\n2. Listar Clientes\n0. Sair e Salvar");
            System.out.print("Entre uma opção: ");
            int op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1: listarAgencias(); break;
                case 2: listarClientes(); break;
                case 0: 
                    PersistAgencias.salvarAgencias(allAgencias);
                    PersistClientes.salvarClientes(allClientes);
                    System.exit(0); 
                    break;
                default: System.out.println("Opção inválida.");
            }
            System.out.println("Pressione Enter para prosseguir....");
            sc.nextLine();

        }
        
    }

    private static void listarClientes() {
        limparTela();

            for (int i = 0; i < allClientes.size(); i++) {
                System.out.println(i + ": " + allClientes.get(i).getNome());
            }
            System.out.println(allClientes.size() + ": Adicionar novo cliente");
            System.out.print("Cliente a acessar/Opcao/-1 para sair: ");
            int op = Integer.parseInt(sc.nextLine());
        
            if (op == allClientes.size()) {
                Cliente novo = newCliente();
                allClientes.add(novo);
                System.out.println("Cliente adicionado.");
            } else if (op >= 0 && op < allClientes.size()) {
                Cliente cli = allClientes.get(op);
                navegarComo(cli);
            }
    }

    private static void operarConta(Cliente dono, Conta conta, String senha)
    {
        while (true) {
            limparTela();
            System.out.println("Conta " + conta.getNroConta());
            System.out.println("1. Saque\n2. Consulta\n3. Depósito\n4. Pagamento\n5. Histórico\n0. Voltar");
            System.out.print("Opção: ");
            int op = Integer.parseInt(sc.nextLine());
    
            switch (op) {
                case 1:
                    System.out.print("Valor: ");
                    double valSaque = Double.parseDouble(sc.nextLine());
                    try {
                        conta.sacar(valSaque, "internet", senha);
                        System.out.println("Saque realizado.");
                    } catch (ExcecaoDeTransacao e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    double saldo = conta.consultar("internet", senha);
                    System.out.println("Saldo: " + saldo);
                    break;
                case 3:
                    System.out.print("Valor: ");
                    double valDep = Double.parseDouble(sc.nextLine());
                    conta.depositar(valDep, "internet", senha);
                    System.out.println("Depósito realizado.");
                    break;
                case 4:
                    // Pagamento
                    System.out.print("Valor: ");
                    double valPag = Double.parseDouble(sc.nextLine());
    
                    try {
                        // Listar todas as contas de todos os clientes, exceto as do cliente atual
                        List<Conta> todasContasDestino = new ArrayList<>();
                        for (Cliente outroCliente : allClientes) {
                            if (outroCliente != dono) {
                                todasContasDestino.addAll(outroCliente.getContas());
                            }
                        }
    
                        if (todasContasDestino.isEmpty()) {
                            System.out.println("Não há contas de outros clientes para pagar.");
                            break;
                        }
    
                        // Exibir as contas de outros clientes
                        for (int i = 0; i < todasContasDestino.size(); i++) {
                            System.out.println(i + ": Conta " + todasContasDestino.get(i).getNroConta() +
                                    " (" + (todasContasDestino.get(i) instanceof Corrente ? "Corrente" :
                                            (todasContasDestino.get(i) instanceof Poupanca ? "Poupança" : "Salário")) + ")");
                        }
    
                        System.out.print("Selecione a conta destino: ");
                        int opContaDestino = Integer.parseInt(sc.nextLine());
    
                        if (opContaDestino < 0 || opContaDestino >= todasContasDestino.size()) return;
    
                        Conta contaDestino = todasContasDestino.get(opContaDestino);
    
                        // Realizando o pagamento
                        conta.pagar(valPag, contaDestino.getNroConta(), "internet", senha);
    
                        // Adicionando saldo na conta destino
                        contaDestino.depositar(valPag, "internet", contaDestino.getSenha());
                        System.out.println("Pagamento realizado com sucesso.");
    
                    } catch (ExcecaoDeTransacao e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 5:
                    conta.getHistorico().forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
            System.out.println("Pressione Enter...");
            sc.nextLine();
        }
    }

    private static void navegarComo(Cliente c) {
        limparTela();
        List<Conta> contas = c.getContas();
        if (contas.isEmpty()) {
            System.out.println("Cliente não possui contas.");
            return;
        }

        for (int i = 0; i < contas.size(); i++) {
            Conta conta = contas.get(i);
            String tipoConta = "";

            if (conta instanceof Corrente) {
                tipoConta = "Corrente";
            } else if (conta instanceof Poupanca) {
                tipoConta = "Poupança";
            } else if (conta instanceof Salario) {
                tipoConta = "Salário";
            }

            System.out.println(i + ": Conta " + conta.getNroConta() + " " + tipoConta + " " + conta.getSenha());
        }

        System.out.print("Selecione a conta: ");
        int opConta = Integer.parseInt(sc.nextLine());
        if (opConta < 0 || opConta >= contas.size()) return;

        // Solicita senha antes de continuar
        System.out.print("Digite a senha: ");
        String senha = sc.nextLine();

        Conta conta = contas.get(opConta);

        operarConta(c, conta, senha);
    }

    private static Cliente newCliente() {
        System.out.print("CPF: ");
        String CPF = sc.nextLine();
    
        System.out.print("Nome: ");
        String nome = sc.nextLine();
    
        Endereco end = newEndereco();
    
        System.out.print("Estado civil: ");
        String est_civil = sc.nextLine();
    
        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        Date data_nasc;
        try {
            data_nasc = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
        } catch (ParseException e) {
            throw new RuntimeException("Data inválida");
        }
    
        System.out.print("Escolaridade: ");
        String escolaridade = sc.nextLine();
    
        System.out.println("Selecione uma agência para cadastro:");
        for (int i = 0; i < allAgencias.size(); i++) {
            System.out.println(i + ": " + allAgencias.get(i).getNome_fic());
        }
        System.out.print("Escolha uma agência (0 - " + (allAgencias.size() - 1) + "): ");
        int agenciaIdx = Integer.parseInt(sc.nextLine());

        if (agenciaIdx >= 0 && agenciaIdx < allAgencias.size()) {
            int num_agenc = allAgencias.get(agenciaIdx).getNumero();
            return new Cliente(CPF, nome, end, est_civil, data_nasc, escolaridade, num_agenc);
        } else {
            System.out.println("Opção inválida.");
            return null;
        }
    }

    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void criarAgencia() {
        limparTela();
        System.out.print("Numero da agência: ");
        int numero = Integer.parseInt(sc.nextLine());
        System.out.print("Nome da agência: ");
        String nome = sc.nextLine();
        Endereco end = newEndereco();

        allAgencias.add(new Agencia(numero, nome, end));

        System.out.println("Agência criada.");
    }

    private static Endereco newEndereco()
    {
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Estado: ");
        String estado = sc.nextLine();
        System.out.print("Bairro: ");
        String bairro = sc.nextLine();
        System.out.print("Rua: ");
        String rua = sc.nextLine();
        System.out.print("Numero: ");
        int num = Integer.parseInt(sc.nextLine());
        System.out.print("Tem complemento [s/n]: ");
        String temComplemento = sc.nextLine();

        if (temComplemento.equalsIgnoreCase("s"))
        {
            System.out.print("Complemento: ");
            String complemento = sc.nextLine();
            return new Endereco(cidade, estado, bairro, rua, num, complemento);
        }else{
            return new Endereco(cidade, estado, bairro, rua, num);
        }
    }

    private static void listarAgencias() {
        limparTela();

        for (int i = 0; i < allAgencias.size(); i++)
        {  
            System.out.print(i + ": " + allAgencias.get(i) + "\n#####################\n");
        }
        System.out.print(allAgencias.size() + ": Adicionar agencia\n");

        System.out.print("Agencia a editar/Opcao/-1 para Sair: ");
        int op = Integer.parseInt(sc.nextLine());

        if (op >= 0 && op <= allAgencias.size())
        {
            if (op == allAgencias.size())
            {
                criarAgencia();
            }else{
                limparTela();
                System.out.print(allAgencias.get(op));

                System.out.println("\n1. Excluir agencia\n2. Editar Gerente\n3. Editar Funcionarios\n4. Criar Contas\n0. Sair");
                System.out.print("Entre uma opção: ");
                int op2 = Integer.parseInt(sc.nextLine());

                switch (op2) {
                    case 1:
                        // Excluir a agência selecionada
                        allAgencias.remove(op);
                        System.out.println("Agência excluída.");
                        break;
                    case 2:
                        // Editar Gerente
                        editarGerente(allAgencias.get(op));
                        break;
                    case 3:
                        // Editar Funcionários
                        editarFuncionarios(allAgencias.get(op));
                        break;
                    case 4:
                        // Editar Contas
                        criarConta(allAgencias.get(op));
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
            
        }
    }

    private static void editarGerente(Agencia ag)
    {
        limparTela();
        Gerente gerente = ag.getGerente();

        if (gerente == null) {
            System.out.println("Agência sem gerente.");
            System.out.print("Deseja promover um funcionário a gerente? (s/n): ");
            if (sc.nextLine().equalsIgnoreCase("s")) {
                List<Funcionario> funcionarios = ag.getFuncionarios();
                for (int i = 0; i < funcionarios.size(); i++) {
                    System.out.println(i + ": " + funcionarios.get(i).getNome());
                }
                System.out.print("Escolha o funcionário: ");
                int idx = Integer.parseInt(sc.nextLine());
                if (idx >= 0 && idx < funcionarios.size()) {
                    Funcionario f = funcionarios.remove(idx);
                    Gerente g = new Gerente(f.getCPF(), f.getNome(), f.getEndereco(), f.getEstCivil(),
                            f.getDataNasc(), f.getCarteiraTrabalho(), f.getRG(), f.getSexo(),
                            f.getSalarioBase(), f.getAnoIngresso(), new Date(), ag.getNumero(), false);
                    ag.setGerente(g);
                    System.out.println("Funcionário promovido a gerente.");
                }
            }
        } else {
            while (true) {
                limparTela();
                System.out.println("Gerente atual:\n" + gerente);
                System.out.println("\n1. Editar como funcionário");
                System.out.println("2. Editar curso formação básico");
                System.out.println("0. Voltar");
                System.out.print("Opção: ");
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1:
                        editarFuncionario(gerente);
                        break;
                    case 2:
                        System.out.print("Possui curso formação básico? (s/n): ");
                        gerente.setCursoFormacaoBasico(sc.nextLine().equalsIgnoreCase("s"));
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }

            }
        }
    }

    private static void editarFuncionarios(Agencia ag) {
        limparTela();
        System.out.println("Agência: " + ag.getNome_fic());
        List<Funcionario> funcionarios = ag.getFuncionarios();

        for (int i = 0; i < funcionarios.size(); i++) {
            System.out.println(i + ": " + funcionarios.get(i));
        }

        System.out.println(funcionarios.size() + ": Adicionar novo funcionário");
        System.out.print("Escolha uma opção/Funcionário: ");
        int op = Integer.parseInt(sc.nextLine());

        if (op == funcionarios.size()) {
            Funcionario novo = newFuncionario();
            ag.adicionarFuncionario(novo);
        } else if (op >= 0 && op < funcionarios.size()) {
            editarFuncionario(ag.getFuncionarios().get(op));
            
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void editarFuncionario(Funcionario f)
    {
        limparTela();

        while (true) {
            limparTela();
            System.out.println("Editando funcionário:\n" + f);
            System.out.println("\n1. Editar estado civil");
            System.out.println("2. Editar sexo");
            System.out.println("3. Editar cargo");
            System.out.println("4. Editar salário base");
            System.out.println("5. Editar endereço");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");
            int op = Integer.parseInt(sc.nextLine());
    
            switch (op) {
                case 1:
                    System.out.print("Novo estado civil: ");
                    f.setEstCivil(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Novo sexo: ");
                    f.setSexo(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Novo cargo: ");
                    f.setCargo(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Novo salário base: ");
                    f.setSalarioBase(Double.parseDouble(sc.nextLine()));
                    break;
                case 5:
                    f.setEndereco(newEndereco());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
        
    private static Funcionario newFuncionario()
    {
        System.out.print("CPF: ");
        String CPF = sc.nextLine();
        
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        
        Endereco end = newEndereco();
        
        System.out.print("Estado civil: ");
        String est_civil = sc.nextLine();
        
        System.out.print("Data de nascimento (dd/MM/yyyy): ");
        java.util.Date data_nasc;
        try {
            data_nasc = new SimpleDateFormat("dd/MM/yyyy").parse(sc.nextLine());
        } catch (ParseException e) {
            throw new RuntimeException("Data inválida");
        }
        
        System.out.print("Carteira de Trabalho: ");
        String carteiraTrabalho = sc.nextLine();
        
        System.out.print("RG: ");
        String RG = sc.nextLine();
        
        System.out.print("Sexo: ");
        String sexo = sc.nextLine();
        
        System.out.print("Cargo: ");
        String cargo = sc.nextLine();
        
        System.out.print("Salário base: ");
        double salarioBase = Double.parseDouble(sc.nextLine());
        
        System.out.print("Ano de ingresso: ");
        int anoIngresso = Integer.parseInt(sc.nextLine());
        
        return new Funcionario(CPF, nome, end, est_civil, data_nasc,
                            carteiraTrabalho, RG, sexo, cargo, salarioBase, anoIngresso);
    }

    private static void criarConta(Agencia ag)
    {
        limparTela();
        System.out.print("Tipo de conta (poupanca/salario/corrente): ");
        String tipo = sc.nextLine().toLowerCase();

        if (allClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (int i = 0; i < allClientes.size(); i++) {
            System.out.println(i + ": " + allClientes.get(i).getNome());
        }
        System.out.print("Selecione o cliente dono da conta: ");
        Cliente titular = allClientes.get(Integer.parseInt(sc.nextLine()));

        System.out.print("Conta conjunta? (s/n): ");
        boolean conjunta = sc.nextLine().equalsIgnoreCase("s");
        Cliente segundoTitular = null;
        if (conjunta) {
            for (int i = 0; i < allClientes.size(); i++) {
                if (!allClientes.get(i).equals(titular))
                    System.out.println(i + ": " + allClientes.get(i).getNome());
            }
            System.out.print("Selecione o segundo titular: ");
            segundoTitular = allClientes.get(Integer.parseInt(sc.nextLine()));
        }

        System.out.print("Número da conta: ");
        int nroConta = Integer.parseInt(sc.nextLine());
        System.out.print("Senha (6 caracteres): ");
        String senha = sc.nextLine();
        Date abertura = new Date();

        Conta nova;
        switch (tipo) {
            case "poupanca":
                System.out.print("Rendimento mensal (ex: 0.01): ");
                double rend = Double.parseDouble(sc.nextLine());
                nova = new Poupanca(senha, nroConta, abertura, rend);
                break;
            case "salario":
                System.out.print("Limite de saque: ");
                double saqueLim = Double.parseDouble(sc.nextLine());
                System.out.print("Limite de transferência: ");
                double transfLim = Double.parseDouble(sc.nextLine());
                nova = new Salario(senha, nroConta, abertura, saqueLim, transfLim);
                break;
            case "corrente":
                System.out.print("Cheque especial: ");
                double chequeEsp = Double.parseDouble(sc.nextLine());
                System.out.print("Taxa administrativa: ");
                double txAdm = Double.parseDouble(sc.nextLine());
                nova = new Corrente(senha, nroConta, abertura, chequeEsp, txAdm);
                break;
            default:
                System.out.println("Tipo inválido.");
                return;
        }

        titular.addConta(nova);
        if (segundoTitular != null)
            segundoTitular.addConta(nova);
        ag.adicionarConta(nova);
    }

}
