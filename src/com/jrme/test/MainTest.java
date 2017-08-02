package com.jrme.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.jrme.network_tcp.Client;
import com.jrme.network_tcp.DiscoverServerThread;
import com.jrme.network_tcp.DiscoveryThread;
import com.jrme.network_tcp.DiscoveryThread.OnServerDetectedListener;
import com.jrme.network_tcp.Serveur;
import com.jrme.network_tcp.Serveur.OnClientConnectedListener;

public class MainTest extends JFrame implements ActionListener {

	private JButton create = new JButton("CREATE");
	private JButton join = new JButton("JOIN");
	private JTextArea jta=new JTextArea();
	private Serveur serveur;
	
	public MainTest()
	{
		super("Test de Jeu");
		
		jta.setPreferredSize(new Dimension(200,70));
		setSize(260,150);
		setLayout(new FlowLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(create);
		add(join);
		add(jta);
		setVisible(true);
		
		create.addActionListener(this);
		join.addActionListener(this);
		
	}
	public static void main(String[] args) {
		new MainTest();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==create){

			create.setEnabled(false);
			join.setEnabled(false);
			
			//A LANCER SUR L'ORDI SERVEUR
			new DiscoverServerThread(2000,20).start(); //Lance le thread pour que les clients auto-détectent ce serveur
						
			//on lance le serveur TCP sur le port 8887
			serveur = new Serveur(8887);
			serveur.start();
			serveur.addOnClientConnectedListener(new OnClientConnectedListener(){
				
				@Override
				public void onConnected(Socket socket){
					updateStatusClients();
				}
				
				@Override
				public void onDisconnected(Socket socket){
					updateStatusClients();
				}
			});
			
		}else if(e.getSource()==join){
			create.setEnabled(false);
			join.setEnabled(false);

			DiscoveryThread discoveryThread = new DiscoveryThread();
			discoveryThread.start(); // Lance la recherche du serveur
			discoveryThread.addOnServerDetectedListener(new OnServerDetectedListener(){

				@Override
				public void onServerDetected(String ipAdress) {
					//L'adresse du serveur a été trouvé
				    Client client = new Client(ipAdress,8887);
					client.sendString("salut");
				}});
			
		}
	}

	private void updateStatusClients() {
		String s = "";
		for(Socket socket:serveur.getSockets()){
			s  += socket.getInetAddress().getHostAddress()+" - Connected"+"\n";
		}
		System.out.println("test = "+s);

		jta.setText(s);
	}
}
