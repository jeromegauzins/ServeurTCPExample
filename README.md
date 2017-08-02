# ServeurTCPExample
Un exemple complet de la mise en place d'un serveur TCP en java

# Communication en TCP Simple

Sur la machine serveur : 
```java
final Serveur serveur = new Serveur(8887);//on lance le serveur sur le port 8887
serveur.start(); // On lance le serveur
serveur.addSocketListener(new SocketListener(){

	@Override
	public void onMessageSend(Socket socket, String message) {
		System.out.println("Message envoyé :"+message); //Message envoyé à un client
	}

	@Override
	public void onMessageReceived(Socket socket, String message) {
		System.out.println("Message recu :"+message); //Message recu depuis un client
		
		serveur.sendStringToClient(socket,"Tais-toi sale client, je suis le serveur tout puissant !"); // On répond au client qui nous harcèle

	}} );
```

Sur la machine cliente : 
```java
final Client client = new Client(ipAdress,8887); // L'adresse ip du serveur (ou "localhost", si même machine), le port sur lequel le serveur est lancé. On se connecte au serveur
client.sendString("salut"); // On envoie 'salut' au serveur TCP
client.addSocketListener(new SocketListener(){

	@Override
	public void onMessageSend(Socket socket, String message) {
		System.out.println("Message envoyé :"+message); //Message envoyé au serveur
	}

	@Override
	public void onMessageReceived(Socket socket, String message) {
		System.out.println("Message recu :"+message); //Message reçu depuis le serveur
		client.sendString("Arrete de m'envoyer des messages serveur !! :)"); // On répond au serveur
	}} );
```
# Détection de serveur en UDP

Lancer le code de detection du serveur coté serveur : 

```java
//Premier parametre : Timeout avant de reessayer
//Deuxième paramètre :  Nombre d'essai
new DiscoverServerThread(2000,20).start(); //Lance le thread pour que les clients auto-détectent ce serveur
```

Lancer le code de detection du serveur coté client : 

```java

DiscoveryThread discoveryThread = new DiscoveryThread();
discoveryThread.start();// Lance la recherche du serveur
discoveryThread.addOnServerDetectedListener(new OnServerDetectedListener(){

	@Override
	public void onServerDetected(String ipAdress) {
		//L'adresse du serveur a été trouvé
		client = new Client(ipAdress,8887); // On cree un Nouveau client TCP
		client.addSocketListener(Main.this); // On ajouter un listener pour lire ce qui est envoyé et recu par ce client
		client.sendString("salut"); //On envoit 'salut' au serveur 

	}});
 ```


# Fonctions disponibles

## Coté Serveur

=> Une fois la connexion établit avec un client ou plusieurs:
```java
serveur.sendStringToAllClients(messageToSend); //pour envoyer un message a tout le monde
serveur.sendStringToClient(socket, messageToSend) // pour envoyer un message a un client en particulier
serveur.getSockets() //Retourne les sockets des clients actuellement connectés
serveur.getNbClientsConnected() //Retourne le nombre de clients connecte
```
=> Les listeners, pour etre notifier:
```java
serveur.addOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener);  // quand un client se connecte ou se deconnecte
serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
```
 
 ## Coté Client

=> Une fois la connexion etablit avec le serveur:
```java
serveur.sendString(messageToSend); //pour envoyer un message au serveur
```
=> Les listeners, pour etre notifier:
```java
serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
```


