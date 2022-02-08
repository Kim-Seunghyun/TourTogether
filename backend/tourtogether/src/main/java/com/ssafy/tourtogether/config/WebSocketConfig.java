package com.ssafy.tourtogether.config;

import com.ssafy.tourtogether.component.WebSocketComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketComponent webSocketComponent;

    public WebSocketConfig(WebSocketComponent webSocketComponent) {
        this.webSocketComponent = webSocketComponent;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketComponent, "/memo3")
	        .setAllowedOriginPatterns("*")
	        .withSockJS()
	        .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
        webSocketHandlerRegistry.addHandler(webSocketComponent, "/schedule")
	        .setAllowedOriginPatterns("*")
	        .withSockJS()
	        .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
        webSocketHandlerRegistry.addHandler(webSocketComponent, "/sch2")
	        .setAllowedOriginPatterns("*")
	        .withSockJS()
	        .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
    }
}