# gameserver

Why Websocket:
- enables continuous open connection over TCP
- build in mechanism which checks if the server supports WebSockets
- event driven , it means both sides the client and server will know whats going on . and will be notified by callbacks.

Third party components used:
- Netty for client-server communication
- Redis for caching
- Hibernate as ORM in persistence layer
- Jackson for JSON marshal and unmarshal
- Mysql for relational models
- Mongo for meta data of each transaction (e.g. moves played by a player in the game, steps in each game)

GameServerMain - Main method class. Its initialized the WebSocketServer class.

WebSocketServer - This class is standard initialization sequence of Netty server.It will set Netty to be A sync when it comes to  handling incoming requests, Non-blocking I/O.

GameSeverInitializer - This class is where the Channel handlers are set up , someone them are Netty build in handlers and some of them are Extended Class's.

TextWebSocketFrameHandler:
userEventTriggered - The method which handles the HTTP hand shake post the websocket protocol is opened for the client.
channelRead0 - This method receives all the messages from each client on their specific channel. This handles the request to a GameEventHandler. Based on the response of handle event the response is written on the each player's channel. Event when a new player joins, message is relayed to new player about existing players and existing players about the new player.
											
GameEventHandler - based on the event send in the request we perform necessary operation. Play/Leave game. Request/Response objects for players calls

PlayerManager - Encapsulates playerservice and returns player entity. creates if doesn't exist

TableManager - Knows about all the tables that exist. Keeps a separate tab of tables which are available for joining. Takes a player and puts it on a available table

GameManager - Has methods to start game on table and destroy the table. Has a executor pool which assigns a thread to finish a game on the table. Uses a event dispatcher to updatet the status of tables on TableManager

EventDispatcher - dispatches event processing to respective listeners

CLientConnection - Uses sendPacket method to communicate with specific players. Does it asynchronously by using a thread pool

Services - Table,Player service 

JungleeRedis - Caching util wrapping jedis

DTO - Table and player dtos. Object persisted in the cache

Persistent Layer (not using this right now) - DBHandler implemented by MySql and Mongo handlers. Table and Player DAO use these to implement crud operations. Entity for player and table
 
