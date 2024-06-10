import java.util.ArrayList;

public class Clientela {

	private ArrayList<Cliente> clientes;

	public Clientela() {
		clientes = new ArrayList<Cliente>(10);
	}

	public boolean cadastraCliente(Cliente cliente) {
		String email = cliente.getEmail();
		if(pesquisaCliente(email) == null){
			return clientes.add(cliente);
		}
		return false;
	}

	public Cliente pesquisaCliente(String email) {
		for(Cliente aux: clientes){
			if(aux.getEmail().equals(email)){
				return aux;
			}
		}
		return null;
	}
	public int numeroClientes(){
		return clientes.size();
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
}
