package FinalProject;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;  // Import the File class
import java.io.FileReader;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class DLTnode extends UnicastRemoteObject implements ServerAPI {
	
	public static void main(String[] args) {
		try {
			DLTnode tradingServer = new DLTnode();
			System.setSecurityManager(new SecurityManager());
			Naming.rebind("TradingServer", tradingServer);
			System.out.println("RMI Service registered");
		}catch(Exception e) {
			System.err.println("Exception thrown: "+e);
		}
	}
	protected DLTnode() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int SendPayment(String SourceAccount, String DestAccount, float balance) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
}
