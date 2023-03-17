package com.ktds.apigw.scg.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
public class CustomFilter1 implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 첫 번째 필터 기능 :
         * 최초 구독 시작 시간 기록하기
         */

        Mono<Date> startDt = Mono.fromSupplier(() -> {
            Date now = new Date();
            log.info("now date : {}", now);
            return now;
        }).log()
                .doOnSubscribe(request -> log.info("from supplier in Custom Filter1 start"))
                .doOnSuccess(request -> log.info("---------- from supplier in Custom Filter1 Finish ----------"));

        Mono<String> strMono = Mono.just("A").log()
                .doOnSubscribe(request -> log.info("Custom Filter1 start"))
                .doOnSuccess(request -> log.info("---------- Custom Filter1 Finish ----------"));

        exchange.getAttributes().put("supplier", startDt);

        return strMono.then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
