package dto;

import java.io.Serializable;

public class UsuarioDTO extends JsonDTO implements Serializable {

	private static final long serialVersionUID = 913850522288957303L;

	private int idUsuario;
	private int partidasGanadas;
	private int partidasJugadas;
	private int puntaje;
	private String apodo;
	private String email;
	private CategoriaDTO categoria;
	private boolean activo;

	public UsuarioDTO(int idUsuario, int partidasGanadas, int partidasJugadas, int puntaje, String apodo, String pass,
			String email, CategoriaDTO categoria, boolean activo) {
		super();
		this.idUsuario = idUsuario;
		this.partidasGanadas = partidasGanadas;
		this.partidasJugadas = partidasJugadas;
		this.puntaje = puntaje;
		this.apodo = apodo;
		this.email = email;
		this.categoria = categoria;
		this.activo = activo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getPartidasGanadas() {
		return partidasGanadas;
	}

	public void setPartidasGanadas(int partidasGanadas) {
		this.partidasGanadas = partidasGanadas;
	}

	public int getPartidasJugadas() {
		return partidasJugadas;
	}

	public void setPartidasJugadas(int partidasJugadas) {
		this.partidasJugadas = partidasJugadas;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public CategoriaDTO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		 return getJson(this);
	}

}
