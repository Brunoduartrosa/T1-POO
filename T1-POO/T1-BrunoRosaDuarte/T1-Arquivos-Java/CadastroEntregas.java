import java.util.Collection;
import java.util.ArrayList;

public class CadastroEntregas {

	private ArrayList<Entrega> entregas;

	public CadastroEntregas() {
		entregas = new ArrayList<Entrega>(10);
	}

	public boolean cadastraEntrega(Entrega entrega) {
		int codigo = entrega.getCodigo();
		if(pesquisaEntrega(codigo) == null){
			return entregas.add(entrega);
		}
		return false;
	}

	public Entrega pesquisaEntrega(int codigo) {
		for (Entrega aux: entregas) {
			if(aux.getCodigo() == codigo){
				return aux;
			}
		}
		return null;
	}

	public ArrayList<Entrega> pesquisaEntrega(String email) {
		ArrayList<Entrega> entregaCliente = new ArrayList<Entrega>(10);
		if(!entregas.isEmpty()) {
			for (Entrega aux : entregas) {
				if (aux.getCliente().getEmail().equals(email)) {
					entregaCliente.add(aux);
				}
			}
			return entregaCliente;
		}
		return null;
	}

	public ArrayList<Entrega> getEntregas() {
		return entregas;
	}
}
