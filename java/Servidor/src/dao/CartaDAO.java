package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entities.CartaEntity;
import entities.CategoriaEntity;
import excepciones.CategoriaException;
import hbt.HibernateUtil;
import negocio.Carta;

public class CartaDAO {
	
	private static CartaDAO instancia;

	public CartaDAO() {
	}

	public static CartaDAO getInstancia() {
		if (instancia == null) {
			instancia = new CartaDAO();
		}
		return instancia;
	}
	
	public Carta buscarCartaPorID(int idCarta) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		CartaEntity cartaEntity = (CartaEntity) session.createQuery("from CartaEntity where idCarta = ?")
				.setParameter(0, idCarta).uniqueResult();
		session.close();
		if (cartaEntity != null) {
			return toNegocio(cartaEntity);
		} else {
			// falta la excepcion Carta
			return null;
		}		
	}
	
	public CartaEntity buscarCartaPorIDEntity (int idCarta) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		CartaEntity cartaEntity = (CartaEntity) session.createQuery("from CartaEntity where idCarta = ?")
				.setParameter(0, idCarta).uniqueResult();
		session.close();
		if (cartaEntity != null) {
			return cartaEntity;
		} else {
			// falta la excepcion Carta
			return null;
		}	
	}
	
	public Carta toNegocio (CartaEntity carta) {
		Carta c = new Carta (carta.getNumero(), carta.getPalo(), carta.getPesoTruco(), carta.getPesoEnvido());
		c.setIdCarta(carta.getIdCarta());
		return c;		
	}

}
