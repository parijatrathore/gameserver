package com.junglee.assignment.game;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.event.response.game.GameStatusResponse;
import com.junglee.assignment.utils.JSONUtils;
import com.junglee.assignment.utils.Properties;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * Class asked to be implemented which has method send packet to communicate with the player
 * @author parijatrathore
 *
 */
public class ClientConnection {
	
	private ThreadPoolExecutor			executor;
	private static final ClientConnection	_instance	= new ClientConnection();
	
	private ClientConnection() {
		executor = new ThreadPoolExecutor(Properties.MIN_PLAYER_GAME_START, Properties.MAX_PLAYER_TABLE, 120, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
	
	public static ClientConnection getInstance() {
		return _instance;
	}
	
	private class MessagePlayer implements Runnable {

		private PlayerDTO			playerDTO;
		private GameStatusResponse	message;

		public MessagePlayer(String username, GameStatusResponse message) {
			this.playerDTO = (PlayerDTO) JungleeRedis.getInstance().get(username);
			this.message = message;
		}

		public MessagePlayer(PlayerDTO dto, GameStatusResponse message) {
			this.playerDTO = dto;
			this.message = message;
		}

		public void run() {
			this.playerDTO.getChannel().writeAndFlush(new TextWebSocketFrame(JSONUtils.objectToString(message)));
		}
	}
	
	public void sendPacket(PlayerDTO dto, GameStatusResponse response){
		executor.submit(new MessagePlayer(dto, response));
	}
	
	public void sendPacket(String username, GameStatusResponse response){
		executor.submit(new MessagePlayer(username, response));
	}

}
