package com.junglee.assignment.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;

public class CloseWebSocketFrameHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame msg) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
