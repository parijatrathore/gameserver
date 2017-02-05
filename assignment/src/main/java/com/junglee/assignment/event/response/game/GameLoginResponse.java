package com.junglee.assignment.event.response.game;

import java.util.List;

import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.event.response.EventResponse;

public class GameLoginResponse extends EventResponse {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7464679056508052942L;

	private PlayerDTO			playerDTO;
	private int					event;
//	private List<PlayerDTO>		currentNewPlayers;
	private List<PlayerDTO>		currentPlayers;

	public GameLoginResponse() {

	}

	public GameLoginResponse(PlayerDTO playerDTO, int event) {
		this.playerDTO = playerDTO;
		this.event = event;
	}

	public PlayerDTO getPlayerDTO() {
		return playerDTO;
	}

	public void setPlayerDTO(PlayerDTO playerDTO) {
		this.playerDTO = playerDTO;
	}

	/*public List<PlayerDTO> getCurrentNewPlayers() {
		return currentNewPlayers;
	}

	public void setCurrentNewPlayers(List<PlayerDTO> currentNewPlayers) {
		this.currentNewPlayers = currentNewPlayers;
	}*/

	public List<PlayerDTO> getCurrentPlayers() {
		return currentPlayers;
	}

	public void setCurrentPlayers(List<PlayerDTO> currentPlayers) {
		this.currentPlayers = currentPlayers;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

}
