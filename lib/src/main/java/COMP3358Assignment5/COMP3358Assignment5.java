package COMP3358Assignment5;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.PutResponse;

public class COMP3358Assignment5 {
	jetcdAPI tool;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to use the Jetcd Client written by Ho Sui Ting.");
		System.out.println("This program is developed based on Jetcd from Github.");
		System.out.println("This program provide incGet and decGet request to the etcd cluster.");
		System.out.println("Usage : incGet [key] = increase the counter(key) by 1 and return result");
		System.out.println("Usage : decGet [key] = decrease the counter(key) by 1 and return result");
		System.out.println("Usage : get [key] = return the counter value");
		new COMP3358Assignment5();
	}
	public COMP3358Assignment5() {
		this.tool = new jetcdAPI();
		try {
			go();
		} catch (ExecutionException e) {
			System.out.print("TimeOut");
			System.out.print("Null");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void go() throws ExecutionException, InterruptedException {
		Scanner keyboard = new Scanner(System.in);
		String line;
		System.out.print("> ");
		while(!(line = keyboard.next()).equals("exit")) {
			if(line.equals("get")) {
				get(keyboard.next());
			}else if(line.equals("put")) {
				put(keyboard.next(),keyboard.next());
			}else if(line.equals("incGet")) {
				incGet(keyboard.next());
			}else if(line.equals("decGet")) {
				decGet(keyboard.next());
			}else {
				System.out.print("Wrong usage!! Command: put, incGet, decGet, get");
			}
			System.out.print("> ");
		}
		keyboard.close();	
	}
	private void get(String key) throws ExecutionException, InterruptedException {
		String result;
		result = tool.get(key);
		System.out.println(result);
	}
	
	private void put(String key, String value) throws ExecutionException, InterruptedException {
		PutResponse result;
		result = tool.put(key,value);
		if(result != null) {
			System.out.println(key + " " + value);
		}
	}
	
	private void incGet(String key) throws ExecutionException, InterruptedException {
		String result;
		result = tool.incGet(key);
		if(result != null) {
			System.out.println(result);
		}else {
			System.out.println("NULL");
		}
	}
	
	private void decGet(String key) throws ExecutionException, InterruptedException {
		String result;
		result = tool.decGet(key);
		if(result != null) {
			System.out.println(result);
		}else {
			System.out.println("NULL");
		}
	}
}
/*
private String get(String key) {
	try {
		tool.put("counter", "1000");
		String result = tool.get("counter");
		System.out.println(result );
	} catch (ExecutionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/

