package entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import negocio.Pareja;

@Entity
@Table(name = "Juego")
public class JuegoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idJuego;

	@OneToOne
	@JoinColumn(name = "idPareja1")
	private ParejaEntity pareja1;

	@OneToOne
	@JoinColumn(name = "idPareja2")
	private ParejaEntity pareja2;

	@OneToOne
	@JoinColumn(name = "idParejaGanadora")
	private ParejaEntity parejaGanadora;

	private String tipoDeJuego;

	private Date fecha;

	private boolean activo;

	public JuegoEntity(ParejaEntity pareja1, ParejaEntity pareja2, String tipoDeJuego) {
		super();
		this.pareja1 = pareja1;
		this.pareja1 = pareja1;
		this.tipoDeJuego = tipoDeJuego;
		this.activo = true;

	}
}
