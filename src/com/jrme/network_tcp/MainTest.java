package com.jrme.network_tcp;

import java.net.Socket;

import com.jrme.network_tcp.Client.SocketListener;
import com.jrme.network_tcp.DiscoveryThread.OnServerDetectedListener;

public class MainTest implements SocketListener{
	private Serveur serveur;
	private Client client;
	
	public MainTest(){
		
		//A LANCER SUR L'ORDI SERVEUR
		new DiscoverServerThread(2000,20).start(); //Lance le thread pour que les clients auto-détectent ce serveur
		
		//on lance le serveur TCP sur le port 8887
		serveur = new Serveur(8887);
		serveur.addSocketListener(this); //informe qu'on va recevoir les messages dans les methodes plus bas
		serveur.start();
		
		/*
		 * On peut faire coté serveur:
		 * 
		 * => Une fois la connexion etablit avec un client ou plusieurs:
		 *
		 * -  serveur.sendStringToAllClients(messageToSend); //pour envoyer un message a tout le monde
		 * -  serveur.sendStringToClient(socket, messageToSend) // pour envoyer un message a un client en particulier
		 * -  serveur.getSockets() //Retourne les sockets des clients actuellement connectés
		 * -  serveur.getNbClientsConnected() //Retourne le nombre de clients connecte
		 * 
		 * => Les listeners, pour etre notifier:
		 * 
		 * -  serveur.addOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener);  // quand un client se connecte ou se deconnecte
		 * -  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
		 */
		
		
		/*//A LANCER SUR UN ORDI CLIENT
		DiscoveryThread discoveryThread = new DiscoveryThread();
		discoveryThread.addOnServerDetectedListener(new OnServerDetectedListener(){

			@Override
			public void onServerDetected(String ipAdress) {
				//L'adresse du serveur a été trouvé
			    client = new Client(ipAdress,8887);
				client.addSocketListener(Main.this);
				client.sendString("salut");

			}});
		
		discoveryThread.start(); // Lance la recherche du serveur
		
		
		/*
		 * On peut faire coté client:
		 * 
		 * => Une fois la connexion etablit avec un client ou plusieurs:
		 *
		 * -  serveur.sendString(messageToSend); //pour envoyer un message au serveur
		 * 
		 * => Les listeners, pour etre notifier:
		 * 
		 * -  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
		 */
		
	}
	
	public static void main(String[] args) {
		new MainTest();
	}

	@Override
	public void onMessageSend(Socket socket, String message) {
		System.out.println("Message send : "+message);
		
		
	}

	@Override
	public void onMessageReceived(Socket socket, String message) {
		System.out.println("Message received from "+socket.getInetAddress().getHostAddress()+": "+message);
		
	}

}
