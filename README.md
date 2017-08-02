# ServeurTCPExample
Un exemple complet de la mise en place d'un serveur TCP en java

# Coté Serveur

=> Une fois la connexion etablit avec un client ou plusieurs:
<code>
-  serveur.sendStringToAllClients(messageToSend); //pour envoyer un message a tout le monde
-  serveur.sendStringToClient(socket, messageToSend) // pour envoyer un message a un client en particulier
-  serveur.getSockets() //Retourne les sockets des clients actuellement connectés
-  serveur.getNbClientsConnected() //Retourne le nombre de clients connecte
</code>
=> Les listeners, pour etre notifier:
<code>

-  serveur.addOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener);  // quand un client se connecte ou se deconnecte
 -  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
 </code>
 
 # Coté Client

=> Une fois la connexion etablit avec le serveur:
<code>
-  serveur.sendString(messageToSend); //pour envoyer un message au serveur
</code>
=> Les listeners, pour etre notifier:
<code>
-  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message

 </code>


