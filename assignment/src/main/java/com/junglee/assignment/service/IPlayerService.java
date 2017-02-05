package com.junglee.assignment.service;

import com.junglee.assignment.dto.PlayerDTO;

public interface IPlayerService {
	
	public PlayerDTO createPlayer(String username);
	
	public PlayerDTO getById(int playerId);

}
