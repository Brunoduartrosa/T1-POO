import java.util.ArrayList;

public class Cliente {

	private String email;
	private String nome;
	private String endereco;
	private ArrayList<Entrega> entregas;

	//construtor
	public Cliente(String email, String nome, String endereco) {
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		entregas = new ArrayList<Entrega>(10);
	}


	public boolean adicionaEntrega(Entrega entrega) {
		if(entregas.add(entrega)){
			return true;
		}
		return false;
	}

	public ArrayList<Entrega> pesquisaEntregas() {
		if(!entregas.isEmpty()){
			return entregas;
		}
		return null;
	}

	//getters
	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

}
