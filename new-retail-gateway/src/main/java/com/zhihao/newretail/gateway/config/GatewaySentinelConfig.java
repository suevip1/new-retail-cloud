package com.zhihao.newretail.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.annotation.PostConstruct;

@Configuration
public class GatewaySentinelConfig {

    @PostConstruct
    private void initBlockRequestHandler() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            JSONObject response = new JSONObject();
            response.put("code", HttpStatus.SC_TOO_MANY_REQUESTS);
            response.put("msg", "当前访问人数过多，请稍后重试");
            return ServerResponse
                    .status(HttpStatus.SC_TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(response));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
