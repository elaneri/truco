package negocio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dao.JugadorDAO;
import dao.MiembroDAO;
import dao.ParejaDAO;
import dao.UsuarioDAO;
import dto.ParejaDTO;
import excepciones.CategoriaException;
import excepciones.MiembroException;
import excepciones.ParejaException;
import excepciones.UsuarioException;

public class Pareja {
	private int idPareja = 0;
	private List<Jugador> jugadores;
	private Categoria categoriaMayor;

	public Pareja(Jugador j1, Jugador j2) throws CategoriaException, UsuarioException {
		jugadores = new LinkedList<Jugador>();
		jugadores.add(j1);
		jugadores.add(j2);

	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}
	
	public Categoria obtenerMayorCategoriaCerrada() throws CategoriaException, MiembroException, UsuarioException {
		Usuario usuario1 = UsuarioDAO.getInstancia()
				.toNegocio(JugadorDAO.getInstancia().buscarJugadorById(getJugador1().getId()).getMiembro().getUsuario());
		Usuario usuario2 = UsuarioDAO.getInstancia()
				.toNegocio(JugadorDAO.getInstancia().buscarJugadorById(getJugador2().getId()).getMiembro().getUsuario());
		Categoria mayor = usuario1.getCategoria();
		if (usuario2.getCategoria().getScore() > mayor.getScore()) {
			mayor = usuario2.getCategoria();
		}
		return mayor;
	}

	public Categoria obtenerMayorCategoria() throws CategoriaException, UsuarioException {
		Usuario usuario1 = UsuarioDAO.getInstancia()
				.toNegocio(JugadorDAO.getInstancia().buscarJugadorById(getJugador1().getId()).getUsuario());
		Usuario usuario2 = UsuarioDAO.getInstancia()
				.toNegocio(JugadorDAO.getInstancia().buscarJugadorById(getJugador2().getId()).getUsuario());
		Categoria mayor = usuario1.getCategoria();
		if (usuario2.getCategoria().getScore() > mayor.getScore()) {
			mayor = usuario2.getCategoria();
		}
		return mayor;
	}

	public int getIdPareja() {
		return idPareja;
	}

	public void setIdPareja(int idPareja) {
		this.idPareja = idPareja;
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public boolean tieneLugar() {
		return (jugadores != null) && (jugadores.size() < 2);
	}

	// TODO addJugadores YA NO ESTA
	public void addJugador(Jugador jugador) {
		if (this.jugadores.size() < 2)
			this.jugadores.add(jugador);
	}

	// TODO tiene jugador
	public boolean tieneJugador(int idJugador) {
		for (Jugador jug : jugadores) {
			if (jug.esJugador(idJugador))
				return true;
		}
		return false;
	}

	// TODO Agregar a Diagrama.
	public boolean esPareja(int idPareja) {
		return getIdPareja() == idPareja;
	}

	public int getMayorTantoTruco() {
		int mayor = 0;
		for (Jugador jug : jugadores) {
			if (mayor < jug.getTantoTruco())
				mayor = jug.getTantoTruco();
		}
		return mayor;
	}

	public int getMayorTantoEnvido() {
		int mayor = 0;
		for (Jugador jug : jugadores) {
			if (mayor < jug.getTantoEnvido())
				mayor = jug.getTantoEnvido();
		}
		return mayor;
	}

	public Jugador getJugador1() {
		return jugadores.get(0);
	}

	public Jugador getJugador2() {
		return jugadores.get(1);
	}

	public Categoria getCategoriaMayor() {
		return categoriaMayor;
	}

	public void setCategoriaMayor(Categoria categoriaMayor) {
		this.categoriaMayor = categoriaMayor;
	}

	public Pareja saveIndividual() throws CategoriaException, UsuarioException, ParejaException {

		return ParejaDAO.getInstancia().guardarParejaIndividual(this);
	}

	public Pareja saveGrupal() throws CategoriaException, MiembroException, UsuarioException, ParejaException {

		return ParejaDAO.getInstancia().guardarParejaGrupal(this);
		
	}

	public ParejaDTO toDTO() {
		// TODO Auto-generated method stub
		List jugdto = new ArrayList<>();
		for (Jugador j : jugadores) {
			jugdto.add(j.toDTO());
		}
		return new ParejaDTO(this.getIdPareja(), jugdto);
	}
}
