package com.junglee.assignment.event;

import java.io.IOException;
import java.util.logging.Logger;

import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.event.request.EventRequest;
import com.junglee.assignment.event.request.game.GameLoginRequest;
import com.junglee.assignment.event.response.game.HandleGameEventResponse;
import com.junglee.assignment.exception.InsuffcientTablePlayers;
import com.junglee.assignment.exception.TableCreateException;
import com.junglee.assignment.exception.TableNotAvailabelException;
import com.junglee.assignment.exception.TableNotFoundException;
import com.junglee.assignment.game.PlayerManager;
import com.junglee.assignment.game.TableManager;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.utils.JSONUtils;

import io.netty.channel.Channel;

//this class will handle all the request / response logic and game protocol 
public class GameEventHandler {
	private final static Logger			LOG							= LoggerManager.GetLogger(GameEventHandler.class.getName());

	public GameEventHandler() {

	}

	public HandleGameEventResponse handleEvent(String requestJson, Channel channel) throws IOException, TableCreateException, TableNotFoundException, TableNotAvailabelException, InsuffcientTablePlayers {
		EventRequest request = JSONUtils.stringToJson(requestJson, EventRequest.class);
		Interaction event = Interaction.valueOf(String.valueOf(request.getEvent().intValue()));
		HandleGameEventResponse response = new HandleGameEventResponse();
		switch (event) {
		case PLAY: {
			GameLoginRequest gameLoginRequest = JSONUtils.stringToJson(requestJson, GameLoginRequest.class);
			String userName = ((GameLoginRequest) request).getUsername();
			PlayerDTO newPlayer = PlayerManager.getInstance().givePlayer(userName, channel);
			//        		PlayerDTO newPlayer = setPlayerNewAttributes(userName,channel,Event.LOGIN_DONE);
			// request game manager to get this a table
			TableDTO tableDTO = TableManager.getInstance().joinTable(newPlayer);
			response.setUsername(newPlayer.getUserName());
			response.setTableId(tableDTO.getTableId());
			response.setSuccessful(true);
			break;
		}
		case LEAVE: {
			// implement game leave request
		}
		}

		return response;
	}

	public boolean ResponseDispatcher(String userName, int tableId, Interaction event) {
		boolean bDone = false;
		switch (event) {
		case LOGIN: {
			bDone = TableManager.getInstance().getGameResponseDispatcher().ResponseDispatcheLoginDone(userName, tableId);
			break;
		}
		case PLAY: {
			bDone = TableManager.getInstance().getGameResponseDispatcher().ResponseDispatchePlayDone(userName, tableId);
			break;
		}
		}

		return bDone;
	}

}
