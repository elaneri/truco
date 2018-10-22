package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.CategoriaEntity;
import entities.ChicoEntity;
import entities.JugadorEntity;
import entities.UsuarioEntity;
import excepciones.CategoriaException;
import excepciones.UsuarioException;
import hbt.HibernateUtil;
import negocio.Juego;
import negocio.Jugador;
import negocio.JugadorIndividual;

public class JugadorDAO {
	private static JugadorDAO instancia;

	private List<Jugador> jugadores;

	public static JugadorDAO getInstancia() {
		if (instancia == null)
			instancia = new JugadorDAO();
		return instancia;
	}

	public JugadorDAO() {
	}

	public JugadorEntity buscarJugadorById(int idJugador) throws UsuarioException, CategoriaException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		JugadorEntity jugadorEntity = (JugadorEntity) session.createQuery("from JugadorEntity where idJugador = ?")
				.setParameter(0, idJugador).uniqueResult();
		session.close();
		if (jugadorEntity != null) {
			return jugadorEntity;
		} else {
			throw new UsuarioException("El jugador con id: " + idJugador + "no existe en la base de datos.");
		}
	}

	public Jugador buscarJugadorByIdClase(int idJugador) throws UsuarioException, CategoriaException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		JugadorEntity jugadorEntity = (JugadorEntity) session.createQuery("from JugadorEntity where idJugador = ?")
				.setParameter(0, idJugador).uniqueResult();
		session.close();
		if (jugadorEntity != null) {
			return toNegocio(jugadorEntity);
		} else {
			throw new UsuarioException("El jugador con id: " + idJugador + "no existe en la base de datos.");
		}
	}

	public Jugador toNegocio(JugadorEntity pe) throws CategoriaException {
		Jugador j = null;
		if (pe.getTipo().equals("individual")) {
			j = new JugadorIndividual(UsuarioDAO.getInstancia().toNegocio(pe.getUsuario()));
			j.setTieneTurno(pe.geTieneTurno());
			j.setOrden(pe.getOrden());

			j.setId(pe.getIdJugador());

		}
		return j;
	}

	public Jugador buscarJugadorByUsario(int idJuego, int idUsuario) throws CategoriaException {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		JugadorEntity jugadorEntity = (JugadorEntity) session
				.createQuery("from JugadorEntity where idJuego = ? and idUsuario = ?").setParameter(0, idJuego)
				.setParameter(1, idUsuario).uniqueResult();
		session.close();
		if (jugadorEntity != null) {
			return toNegocio(jugadorEntity);
		} else {
			// throw new UsuarioException("El jugador con id: " + idJugador + "no existe en
			// la base de datos.");
		}
		return null;
	}

	public List<Jugador> buscarJugadoresByJuego(int idJuego) throws CategoriaException {
		// TODO Auto-generated method stub
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();

		List<JugadorEntity> jugadoresEntity = (List<JugadorEntity>) session
				.createQuery("from JugadorEntity where idJuego = ? ").setParameter(0, idJuego).list();

		session.close();
		List<Jugador> jugadores = new ArrayList<>();

		for (JugadorEntity je : jugadoresEntity) {
			jugadores.add(toNegocio(je));
		}

		return jugadores;
	}

	public void setTurno(Jugador jugador) {
		// TODO Auto-generated method stub

	}

	public Jugador getJugadorConTurno(Juego juego) throws CategoriaException {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		JugadorEntity jugadorEntity = (JugadorEntity) session
				.createQuery("from JugadorEntity where idJuego = ? and tieneTurno=1").setParameter(0, juego.getId())
				.uniqueResult();
		session.close();
		if (jugadorEntity != null) {
			return toNegocio(jugadorEntity);
		} else {
			// throw new UsuarioException("El jugador con id: " + idJugador + "no existe en
			// la base de datos.");
		}

		return null;
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public void getPasarTurno(Juego juego) throws CategoriaException, UsuarioException {
		// TODO Auto-generated method stub

		Jugador jturno = this.getJugadorConTurno(juego);
		//fue la ultima
		if ((jturno.getOrden()+1)==4)return;

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		JugadorEntity jugadorAnterior = this.buscarJugadorById(jturno.getId());
		
		session.beginTransaction();
		jugadorAnterior.setTieneTurno(false);
		session.saveOrUpdate(jugadorAnterior);
		
		session.getTransaction().commit();

		
		
		
		
		
		JugadorEntity jugadorSiguiente = (JugadorEntity) session
				.createQuery("from JugadorEntity where idJuego = ? and orden = ?").setParameter(0, juego.getId())
				.setParameter(1, (jturno.getOrden() + 1)).uniqueResult();
		

		session.beginTransaction();
		jugadorSiguiente.setTieneTurno(true);
		
		
		session.saveOrUpdate(jugadorSiguiente);
		session.getTransaction().commit();

	
		session.close();
	}

	public void actualizarTurnos(List<Jugador> jugadores) throws UsuarioException, CategoriaException {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		int i = 1;

		JugadorEntity jePrimero = JugadorDAO.getInstancia().buscarJugadorById(jugadores.get(0).getId());
		jePrimero.setTieneTurno(true);
		session.saveOrUpdate(jePrimero);

		for (int x = 1; x < jugadores.size(); x++) {
			JugadorEntity je = JugadorDAO.getInstancia().buscarJugadorById(jugadores.get(x).getId());

			je.setOrden(i);

			session.saveOrUpdate(je);
			i++;

		}

		session.getTransaction().commit();
		session.close();

	}

}