package negocio;

import dao.CategoriaDAO;
import excepciones.CategoriaException;
import excepciones.UsuarioException;

public class Master extends Categoria{

	public Master() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Master(int idCategoria, String nombre, int score, int minimoPartida, int minimoPuntos, int promedioMinimo) {
		super(idCategoria, nombre, score, minimoPartida, minimoPuntos, promedioMinimo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actualizar(Usuario usuario) throws CategoriaException, UsuarioException {
		// En Master solo puede bajar		
		if (!this.debeSer(usuario)) {
			Experto experto = (Experto) CategoriaDAO.getInstancia().buscarCategoriaByNombreNegocio("EXPERTO");
			usuario.actualizarCategoria(experto);
		}
		
	}

	@Override
	public boolean debeSer(Usuario usuario) {
		if (usuario.getPartidasJugadas() >= this.getMinimoPartida() &&
				usuario.getPuntaje() >= this.getMinimoPuntos() &&
				usuario.getPromedio() >= this.getPromedioMinimo())
			return true;
		else
			return false;
	}

	
}
