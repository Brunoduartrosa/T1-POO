import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class ACMEDelivery {

	private CadastroEntregas cadastroEntregas;
	private Clientela clientela;
	private Scanner entrada;
	private PrintStream saidaPadrao = System.out;
	int contador = 0;

	public ACMEDelivery() {
		try {
			BufferedReader streamEntrada = new BufferedReader(new FileReader("arquivoentrada.txt"));
			entrada = new Scanner(streamEntrada);   // Usa como entrada um arquivo
			PrintStream streamSaida = new PrintStream(new File("arquivosaida.txt"), Charset.forName("UTF-8"));
			System.setOut(streamSaida);             // Usa como saida um arquivo
		} catch (Exception e) {
			System.out.println(e);
		}
		Locale.setDefault(Locale.ENGLISH);   // Ajusta para ponto decimal
		entrada.useLocale(Locale.ENGLISH);
		cadastroEntregas = new CadastroEntregas();
		clientela = new Clientela();
	}

	public void executa() {
		cadastrarCliente(); //1
		cadastrarEntrega(); //2
		mostraClientes(); //3
		mostraEntregas(); //4
		mostraDadosCliente(); //5
		mostraDadosEntrega(); //6
		mostraDadosEntregaCliente(); //7
		mostrarDadosEntregaMaiorValor(); //8
		mostrarEnderecoEntrega(); //9
		try { //try-catch para tratar excecao quando o arquivoentrada eh invalido
			mostrarSomatorioValorEntrega(); //10
		} catch (NullPointerException e) {
			System.out.println("10;Entrada invalida.");
		}
		restauraES();
		opcao();
	}


	private void cadastrarCliente() {
		String email;
		String nome;
		String endereco;
		Cliente cliente;
		email = entrada.nextLine();
		while (!email.equals("-1")) {
			nome = entrada.nextLine();
			endereco = entrada.nextLine();
			cliente = new Cliente(email, nome, endereco);
			if (clientela.cadastraCliente(cliente)) {
				System.out.println("1;" + cliente.getEmail() + ";" + cliente.getNome() + ";" + cliente.getEndereco());
			}
			email = entrada.nextLine();
		}
	}

	private void cadastrarEntrega() {
		int codigo;
		double valor;
		String descricao;
		String email;
		Cliente cliente;
		Entrega entrega;
		codigo = entrada.nextInt();
		entrada.nextLine();
		while (codigo != -1) {
			valor = entrada.nextDouble();
			entrada.nextLine();
			descricao = entrada.nextLine();
			email = entrada.nextLine();
			cliente = clientela.pesquisaCliente(email);
			entrega = new Entrega(codigo, valor, descricao, cliente);
			if (cliente != null && cliente.getEmail() != null) {
				cadastroEntregas.cadastraEntrega(entrega);
				System.out.println("2;" + entrega.getCodigo() + ";" + entrega.getValor() + ";" + entrega.getDescricao() + ";" + cliente.getEmail());
				contador++;
			}
			codigo = entrada.nextInt();
			entrada.nextLine();
		}
	}


	private void mostraClientes() {
		System.out.println("3;" + clientela.numeroClientes());
	}

	private void mostraEntregas() {
		System.out.println("4;" + contador);
	}

	private void mostraDadosCliente() {
		String email;
		Cliente cliente;
		email = entrada.nextLine();
		cliente = clientela.pesquisaCliente(email);
		if (cliente != null) {
			System.out.println("5;" + cliente.getEmail() + ";" + cliente.getNome() + ";" + cliente.getEndereco());
		} else {
			System.out.println("5;Cliente inexistente");
		}
	}

	private void mostraDadosEntrega() {
		int codigo;
		Entrega entrega;
		Cliente cliente = null;
		codigo = entrada.nextInt();
		entrada.nextLine();
		entrega = cadastroEntregas.pesquisaEntrega(codigo);
		if (entrega != null) {
			cliente = entrega.getCliente();
			System.out.println("6;" + entrega.getCodigo() + ";" + entrega.getValor() + ";" + entrega.getDescricao() + ";" + cliente.getEmail() + ";" + cliente.getNome() + ";" + cliente.getEndereco());
		} else {
			System.out.println("6;Entrega inexistente");
		}
	}

	private void mostraDadosEntregaCliente() {
		String email;
		Cliente cliente;
		ArrayList<Entrega> entregas;
		email = entrada.nextLine();
		cliente = clientela.pesquisaCliente(email);
		if (cliente != null) {
			entregas = cadastroEntregas.pesquisaEntrega(email);
			for (Entrega aux : entregas) {
				System.out.println("7;" + cliente.getEmail() + ";" + aux.getCodigo() + ";" + aux.getValor() + ";" + aux.getDescricao());
			}
		} else {
			System.out.println("7;Cliente inexistente");

		}
	}

	private void mostrarDadosEntregaMaiorValor() {
		Entrega entregaMaiorValor = null;
		ArrayList<Entrega> entregas;
		int numeroEntregas = 0;
		numeroEntregas = contador;
		if (numeroEntregas != 0) {
			entregas = cadastroEntregas.getEntregas();
			if (entregas != null && entregas.size() > 0) {
				for (Entrega aux : entregas) {
					if (entregaMaiorValor == null) {
						entregaMaiorValor = aux;
					}
					if (entregaMaiorValor.getValor() < aux.getValor()) {
						entregaMaiorValor = aux;
					}
				}
			}
			System.out.println("8;" + entregaMaiorValor.getCodigo() + ";" + entregaMaiorValor.getValor() + ";" + entregaMaiorValor.getDescricao());
		} else {
			System.out.println("8;Entrega inexistente");
		}
	}

	private void mostrarEnderecoEntrega() {
		int codigo;
		Entrega entrega;
		Cliente cliente;
		codigo = entrada.nextInt();
		entrada.nextLine();
		entrega = cadastroEntregas.pesquisaEntrega(codigo);
		if (entrega != null) {
			cliente = entrega.getCliente();
			System.out.println("9;" + entrega.getCodigo() + ";" + entrega.getValor() + ";" + entrega.getDescricao() + ";" + cliente.getEndereco());
		} else {
			System.out.println("9;Entrega inexistente");
		}
	}

	private void mostrarSomatorioValorEntrega() {
		String email;
		Cliente cliente;
		double somatorio = 0.0;
		ArrayList<Entrega> entregas;
		email = entrada.nextLine();
		cliente = clientela.pesquisaCliente(email);
		entregas = cadastroEntregas.pesquisaEntrega(email);
		if (cliente == null) {
			System.out.println("10;Cliente inexistente");
		} else {
			if (entregas.size() == 0) {
				System.out.println("10;Entrega inexistente");
			} else {
				if (cliente.getEmail().equals(email)) {
					for (Entrega aux : entregas) {
						somatorio += aux.getValor();
					}
					if (cliente.getEmail().equals(email)) {
						System.out.printf("10;%s;%s;%.2f", cliente.getEmail(), cliente.getNome(), somatorio);
					}
				}
			}
		}
	}

	// Restaura E/S padrao de tela(console)/teclado
	private void restauraES() {
		System.setOut(saidaPadrao);
		entrada = new Scanner(System.in);
	}

	private void menu() {
		System.out.println("==========================================================================");
		System.out.println("Menu de opcoes: ");
		System.out.println("[0] sair do sistema");
		System.out.println("[1] cadastrar um novo cliente e uma entrega correspondente");
		System.out.println("[2] mostrar todos os clientes cadastrados e suas entregas correspondentes");
		System.out.println("==========================================================================");
	}

	private void opcao() {
		ArrayList<Cliente> clientes;
		clientes = clientela.getClientes();
		clientes.clear(); //Esvaziando o ArrayList para entrar com as informacoes digitadas no teclado.
		ArrayList<Entrega> entregas;
		entregas = cadastroEntregas.getEntregas();
		entregas.clear(); //Esvaziando o ArrayList para entrar com as informacoes digitadas no teclado.
		int opcao = 0;
		do {
			System.out.println("Digite a opcao desejada: ");
			menu();
			opcao = entrada.nextInt();
			entrada.nextLine();
			switch (opcao) {
				case 0:
					break;
				case 1:
					cadastrarClienteEEentrega();
					break;
				case 2:
					mostrarClientesComEntrega();
					break;
				default:
					try {
						System.out.println("Opcao invalida! Redigite!");
					} catch (InputMismatchException e) {
						System.out.println("Opcao invalida! Redigite!");
					}
			}
		} while (opcao != 0);
	}

	private void cadastrarClienteEEentrega() {
		Cliente cliente;
		System.out.println("========================================");
		System.out.println("Cadastrar um cliente");
		System.out.print("Digite o email do cliente: ");
		String email = entrada.nextLine();
		System.out.println("Digite o nome do cliente: ");
		String nome = entrada.nextLine();
		System.out.println("Digite o endereco do cliente: ");
		String endereco = entrada.nextLine();
		cliente = new Cliente(email, nome, endereco);
		if (clientela.cadastraCliente(cliente)) {
			System.out.println("Cliente cadastrado com sucesso.");
			System.out.println("========================================");
		} else {
			System.out.println("Erro: cliente não cadastrado.");
		}
		System.out.println("Cadastrar uma entrega");
		System.out.println("Digite o codigo da entrega: ");
		int codigo = entrada.nextInt();
		entrada.nextLine();
		System.out.println("Digite o valor da entrega: ");
		double valor = entrada.nextDouble();
		entrada.nextLine();
		System.out.println("Insira uma descricao a entrega: ");
		String descricao = entrada.nextLine();
		Entrega entrega = new Entrega(codigo, valor, descricao, cliente);
		if (cadastroEntregas.cadastraEntrega(entrega)) {
			System.out.println("Entrega cadastrada com sucesso.");
		} else {
			System.out.println("Erro: entrega não cadastrada.");
		}
		System.out.println("========================================");
	}

	private void mostrarClientesComEntrega() {
		String email;
		String nome;
		String endereco;
		int codigo;
		double valor;
		String descricao;
		Cliente cliente = null;
		ArrayList<Cliente> clientes = clientela.getClientes();
		ArrayList<Entrega> entregas = cadastroEntregas.getEntregas();
		int count = 1;
		for (Cliente aux:clientes) {
			email = aux.getEmail();
			nome = aux.getNome();
			endereco = aux.getEndereco();
			for(Entrega aux1 : entregas){
				codigo = aux1.getCodigo();
				valor = aux1.getValor();
				descricao = aux1.getDescricao();
				if(cadastroEntregas.pesquisaEntrega(email).equals(email)) {
					System.out.println("Cliente " + count + ":" + email + ";" + nome + ";" + endereco + ";" + codigo + ";" + valor + ";" + descricao);
				}
			}
			count++;
		}


	}
}









