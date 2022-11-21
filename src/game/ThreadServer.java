package game;

import java.io.*;
import java.net.*;

/**
 * Class ThreadServer.
 * @author sousap
 *
 */
public class ThreadServer implements Runnable {
	
	/**
	 * Socket variable.
	 */
	Socket sock;
	
	/**
	 * Variables for number clients.
	 */
	static int nclient = 0, nclients = 0;
	
	/**
	 * Server socket.
	 */
	static ServerSocket servsock;
	
	/**
	 * Default port.
	 */
	static int PORT = 3000;
	
	/**
	 * Number of port.
	 */
	static int portNumber = 0;

	/**
	 * Default constructor.
	 */
	public ThreadServer() {
		; // No commands.
	}
	
	/**
	 * Main method.
	 * @param args Param arguments.
	 */
	public static void main(String args[]) {
    	if (args == null) {
            portNumber = PORT;    		
    	} else if (args.length < 1) {
            //System.err.println("Usage: java EchoServer <port number>");
            portNumber = PORT;
        } else {
            portNumber = Integer.parseInt(args[0]);
        }
        System.out.println("Starting Server Thread on port " + portNumber);
		try {
			servsock = new ServerSocket(portNumber);
			Thread servDaemon = new Thread(new ThreadServer());
			servDaemon.start();
			System.out.println("Server running on " + InetAddress.getLocalHost() + " at port " + portNumber + "!");
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	/**
	 * Run method.
	 */
	public void run() {
		for (;;) {
			try {
				sock = servsock.accept();
				nclient += 1;
				nclients += 1;
				System.out.println("Connecting " + sock.getInetAddress() + " at port " + sock.getPort() + ".");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
			Worked w = new Worked(sock, nclient);
			w.start();
		}
	}

	/**
	 * Inner class for Theads.
	 * @author sousap
	 *
	 */
	class Worked extends Thread {
		
		/**
		 * Socket variable.
		 */
		Socket sock;
		
		/**
		 * Integers for client and positions.
		 */
		int clientid, poscerq;
		
		/**
		 * String for data.
		 */
		String strcliid, dadosCliente;

		/**
		 * Default constructor.
		 * @param s Socket
		 * @param nclient Number of client.
		 */
		public Worked(Socket s, int nclient) {
			sock = s;
			clientid = nclient;
		}

		/**
		 * Run method.
		 */
		public void run() {
			String data;
			PrintStream out = null;
			BufferedReader in;
			try {
				out = new PrintStream(sock.getOutputStream());
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out.println(clientid);
				data = in.readLine();
				poscerq = data.indexOf("#");
				strcliid = data.substring(0, poscerq);
				dadosCliente = data.substring(poscerq + 1, data.length());
				while (!dadosCliente.equals("end")) {
					System.out.println("Cli[" + strcliid + "]: " + data);
					out.println("String \"" + data + "\" received.");
					out.flush();
					data = in.readLine();
					poscerq = data.indexOf("#");
					strcliid = data.substring(0, poscerq);
					dadosCliente = data.substring(poscerq + 1, data.length());
				}
				System.out.println("Disconecting " + sock.getInetAddress() + "!");
				nclients -= 1;
				System.out.println("Current client number: " + nclients);
				if (nclients == 0) {
					System.out.println("Ending server...");
					sock.close();
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}
}
