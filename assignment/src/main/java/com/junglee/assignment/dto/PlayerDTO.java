package com.junglee.assignment.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.junglee.assignment.GameServerMain;
import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.entity.Player;
import com.junglee.assignment.event.Interaction;
import com.junglee.assignment.logger.LoggerManager;

import io.netty.channel.Channel;

public class PlayerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 365669937582390922L;

	private final static Logger	LOG					= LoggerManager.GetLogger(GameServerMain.class.getName());

	private int					id;
	private String				userName;
	private Interaction				event;
	private Channel				channel;
	private String				playerJson;
	private AtomicInteger		gamesPlayed;

	public PlayerDTO() {
		this.channel = null;
	}

	public PlayerDTO(Channel _channel) {
		this.channel = _channel;
	}

	public PlayerDTO(Player player) {
		this.id = player.getId();
		this.userName = player.getName();
	}
	
	public PlayerDTO(int id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public String getPlayerJson() {
		return playerJson;
	}

	public void setPlayerJson(String playerJson) {
		this.playerJson = "";
		this.playerJson = playerJson;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Channel getChannel() {
		return channel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getEvent() {
		return event.getNum();
	}

	public void setEvent(Interaction event) {
		this.event = event;
	}

	public int getId() {
		return id;
	}

	public int getGamesPlayed() {
		return gamesPlayed.get();
	}

	public void setGamesPlayed() {
		this.gamesPlayed.incrementAndGet();
		try {
			JungleeRedis.getInstance().cacheObject(this.userName, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
