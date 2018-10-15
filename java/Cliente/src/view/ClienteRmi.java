package view;

import javax.swing.JOptionPane;

import delegado.BusinessDelegateTruco;
import dto.UsuarioDTO;
import excepciones.ComunicacionException;

public class ClienteRmi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// usuarios.add(new Usuario("Emiliano", "Emiliano", "pepe"));
			// usuarios.add(new Usuario("Debi", "Debi", "pepe"));
			// usuarios.add(new Usuario("Lucas", "Lucas", "pepe"));
			// usuarios.add(new Usuario("Amilcar", "Amilcar", "pepe"));
			//
//			 new BusinessDelegateTruco().alta("Emiliano", "Emiliano", "pepe");
//			 new BusinessDelegateTruco().alta("Debi", "Debi", "pepe");
//			 new BusinessDelegateTruco().alta("Lucas", "Lucas", "pepe");
//			 new BusinessDelegateTruco().alta("Amilcar", "Amilcar", "pepe");

			 
			//
			UsuarioDTO us1 = new BusinessDelegateTruco().login("Emiliano", "pepe");
			UsuarioDTO us2 = new BusinessDelegateTruco().login("Debi", "pepe");
			UsuarioDTO us3 = new BusinessDelegateTruco().login("Lucas", "pepe");
			UsuarioDTO us4 = new BusinessDelegateTruco().login("Amilcar", "pepe");

			new BusinessDelegateTruco().agregarAListaEspera(us1);
			new BusinessDelegateTruco().agregarAListaEspera(us2);
			new BusinessDelegateTruco().agregarAListaEspera(us3);
			new BusinessDelegateTruco().agregarAListaEspera(us4);
			
			
			
			
			
		//	new BusinessDelegateTruco().armarPareja(us1, us2);

			// new BusinessDelegateTruco().alta("pepe", "pepe", "pepe");
			// new BusinessDelegateTruco().login("pepe", "pepe");
		} catch (ComunicacionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

}
