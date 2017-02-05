package com.junglee.assignment.server;
import com.junglee.assignment.event.GameEventHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class GameServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("HttpObjectAggregator",new HttpObjectAggregator(64 * 1024));
        pipeline.addLast("ChunkedWriteHandler",new ChunkedWriteHandler());
        pipeline.addLast("WebSocketServerProtocolHandler",new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("TextWebSocketFrameHandler",new TextWebSocketFrameHandler(new GameEventHandler()));
        												    
        
    }
}
