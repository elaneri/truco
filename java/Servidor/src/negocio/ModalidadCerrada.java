package negocio;

import java.util.List;

import dao.JuegoDAO;
import excepciones.CategoriaException;
import excepciones.MiembroException;
import excepciones.ParejaException;
import excepciones.UsuarioException;

public class ModalidadCerrada  extends Juego{

	public ModalidadCerrada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ModalidadCerrada(List<Pareja> parejas) {
		super(parejas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int calcularPuntos() {
		return 5;
	}

	public void save() throws ParejaException {
		this.setId(JuegoDAO.getInstancia().guardarJuegoCerrado(this));
	}

	@Override
	public void finalizarJuego() throws UsuarioException, CategoriaException, ParejaException, MiembroException {
		// TODO Auto-generated method stub
		// FALTA
		
	}

	@Override
	public Categoria obtenerCategoriaMayor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calcularPuntos(Usuario usuario) {
		// TODO Auto-generated method stub
		return 0;
	}
}
