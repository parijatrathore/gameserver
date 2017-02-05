package com.junglee.assignment.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.event.Interaction;
import com.junglee.assignment.event.GameEventHandler;
import com.junglee.assignment.event.response.game.GameLoginResponse;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.utils.JSONUtils;

public class GameResponseDispatcher {
	
	private final static Logger LOG = LoggerManager.GetLogger(GameEventHandler.class.getName());
	
	public boolean ResponseDispatcheLoginDone(String  userName, int _tableId)
	{
		String currentPlayerName = userName;
		PlayerDTO currentPlayer = TableManager.getInstance().getTable(_tableId).getPlayers().get(currentPlayerName);
		GameLoginResponse response = new GameLoginResponse(currentPlayer, currentPlayer.getEvent());
		
		//build the other players json
		List<PlayerDTO> currentNewPlayers = new ArrayList<PlayerDTO>();
		List<PlayerDTO> currentPlayers = new ArrayList<PlayerDTO>();
    	currentPlayers.add(currentPlayer);
    	
    	Iterator<Entry<String, PlayerDTO>> it = TableManager.getInstance().getTable(_tableId).getPlayers().entrySet().iterator();
	    while (it.hasNext()) {
	    	@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
	        PlayerDTO playerIt = ((PlayerDTO)pair.getValue());
	         
	        if(!currentPlayerName.equalsIgnoreCase(playerIt.getUserName()))
	        {
	        	TableManager.getInstance().getTable(_tableId).getPlayers().get(playerIt.getId()).setEvent(Interaction.NEW_USER_LOGIN_DONE); 
	        	GameLoginResponse loginResponse = new GameLoginResponse(playerIt, Interaction.NEW_USER_LOGIN_DONE.getNum());
	        	//to current
	        	//LOG.severe(newPlayerForCurrent.toString()); 	        	 
	        	currentNewPlayers.add(playerIt);
	        	//LOG.severe(currentPlayerArrayNewPlayers.toString());
	        	//to others
	        	loginResponse.setCurrentPlayers(currentPlayers);
	        	String jsonStr = JSONUtils.objectToString(loginResponse);
	        	//LOG.severe("jsonStr in while:"+jsonStr);
	        	TableManager.getInstance().getTable(_tableId).getPlayers().get(playerIt.getId()).setPlayerJson(jsonStr);
	        	//LOG.severe("currentPlayerArrayNewPlayers:"+currentPlayerArrayNewPlayers.toString());
	        }
	        
	        
	    }
	    //current user array
	    //LOG.severe(currentPlayerArrayNewPlayers.toString());
	    response.setCurrentPlayers(currentNewPlayers);
	    String jsonStr = JSONUtils.objectToString(response);
	   // LOG.severe("jsonStr:"+jsonStr);
	  //  LOG.severe("getPlayerJson() BEFOR:"+this.gameManager.getPlayers().get(currentPlayerId).getPlayerJson());
	    TableManager.getInstance().getTable(_tableId).getPlayers().get(currentPlayerName).setPlayerJson(jsonStr);
	   // LOG.severe("getPlayerJson() AFTER:"+this.gameManager.getPlayers().get(currentPlayerId).getPlayerJson());
	    
	    
		return true;
	}
	
	public boolean ResponseDispatchePlayDone(String userName, int _tableId)
	{
		String currentPlayerName = userName;
		TableManager.getInstance().getTable(_tableId).getPlayers().get(currentPlayerName).setEvent(Interaction.PLAY_DONE);
		int newActivePlayer = TableManager.getInstance().getTable(_tableId).getNextActivePlayer(currentPlayerName);
		TableManager.getInstance().getTable(_tableId).setActiveplayerid(newActivePlayer);
		
		PlayerDTO currentPlayer = TableManager.getInstance().getTable(_tableId).getPlayers().get(currentPlayerName);
		GameLoginResponse response = new GameLoginResponse(currentPlayer, currentPlayer.getEvent());

		//build the other players json
		List<PlayerDTO> currentNewPlayers = new ArrayList<PlayerDTO>();
		List<PlayerDTO> currentPlayers = new ArrayList<PlayerDTO>();
    	currentPlayers.add(currentPlayer);
    	/*JSONArray currentPlayerArrayNewPlayers = new JSONArray();
    	JSONArray ArrayCurrentPlayers = new JSONArray();
    	ArrayCurrentPlayers.put(currentPlayerJsonObj2);*/
    	
    	Iterator<Entry<String, PlayerDTO>> it = TableManager.getInstance().getTable(_tableId).getPlayers().entrySet().iterator();
	    while (it.hasNext()) {
	    	@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
	        PlayerDTO playerIt = ((PlayerDTO)pair.getValue());
	         
	         
	        if(!currentPlayerName.equalsIgnoreCase(playerIt.getUserName()))
	        {
	        	
	        	//update each user 
	        	TableManager.getInstance().getTable(_tableId).getPlayers().get(playerIt.getId()).setEvent(Interaction.PLAY_DONE); 
	        	TableManager.getInstance().getTable(_tableId).setActiveplayerid(newActivePlayer);
	        	
	        	GameLoginResponse loginResponse = new GameLoginResponse(playerIt, Interaction.NEW_USER_LOGIN_DONE.getNum());
	        	currentNewPlayers.add(playerIt);
	        	loginResponse.setCurrentPlayers(currentPlayers);
	        	 
	        	//to others 
	        	String jsonStr = JSONUtils.objectToString(loginResponse);
	        	//LOG.severe("jsonStr in while:"+jsonStr);
	        	TableManager.getInstance().getTable(_tableId).getPlayers().get(playerIt.getId()).setPlayerJson(jsonStr);
	        	//LOG.severe("currentPlayerArrayNewPlayers:"+currentPlayerArrayNewPlayers.toString());
	        }
	        
	        
	    }
	    //current user array
	    //LOG.severe(currentPlayerArrayNewPlayers.toString());
	    response.setCurrentPlayers(currentNewPlayers);
	    //String cardInDeck = this.gameManager.getCardsPlayDeck().getFirst();
	    //currentPlayerJsonObj.put("deck", cardInDeck);
	    String jsonStr = JSONUtils.objectToString(response);
	   // LOG.severe("jsonStr:"+jsonStr);
	  //  LOG.severe("getPlayerJson() BEFOR:"+this.gameManager.getPlayers().get(currentPlayerId).getPlayerJson());
	    TableManager.getInstance().getTable(_tableId).getPlayers().get(currentPlayerName).setPlayerJson(jsonStr);
	   // LOG.severe("getPlayerJson() AFTER:"+this.gameManager.getPlayers().get(currentPlayerId).getPlayerJson());
	   
		return true;
	}
	
}
