package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import dao.BazaDAO;
import dao.CartaDAO;
import dao.JugadaDAO;
import dao.JugadorCartaDAO;
import dao.JugadorDAO;
import dao.ManoDAO;
import dao.ParejaDAO;
import dto.BazaDTO;
import dto.JugadorDTO;
import dto.ManoDTO;
import dto.ParejaDTO;
import excepciones.CartaException;
import excepciones.CategoriaException;
import excepciones.JugadorException;
import excepciones.UsuarioException;

public class Mano {

	private int idMano;
	private List<Pareja> parejas;
	private List<Baza> bazas;
	private List<Jugador> jugadores;

	private Envidoo envido;
	private Trucoo truco;

	private int puntoParaTerminarChico;
	private Mazo mazo;

	private boolean seCantoEnvido;
	private boolean seCantoTruco;

	// esto mantiene el turno de los jugadores se puede iniciar donde sea pero se
	// incrementa con cada jugada

	public Mano(int idMano) {
		this.idMano = idMano;
		this.truco = new Trucoo();
	}

	public Mano(List<Pareja> parejas, List<Jugador> jugadores, int puntoParaTerminarChico)
			throws UsuarioException, CategoriaException {
		super();
		this.parejas = parejas;
		this.jugadores = jugadores;
		setPuntoParaTerminarChico(puntoParaTerminarChico);
		this.bazas = new ArrayList<Baza>();
//		this.truco = new Trucoo();
//		truco.save(this);

		this.seCantoEnvido = false;
		this.seCantoTruco = false;

		mazo = new Mazo();
		repartir();
	}

	public void altaBaza() {
		Baza b = new Baza(this.getJugadores());
		b.save(this);
		// FIX arreglar
		this.bazas = new ArrayList<Baza>();
		this.bazas.add(b);
		// System.out.println("BAZA NUMERO " + this.bazas.size());

		// turno del primer jugador
		JugadorDAO.getInstancia().setTurno(this.jugadores.get(0));
	}

	private void repartir() throws UsuarioException, CategoriaException {

		// arreglo pone todas las cartas del jugador como jugadas antes de pedir mas
		for (Jugador jug : jugadores) {
			JugadorCartaDAO.getInstancia().limpiarCartas(jug.getId());
		}

		for (Jugador jug : jugadores) {
			Vector<Carta> cartas = this.mazo.getTresCartasRandom();
			jug.guardarCartas(cartas);
		}

	}

	public List<Pareja> getParejas() {
		return parejas;
	}

