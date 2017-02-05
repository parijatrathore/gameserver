package com.junglee.assignment.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class WebSocketServer {

	private final ChannelGroup		channelGroup	= new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
	private final EventLoopGroup	group			= new NioEventLoopGroup();

	private Channel					channel;

	public ChannelFuture start(int address) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(createInitializer());
		ChannelFuture future = bootstrap.bind(new InetSocketAddress(address));
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}

	protected ChannelInitializer<Channel> createInitializer() {
		return new GameServerInitializer();
	}

	public void destroy() {
		if (channel != null) {
			channel.close();
		}
		channelGroup.close();
		group.shutdownGracefully();
	}

}
