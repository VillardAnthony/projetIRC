package client;

import IHM.ClientPanel;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
//import IHM.ClientPanel;

public class ClientSend implements Runnable{
	private PrintWriter out; //va permettre de réceptionner les messages du client
        private Label lab;
        private ClientPanel cp;
       // private boolean envoyer;
	
	public ClientSend(PrintWriter out2/*, Label m*/, ClientPanel clp/*,boolean b*/){
        //initialise cet attribut
            this.out = out2; 
           // this.lab=m;
            this.cp=clp;
            //this.envoyer=b;
	}
        @Override
        public void run() {
           Scanner sc = new Scanner(System.in);

            while (/*this.envoyer==*/true) {
               // this.envoyer=false;
                System.out.print("Votre message >> ");
                //Label m = this.lab;
                //String m = sc.nextLine();
                //out.println(m);
                out.flush();
            }  
        }
}