	public void setParejas(List<Pareja> parejas) {
		this.parejas = parejas;
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public int getPuntoParaTerminarChico() {
		return puntoParaTerminarChico;
	}

	public void setPuntoParaTerminarChico(int puntoParaTerminarChico) {
		this.puntoParaTerminarChico = puntoParaTerminarChico;
	}

	// TODO AGREGAR BUSCA UN JUGADOR EN UNA PAREJA

	public void cantarTruco() {
		
		Trucoo truco = this.getTruco();
		truco.setPuntosNoQuiero(truco.getPuntosQuiero());
		truco.setPuntosQuiero(truco.getPuntosQuiero() + 1);
		this.setTruco(truco);
		truco.save(this);
		
	}

	public void cantarReTruco() {
		
		Trucoo truco = this.getTruco();
		truco.setPuntosNoQuiero(truco.getPuntosQuiero());
		truco.setPuntosQuiero(truco.getPuntosQuiero() + 1);
		this.setTruco(truco);
		truco.save(this);
		
	}

	public void cantarVale4() {
		
		Trucoo truco = this.getTruco();
		truco.setPuntosNoQuiero(truco.getPuntosQuiero());
		truco.setPuntosQuiero(truco.getPuntosQuiero() + 1);
		this.setTruco(truco);
		truco.save(this);
	}

	public Pareja getPareja(int idJugador) {
		for (Pareja p : parejas) {
			if (p.tieneJugador(idJugador))
				return p;
		}
		return null;
	}

	public Pareja getParejaActual(int idJugador) {
		for (Pareja p : parejas) {
			if (p.tieneJugador(idJugador))
				return p;
		}
		return null;
	}

	public Pareja getParejaContrariaActual(int idJugador) {
		for (Pareja p : parejas) {
			if (!p.tieneJugador(idJugador))
				return p;
		}
		return null;
	}

	public void jugarCarta(Carta carta, Jugador jugador)
			throws JugadorException, CartaException, UsuarioException, CategoriaException {
		// TODO Auto-generated method stub

		// despues de jugar una carta se tiene que guardar la baza

		if (this.bazas.size() == 0) {
			Baza nuevaBaza = new Baza(this.getJugadores());
			nuevaBaza.save(this);
			// es la primera baza
			this.bazas.add(nuevaBaza);
		}

		Baza ultimaBaza = this.bazas.get(this.bazas.size() - 1);

		ultimaBaza.jugarCarta(carta, jugador);

		// la jugada mayor esta en baza y son 4 esta mal, el update es por id mano
		// actualizo la jugadamayor en todas las bazas de la mano

		// BazaDAO.getInstancia().actualizarJugadaMayor(ultimaBaza);
		// carga las ultimas modificaciones de la baza
		// this.bazas = BazaDAO.getInstancia().buscarBazaPorIDMano(this);

	}

	public Pareja obtenerParejaGanadora() throws CategoriaException, UsuarioException {
		Baza ultimaBaza = this.bazas.get(this.bazas.size() - 1);

		Jugador jMayorUlimaBaza = ultimaBaza.getJugadaMayor().getJugador();
		Pareja pMayorUlimaBaza = ParejaDAO.getInstancia().buscarParejaDeUnJugador(jMayorUlimaBaza.getId());

		Jugador jMayorPrimeraBaza = this.getBazas().get(0).getJugadaMayor().getJugador();
		Pareja pMayorPrimeraBaz = ParejaDAO.getInstancia().buscarParejaDeUnJugador(jMayorPrimeraBaza.getId());

		// si gano la primera y la segunda la misma pareja es la primera
		if (pMayorUlimaBaza.esPareja(pMayorPrimeraBaz.getIdPareja())) {

			return pMayorUlimaBaza;

		} else {
			// si no es ni la primera pareja ni la tercera entonces gano la pareja del medio
			Jugador jMayorSegundaBaza = this.getBazas().get(1).getJugadaMayor().getJugador();
			return ParejaDAO.getInstancia().buscarParejaDeUnJugador(jMayorSegundaBaza.getId());

		}

	}

	public void armarNuevaBaza() throws UsuarioException, CategoriaException {

		// modifica el orden de los jugadores para la nueva baza
		
		Baza b = new Baza(this.getJugadores());
		b.save(this);
		// FIX arreglar
		this.bazas.add(b);

		// turno del primer jugador
		JugadorDAO.getInstancia().setTurno(this.jugadores.get(0));
		

	}

	public boolean terminoMano() throws CategoriaException, UsuarioException {
		// 3 Bazas maximo

		if (this.getBazas().size() == 3)
			return true;
		else {
			if (this.getBazas().size() > 1) {
				Pareja pareja = ParejaDAO.getInstancia()
						.buscarParejaDeUnJugador(this.getBazas().get(0).getJugadaMayor().getJugador().getId());
				Pareja pareja1 = ParejaDAO.getInstancia()
						.buscarParejaDeUnJugador(this.getBazas().get(1).getJugadaMayor().getJugador().getId());
				if (pareja.getIdPareja() == pareja1.getIdPareja())
					return true;
			}
		}
		return false;
	}

	public List<Baza> getBazas() {
		return bazas;
	}

	public void setBazas(List<Baza> bazas) {
		this.bazas = bazas;
	}

	public Envidoo getEnvido() {
		return envido;
	}

	public void setEnvido(Envidoo envido) {
		this.envido = envido;
	}

	public Trucoo getTruco() {
		return truco;
	}

	public void setTruco(Trucoo truco) {
		this.truco = truco;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public int getIdMano() {
		return idMano;
	}

	public void setIdMano(int idMano) {
		this.idMano = idMano;
	}

	public boolean seCantoEnvido() {
		return seCantoEnvido;
	}

	public void setSeCantoEnvido(boolean seCantoEnvido) {
		this.seCantoEnvido = seCantoEnvido;
	}

	public Baza getUltimaBaza() {
		return this.bazas.get(this.bazas.size() - 1);
	}

	public boolean seCantoTruco() {
		return seCantoTruco;
	}

	public void setSeCantoTruco(boolean seCantoTruco) {
		this.seCantoTruco = seCantoTruco;
	}

	public void save(Chico chico) {
		this.setIdMano(ManoDAO.getInstancia().guardarMano(chico, this));
	}

	public ManoDTO toDTO() {
		// TODO Auto-generated method stub

		List<ParejaDTO> parDTO = new ArrayList<>();
		for (Pareja p : parejas) {
			parDTO.add(p.toDTO());
		}

		List<BazaDTO> bazDTO = new ArrayList<>();
		for (Baza b : bazas) {
			bazDTO.add(b.toDTO());
		}

		List<JugadorDTO> jugDTO = new ArrayList<>();
		for (Jugador j : jugadores) {
			jugDTO.add(j.toDTO());
		}

		return new ManoDTO(idMano, parDTO, bazDTO, jugDTO, puntoParaTerminarChico, seCantoEnvido, seCantoTruco);
	}

	
	
	// �VER POR QUE ESTA ACA Y NO EN BAZA?
	// LA BAZA TERMINA CUANDO HAY 4 JUGADAS HECHAS, NO CUANDO HAY 3 BAZAS EN MANO
	public boolean terminoBaza() {
		if (this.getUltimaBaza().getJugadas().size() == 4)
			return true;
		else
			return false;
	}

	public void cantarEnvido() {
		
		Envidoo env = this.getEnvido();
		
		if (env.getPuntosQuiero() == 1) {
			env.setPuntosQuiero(env.getPuntosQuiero() + 1);
		} else {
			env.setPuntosNoQuiero(env.getPuntosQuiero());
			env.setPuntosQuiero(env.getPuntosQuiero() + 2);
		}
		
		this.setEnvido(env);
		env.save(this);		

	}

	public void cantarRealEnvido() {
		
		Envidoo env = this.getEnvido();
		
		if (env.getPuntosQuiero() == 1) {
			env.setPuntosQuiero(env.getPuntosQuiero() + 2);
		} else {
			env.setPuntosNoQuiero(env.getPuntosQuiero());
			env.setPuntosQuiero(env.getPuntosQuiero() + 3);
		}
		
		this.setEnvido(env);
		env.save(this);		
		
		

	}

	public void cantarFaltaEnvido(int puntosParaTerminar) {
		
		Envidoo env = this.getEnvido();
		
		if (env.getPuntosQuiero() == 1) {
			env.setPuntosQuiero(puntosParaTerminar);
		} else {
			env.setPuntosNoQuiero(env.getPuntosQuiero());
			env.setPuntosQuiero(puntosParaTerminar);
		}
		
		this.setEnvido(env);
		env.save(this);	


	}

	// VER SI SE BORRAN LAS CARTAS QUE TIENE EL JUGADOR. PUEDE TRAER PROBLEMAS
	public Pareja obtenerParejaGanadoraEnvido() {
		Jugador ganador = this.getJugadores().get(0);
		int puntos = ganador.getTantoEnvido();
		for (Jugador jugador : this.jugadores)
			if (jugador.getTantoEnvido() > puntos) {
				ganador = jugador;
				puntos = jugador.getTantoEnvido();
			}
		
		
		Pareja pareja2 = this.getPareja(ganador.getId());
		return pareja2;
	}


}
