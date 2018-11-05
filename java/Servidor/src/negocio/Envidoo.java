package negocio;

import dao.EnvidooDAO;

public class Envidoo {
	
	
	private int id;
	private int puntosQuiero;
	private int puntosNoQuiero;
	
	public Envidoo () {
		
	}
	
	public Envidoo (int puntosQuiero) {
		this.puntosQuiero = puntosQuiero;
		this.puntosNoQuiero = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPuntosQuiero() {
		return puntosQuiero;
	}

	public void setPuntosQuiero(int puntosQuiero) {
		this.puntosQuiero = puntosQuiero;
	}

	public int getPuntosNoQuiero() {
		return puntosNoQuiero;
	}

	public void setPuntosNoQuiero(int puntosNoQuiero) {
		this.puntosNoQuiero = puntosNoQuiero;
	}

	public void save(Mano mano) {
		
		this.setId(EnvidooDAO.getInstancia().guardarEnvido(this, mano));
	
	}
	
	

}