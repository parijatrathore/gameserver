package com.junglee.assignment.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.junglee.assignment.dao.PlayerDAOcheck;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.entity.Player;
import com.junglee.assignment.service.IPlayerService;

public class PlayerService implements IPlayerService {
	
	private static AtomicInteger playerIdCounter = new AtomicInteger(0);

	@Override
	public PlayerDTO createPlayer(String username) {
//		Player player = new Player(username);
//		PlayerDAOcheck.getInstance().saveOrUpdate(player);
		PlayerDTO dto = new PlayerDTO(GenerateUniqueId(), username);
		return dto;
	}

	@Override
	public PlayerDTO getById(int playerId) {
		return new PlayerDTO(PlayerDAOcheck.getInstance().getById(playerId));
	}
	
	private int GenerateUniqueId()
	{
		return playerIdCounter.incrementAndGet();
	}

}
