package servidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import interfaces.InterfaceRemotaTruco;
import remoto.ObjetoRemotoTruco;

public class ServidorTruco {

	public ServidorTruco() throws RemoteException{
		inicializar();
	}

	private void inicializar() throws RemoteException {
		InterfaceRemotaTruco or = new ObjetoRemotoTruco();
		try {
			LocateRegistry.createRegistry(1099);
			Naming.rebind("//127.0.0.1/truco", or);
			System.out.println("SERVIDOR TRUCO OK");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
