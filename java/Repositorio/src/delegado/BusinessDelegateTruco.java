package delegado;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import dto.CartaDTO;
import dto.JuegoDTO;
import dto.ParejaDTO;
import dto.UsuarioDTO;
import excepciones.ComunicacionException;
import excepciones.MiembroException;
import interfaces.InterfaceRemotaTruco;

public class BusinessDelegateTruco {

	private InterfaceRemotaTruco ir;

	public BusinessDelegateTruco() throws ComunicacionException {
		try {
			ir = (InterfaceRemotaTruco) Naming.lookup("//127.0.0.1/truco");
		} catch (MalformedURLException e) {
			throw new ComunicacionException("La direccion especificada no es correcta");
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		} catch (NotBoundException e) {
			throw new ComunicacionException("El servidor no esta disponible");
		}
	}

	public UsuarioDTO login(String usuario, String pass) throws ComunicacionException {
		try {
			return ir.login(usuario, pass);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}

	public int alta(String apodo, String email, String password) throws ComunicacionException {
		try {
			return ir.altaUsuario(apodo, email, password);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}

	public void agregarAListaEspera(UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.agregarAListaEspera(usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}

	public List<JuegoDTO> getJuegosActivo(UsuarioDTO usuario) throws ComunicacionException {
		try {
			return ir.getJuegosActivo(usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	public JuegoDTO getJuegosById(int juego) throws ComunicacionException {
		try {
			return ir.getJuegosById(juego);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public boolean alguienTieneQueContestar(JuegoDTO juego) throws ComunicacionException {
		try {
			return ir.alguienTieneQueContestar(juego);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public String tengoQueContestar(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			return ir.tengoQueContestar(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public boolean esMiTurno(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			return ir.esMiTurno(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}

	public List<CartaDTO> getCartas(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			return ir.getCartas(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}


	public void jugarCarta(JuegoDTO juego, CartaDTO carta, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.jugarCarta(juego, carta, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public void cantarEnvido(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cantarEnvido(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	public void cantarEnvidoEnvido(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cantarEnvidoEnvido(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	public void cantarRealEnvido(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException{
		try {
			ir.cantarRealEnvido(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public void cantarFaltaEnvido(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException{
		try {
			ir.cantarFaltaEnvido(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}

	public void quieroEnvido(JuegoDTO juego) throws ComunicacionException, MiembroException {
		try {
			ir.quieroEnvido(juego);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}

	public void noQuieroEnvido(JuegoDTO juego, UsuarioDTO us1) throws ComunicacionException, MiembroException {
		try {
			ir.noQuieroEnvido(juego, us1);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	public void cantarTruco(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cantarTruco(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	public void cantarReTruco(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cantarReTruco(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	public void cantarValeCuatro(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cantarValeCuatro(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	public void quieroTruco(JuegoDTO juego) throws ComunicacionException {
		try {
			ir.quieroTruco(juego);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	public void noQuieroTruco(JuegoDTO juego, UsuarioDTO us1) throws ComunicacionException, MiembroException {
		try {
			ir.noQuieroTruco(juego, us1);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	
	public void modificarUsuario(String apodo, String password, String nuevoEmail, String nuevaPass, String nuevoApodo) throws ComunicacionException {
		try {
			ir.modificarUsario(apodo, password, nuevoEmail, nuevaPass, nuevoApodo);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
		
	}
	
	public void altaGrupo(String nombre, UsuarioDTO administrador) throws ComunicacionException {
		try {
			ir.altaGrupo(nombre, administrador);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public ParejaDTO agregarParejaLibreAEspera(UsuarioDTO usuario1, UsuarioDTO usuario2) throws ComunicacionException {
		try {
			return ir.agregarParejaLibreAEspera(usuario1, usuario2);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public void cancelarEsperaJugador(UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.cancelarEsperaJugador(usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public void cancelarEsperaPareja(ParejaDTO pareja) throws ComunicacionException{
		try {
			ir.cancelarEsperaPareja(pareja);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public void salirJuego(JuegoDTO juego, UsuarioDTO usuario) throws ComunicacionException {
		try {
			ir.salirJuego(juego, usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}		
	}	
	
	public List<UsuarioDTO> generarRanking() throws ComunicacionException{
		try {
			return ir.generarRanking();
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}	
	}
	
	public void desloggearUsuario(UsuarioDTO usuario) throws ComunicacionException{
		try {
			ir.desloggearUsuario(usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public List<UsuarioDTO> obtenerUsuariosLoggeados(UsuarioDTO usuario) throws ComunicacionException {
		try {
			return ir.obtenerUsuariosLoggeados(usuario);
		} catch (RemoteException e) {
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	public UsuarioDTO buscarUsuarioPorApodoDTO(String apodo) throws ComunicacionException {
		try {
			return ir.buscarUsuarioPorApodoDTO(apodo);
		} catch (RemoteException e){
			throw new ComunicacionException("Error en las comunicaciones");
		}
	}
	
	
	
	
}
