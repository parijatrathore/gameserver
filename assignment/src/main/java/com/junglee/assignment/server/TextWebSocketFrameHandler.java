package com.junglee.assignment.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.redisson.config.Config;

import com.junglee.assignment.GameServerMain;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.entity.Player;
import com.junglee.assignment.event.Interaction;
import com.junglee.assignment.event.request.EventRequest;
import com.junglee.assignment.event.GameEventHandler;
import com.junglee.assignment.event.response.EventResponse;
import com.junglee.assignment.event.response.game.HandleGameEventResponse;
import com.junglee.assignment.event.response.game.HandshakeResponse;
import com.junglee.assignment.game.TableManager;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.utils.JSONUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

//@Sharable 
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private final static Logger	LOG	= LoggerManager.GetLogger(GameServerMain.class.getName());
	private GameEventHandler	gameEventHandler;

	public TextWebSocketFrameHandler(GameEventHandler _gameEventHandler) {
		this.gameEventHandler = _gameEventHandler;

	}

	public TextWebSocketFrameHandler() {
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

		if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {

			ctx.pipeline().remove(HttpRequestHandler.class);
			HandshakeResponse responseJson = new HandshakeResponse();
			responseJson.setEvent(Interaction.HANDSHAKE_COMPLETE_SUCCESS.getNum());
			LOG.severe("HANDSHAKE_COMPLETE " + JSONUtils.objectToString(responseJson));
			ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson.toString()));

		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		msg.retain();
		TextWebSocketFrame frame = (TextWebSocketFrame) msg;
		String jsonRequest = frame.text();
		EventRequest request = JSONUtils.stringToJson(jsonRequest, EventRequest.class);
		Interaction event = Interaction.valueOf(String.valueOf(request.getEvent().intValue()));
		LOG.severe("Recived from client :" + jsonRequest);
		try {
			HandleGameEventResponse response = this.gameEventHandler.handleEvent(jsonRequest, ctx.channel());
			if (response.isSuccessful()) {
				this.gameEventHandler.ResponseDispatcher(response.getUsername(), response.getTableId(), event);
				Iterator<Entry<String, PlayerDTO>> it = TableManager.getInstance().getTable(response.getTableId()).getPlayers().entrySet().iterator();
				while (it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry pair = (Map.Entry) it.next();
					PlayerDTO playerIt = ((PlayerDTO) pair.getValue());
					String PlayerMassage = playerIt.getPlayerJson().toString();
					responseToClient(playerIt, PlayerMassage);
					LOG.severe("Sending to client id:" + String.valueOf(playerIt.getId()) + " name:" + playerIt.getUserName() + " json:" + PlayerMassage);
				}
			} else {
				LOG.severe("Sending to clients Failed playerId is -1");
			}

		} finally {
			frame.release();
		}
	}

	private void responseToClient(PlayerDTO _player, String responseJson) {
		_player.getChannel().writeAndFlush(new TextWebSocketFrame(responseJson));
	}
}
