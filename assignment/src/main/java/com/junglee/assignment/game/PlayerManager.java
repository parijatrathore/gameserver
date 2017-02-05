package com.junglee.assignment.game;

import java.io.IOException;
import java.util.logging.Logger;

import com.junglee.assignment.GameServerMain;
import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.service.IPlayerService;
import com.junglee.assignment.service.impl.PlayerService;

import io.netty.channel.Channel;

public final class PlayerManager {
	
	private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());
	
	private static final PlayerManager _instance = new PlayerManager();
	private final IPlayerService playerService;
	
	private PlayerManager(){
		playerService = new PlayerService();
	}

	public static PlayerManager getInstance(){
		return _instance;
	}
	
	public PlayerDTO givePlayer(String username, Channel channel) throws IOException{
		PlayerDTO dto = (PlayerDTO) JungleeRedis.getInstance().get(username);
		if(dto == null){
			dto = playerService.createPlayer(username);
			dto.setChannel(channel);
			JungleeRedis.getInstance().cacheObject(username, dto);
		}
		return dto;
	}
}
