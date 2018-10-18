package controlador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dao.BazaDAO;
import dao.CartaDAO;
import dao.JuegoDAO;
import dao.JugadorDAO;
import dao.UsuarioDAO;
import dto.BazaDTO;
import dto.CartaDTO;
import dto.JuegoDTO;
import dto.JugadorDTO;
import dto.UsuarioDTO;
import excepciones.CartaException;
import excepciones.CategoriaException;
import excepciones.JuegoException;
import excepciones.JugadorException;
import excepciones.MiembroException;
import excepciones.ParejaException;
import excepciones.UsuarioException;
import negocio.Carta;
import negocio.FactoryJuegos;
import negocio.GrupoJuego;
import negocio.Juego;
import negocio.Jugador;
import negocio.JugadorIndividual;
import negocio.Pareja;
import negocio.Usuario;

public class ControladorJuego {

	private List<Juego> juegos;
	private static FactoryJuegos fcJuegos;
	private static ControladorJuego controlador;

	public static ControladorJuego getInstancia() {
		if (controlador == null)
			controlador = new ControladorJuego();
		return controlador;
	}

	public ControladorJuego() {
		juegos = new LinkedList<Juego>();
		try {
			juegos = JuegoDAO.getInstancia().getJuegosActivos();
		} catch (CategoriaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fcJuegos = new FactoryJuegos();
	}

	public void iniciarJuego(GrupoJuego grupo)
			throws JuegoException, UsuarioException, CategoriaException, ParejaException, MiembroException {
		Juego j = fcJuegos.getJuego(grupo.getParejas(), grupo.getTipoJuego());
		if (j != null) {
			// Creo que se debería crear el chico en el constructor de Juego y no aca (VER)
			j.save();
			// para tener el id el crear va despues del save
			j.crearChico();
			juegos.add(j);
			// imprimirDbg();
		}
	}

	public void cantarTruco(int idJuego) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
		j.cantarTruco();
	}

	public void cantarReTruco(int idJuego) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
		j.cantarReTruco();
	}

	public void cantarVale4(int idJuego, int idJugador) throws JuegoException, CategoriaException {
		// TODO Auto-generated method stub
		Juego j = this.buscarJuego(idJuego);
		j.cantarVale4(idJugador);
	}

	public void cantarQuieroTruco(int idJuego, boolean quieroSiNo) throws JuegoException, CategoriaException {
		// TODO Auto-generated method stub
		Juego j = this.buscarJuego(idJuego);
		j.cantarQuieroTruco(quieroSiNo);
	}

	public void cantarQuieroEnvido(int idJuego, boolean quieroSiNo) throws JuegoException, CategoriaException {
		// TODO Auto-generated method stub
		Juego j = this.buscarJuego(idJuego);
		j.cantarQuieroEnvido(quieroSiNo);
	}

	public void cantarEnvido(int idJuego) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
		j.cantarEnvido();
	}

	// TODO AGREGAR
	public void aceptarTruco(int idJuego, int idJugador) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
	}

	public Juego buscarJuego(int idJuego) throws JuegoException, CategoriaException {
		return JuegoDAO.getInstancia().buscarJuego(idJuego);
	}

	public void jugarCarta(JuegoDTO juego, CartaDTO carta, JugadorDTO jugador)
			throws JugadorException, CartaException, JuegoException, CategoriaException, UsuarioException {
		Juego jue = this.buscarJuego(juego.getIdJuego());
		Carta car = CartaDAO.getInstancia().buscarCartaPorID(carta.getIdCarta());
		Jugador jug = JugadorDAO.getInstancia().buscarJugadorByIdClase(jugador.getIdJugador());
		jue.jugarCarta(car, jug);
	}

	public boolean verificarFinJuego(int idJuego)
			throws JuegoException, CategoriaException, UsuarioException, ParejaException {
		Juego j = this.buscarJuego(idJuego);
		return j.verificarFinJuego();
	}

	public boolean terminoMano(int idJuego) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
		return j.terminoMano();
	}

	public void sinCantar(int idJuego) throws JuegoException, CategoriaException {
		// TODO Auto-generated method stub
		Juego j = this.buscarJuego(idJuego);
		j.sinCantar();
	}

	public boolean sePuedeTruco(int idJuego) throws JuegoException {
		return true;
	}

	public boolean sePuedeCantarEnvido(int idJuego) throws JuegoException, CategoriaException {
		Juego j = this.buscarJuego(idJuego);
		return j.sePuedeCantarEnvido();
	}

	public void imprimirDbg() throws JuegoException, CategoriaException {
		for (Juego juego : juegos) {
			List<Pareja> par = juego.getParejas();
			for (Pareja p : par) {
				// CONFLICTOS CON NOMBRE JUGADOR
				System.out.println("Pareja num " + p.getIdPareja());
				for (Jugador j : p.getJugadores()) {
					System.out.println(((JugadorIndividual) j).getUsuario().getApodo());
					j.dbgCartas();
				}
				if (this.sePuedeCantarEnvido(juego.getId())) {
					System.out.println("Tanto para envido " + p.getMayorTantoEnvido());
					System.out.println("Tanto para truco " + p.getMayorTantoTruco());
				}
				juego.puntosDbg(p.getIdPareja());
				System.out.println("");
			}
			// System.out.println("juega " + juego.proximoDbg().getNombre());
			System.out.println("");
		}
	}

	public List<JuegoDTO> getJuegosActivos(UsuarioDTO usuario) throws CategoriaException {
		List<Juego> juegos = JuegoDAO.getInstancia().buscarJuegosActivos(usuario);
		List<JuegoDTO> juegosDto = new ArrayList<>();
		for (Juego j : juegos) {
			juegosDto.add(j.toDTO());
		}
		return juegosDto;
	}

	public boolean turnoJugador(JuegoDTO juego, UsuarioDTO usuario)
			throws CategoriaException, UsuarioException, JuegoException {
		Juego ju = JuegoDAO.getInstancia().buscarJuego(juego.getIdJuego());
		Usuario us = UsuarioDAO.getInstancia().buscarUsuarioById(usuario.getIdUsuario());
		Jugador jug = JugadorDAO.getInstancia().buscarJugadorByUsario(ju.getId(), us.getIdUsuario());
		for (Juego j : juegos) {
			// falta un esJuego
			if (j.sosJuego(ju) && j.esTurno(jug)) {
				return true;
			}
		}
		return false;
	}

	public List<CartaDTO> getCartas(JuegoDTO juego, UsuarioDTO usuario)
			throws CategoriaException, UsuarioException, JuegoException {
		Juego ju = JuegoDAO.getInstancia().buscarJuego(juego.getIdJuego());
		Usuario us = UsuarioDAO.getInstancia().buscarUsuarioById(usuario.getIdUsuario());
		Jugador jug = JugadorDAO.getInstancia().buscarJugadorByUsario(ju.getId(), us.getIdUsuario());
		List<Carta> cartas = new ArrayList<>();
		List<CartaDTO> cartasDto = new ArrayList<>();
		for (Juego j : juegos) {
			// falta un esJuego
			if (j.sosJuego(ju)) {
				cartas = j.getCartas(jug);
				break;
			}
		}
		for (Carta carta : cartas) {
			cartasDto.add(carta.toDTO());
		}
		return cartasDto;
	}
}
