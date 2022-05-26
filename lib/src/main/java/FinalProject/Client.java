package FinalProject;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client implements Runnable{
	private String host;
	private ServerAPI serverHandler;
	public Client(String host) {
	    try {
	    	this.host = host;
	        Registry registry = LocateRegistry.getRegistry(host);
	        serverHandler = (ServerAPI)registry.lookup("tradingServer");
	    } catch(Exception e) {
	        System.err.println("Failed accessing RMI: "+e);
	    }
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
