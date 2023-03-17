package com.ktds.apigw.scg.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
public class CustomFilter3 implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 구독과 상관없이 가장 먼저 생성될 줄 알았는데, 같은 filter 내에서만 순서 상관 있는 듯
         */
        Mono<Boolean> sMono1 = Mono.defer(() -> {
            boolean isHttpProt = Boolean.TRUE.equals(exchange.getAttribute("isHttpProt"));

            if (!isHttpProt) {
                String reqMethod = exchange.getRequest().getMethodValue();
                isHttpProt = HttpMethod.GET.name().equals(reqMethod);

                exchange.getAttributes().put("isHttpProt", isHttpProt);
                log.info("is http protocol? ==> {}", isHttpProt);
            }
            return Mono.just(isHttpProt);
        }).log()
                .doFirst(() -> log.info("Mono.defer operator"));

        Mono<String> sMono2 = Mono.justOrEmpty("Custom Filter3").log()
                .doFirst(() -> log.info("Mono.just operator"));


        //exchage에 MONO 객체 태우기 -> 가능
        Mono< Date> startDt = exchange.getAttribute("supplier");

        System.out.println("-------------------------------------------------");

        return sMono1.then(sMono2).then(startDt).then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}
