package com.jrme.network_tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
	
	private String serveurIP;
	private int port;
	private Socket socket;
	private SocketListener socketListener;
	
	private BufferedReader dis;
	private BufferedWriter dos;

	public Client(String serveurIP, int port){
		this.serveurIP = serveurIP;
		this.port = port;
		ouvrirSocket();

	}
	
	private void ouvrirSocket() {
		try {
			this.socket = new Socket(serveurIP,port);
			this.socket.setSoTimeout(0);
			this.dis =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.dos =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			new Thread(this).start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendString(String messageToSend){
		if(socket == null || socket.isClosed() || !socket.isConnected()){
			ouvrirSocket();
		}
		
		StreamUtils.writeToOutputStream(dos,messageToSend);
		
		if(socketListener!=null){
			socketListener.onMessageSend(socket, messageToSend);
		}
	}

	public void addSocketListener(SocketListener socketListener) {
		this.socketListener=socketListener;
	}
	
	public interface SocketListener{
		void onMessageSend(Socket socket,String message);
		void onMessageReceived(Socket socket,String message);
	}

	@Override
	public void run() {
		while(socket.isConnected() && !socket.isClosed()){
			String messageReceived = StreamUtils.readFromInputStream(dis);
			if(socketListener!=null && messageReceived.length()>0)socketListener.onMessageReceived(socket, messageReceived);

		}
	}

	public Socket getSocket() {
		return socket;
	}
	

}
