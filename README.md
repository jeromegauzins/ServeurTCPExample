# ServeurTCPExample
Un exemple complet de la mise en place d'un serveur TCP en java

# Coté Serveur

=> Une fois la connexion etablit avec un client ou plusieurs:

-  serveur.sendStringToAllClients(messageToSend); //pour envoyer un message a tout le monde
-  serveur.sendStringToClient(socket, messageToSend) // pour envoyer un message a un client en particulier
-  serveur.getSockets() //Retourne les sockets des clients actuellement connectés
-  serveur.getNbClientsConnected() //Retourne le nombre de clients connecte

=> Les listeners, pour etre notifier:

-  serveur.addOnClientConnectedListener(OnClientConnectedListener onClientConnectedListener);  // quand un client se connecte ou se deconnecte
 -  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message
 
 # Coté Client

=> Une fois la connexion etablit avec le serveur:

-  serveur.sendString(messageToSend); //pour envoyer un message au serveur

=> Les listeners, pour etre notifier:

-  serveur.addSocketListener(SocketListener socketListener); // quand on recoit ou on emet un message

 


