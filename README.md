# BacarratGameServerAndClient


### About this project:

This project was created in Eclipse and uses JavaFX for the GUI. This project is a game of Bacarrat (About Bacarrat: https://en.wikipedia.org/wiki/Baccarat) which is hosted on a server and can be played on many clients. The user starts with an arbitrary amount of money and can place bets on the Host, Player, or a tie. After they place their bet, the deck will be shuffled and they will be able to see what the dealer has drawn and whether or not their bet was correct. If the player wins the bet, they will recieve (Their bet * 1.95) in game money.

### Images of the game running:

![baccarrat server gui 1](https://user-images.githubusercontent.com/85149000/235393444-6edf815c-c77f-4d5c-9e47-931d40411e56.png)
![server gui 2](https://user-images.githubusercontent.com/85149000/235393445-7dcf768b-bfa2-4978-b6e5-33ab7c363c70.png)
![client gui 1](https://user-images.githubusercontent.com/85149000/235393446-2efbc18b-5bf2-4869-a670-ea928a919c96.png)
![client connected 1](https://user-images.githubusercontent.com/85149000/235393447-2411b6f7-b30e-4a16-9a5f-a65000c47c97.jpg)
![betplaced1](https://user-images.githubusercontent.com/85149000/235393448-31ed7b7b-b754-4224-bfd1-a4a1c50bb5fc.jpg)


### How to run:

In order to run this project, you must first download the Server/Client files and import them as a new project in Eclipse. Next, you must import the JavaFX libraries into your libraries in Eclipse. JavaFX Files Link: https://gluonhq.com/products/javafx/ (Choose SDK version). After this, the project should be running. Lastly, in order to connect clients, the server must be hosted on port 5555 as this is the port the client is trying to connect to. After this, you can connect on a client via localhost IP address (127.0.0.1) and play Baccarat!

### Credits/Libraries Used:

JavaFX (Source): https://gluonhq.com/products/javafx/
