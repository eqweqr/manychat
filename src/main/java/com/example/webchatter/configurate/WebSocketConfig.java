package com.example.webchatter.configurate;

import com.example.webchatter.sockets.SocketHandler;
import com.example.webchatter.sockets.TokenHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketHandler socketHandler;
    private final TokenHandshakeInterceptor handshakeInterceptor;

    public WebSocketConfig(SocketHandler socketHandler, TokenHandshakeInterceptor handshakeInterceptor) {
        this.socketHandler = socketHandler;
        this.handshakeInterceptor = handshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(socketHandler, "/web-socket")
                .addInterceptors(handshakeInterceptor);
               // .setOriginPatterns("*");
    }
}