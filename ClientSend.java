package client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientSend implements Runnable{
	private PrintWriter out; //va permettre de réceptionner les messages du client
	
	public ClientSend(PrintWriter out2){
		//initialise cet attribut
                this.out = out2; 
	}
         @Override
                public void run() {
                  Scanner sc = new Scanner(System.in);
                  while (true) {
                    System.out.print("Votre message >> ");
                    String m = sc.nextLine();
                    out.println(m);
                    out.flush();
                  }  
                }
}