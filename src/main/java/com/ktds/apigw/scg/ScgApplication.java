package com.ktds.apigw.scg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@RestController
//@EnableConfigurationProperties(UriConfiguration.class)
@SpringBootApplication
public class ScgApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScgApplication.class, args);
    }

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
//
//        String httpUri = uriConfiguration.getHttpbin();
//
//        /**
//         * RouteLocatorBuilder : 경로 생성 또는 predicate(routing 정책)와 filter를 추가하여
//         *                       특정 조건에 따른 핸들을 라우팅하고 요청/응답을 원하는대로 변경할 수 있는 객체
//         *
//         * /get uri에서 요청이 들어왔을 때, http://httpbin.org:80으로 라우팅 전에 헤더에 Hello: World를 추가하는 필터 추가
//         */
//        return builder.routes()
//                .route(p -> p
//                        .path("/get")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri(httpUri))
//                .route(p -> p
//                        .host("*.circuitbreaker.com")
//                        .filters(f ->
//                                f.circuitBreaker(config ->
//                                        config
//                                                .setName("mycmd")
//                                                .setFallbackUri("forward:/fallback"))) //시간 초과 시 폴백 제공 가능
//                        .uri(httpUri))
//                .route(p -> p
//                        .path("/monoDefer")
//                        .uri(httpUri))
//                .build();
//    }
//
//    @RequestMapping("/fallback")
//    public Mono<String> fallback() {
//        return Mono.just("fallback");
//    }
//
//    @RequestMapping("/monoDefer")
//    public Mono<String> deferMthd() {
//        String str = null;
//        return Mono.just("QA");
//    }
}

//@ConfigurationProperties(prefix = "uri")
//class UriConfiguration {
//
//    private String httpbin = "http://httpbin.org:80";
//
//    public String getHttpbin() {
//        return httpbin;
//    }
//
//    public void setHttpbin(String httpbin) {
//        this.httpbin = httpbin;
//    }
//}
