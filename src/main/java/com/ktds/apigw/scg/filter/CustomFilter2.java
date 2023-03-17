package com.ktds.apigw.scg.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
public class CustomFilter2 implements GlobalFilter, Ordered {

    private static final String TIMEOUT_HEADER = "timeout";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * 두 번째 필터 기능 :
         * 지연 발생 시, header에 timeout 추가
         */

        Mono<Long> vMono = Mono.delay(Duration.ofSeconds(6)).log()
                .timeout(Duration.ofSeconds(3))
                /**
                 * 1. doOnError() : 에러가 발생했을 때, 추가적인 작업을 수행할 수 있도록 합니다.
                 *                  하지만, 에러를 처리하는 것은 아니기 때문에 이 연산자를 사용한 후에도 에러는 여전히 구독자에게 전달됩니다.
                 * 2. onErrorReturn() : 에러가 발생하면 지정된 값을 emit하여 이를 구독자에게 전달합니다.
                 *                      이 연산자는 에러 발생 시 값을 반환하기 때문에, 반환값의 타입은 Mono<T>가 아닌 Mono<? extends T>입니다.
                 * 3. onErrorResume() : 에러가 발생하면 새로운 Mono나 Flux를 생성하여 이를 구독자에게 전달합니다.
                 *                      즉, 에러가 발생하더라도 구독자는 계속해서 스트림을 받을 수 있습니다.
                 *                      onErrorResume의 인자로는 Function<Throwable, Mono<? extends T>>를 받습니다.
                 * 4. onErrorResumeWith() : 에러가 발생하면 새로운 Mono나 Flux를 생성하여 이를 구독자에게 전달합니다.
                 *                          이 때, onErrorResumeWith의 인자로는 Throwable을 인자로 받는 Function<Throwable, Publisher<? extends T>>를 받습니다.
                 * 4. onErrorContinue() : 에러가 발생하더라도 skip하고 계속해서 데이터를 전달합니다.
                 *                        onErrorContinue의 인자로는 BiConsumer<? super T, ? super Throwable>를 받습니다.
                 * 5. onErrorMap() : 에러가 발생하면 Function<Throwable, ? extends Throwable> 함수를 실행시켜 에러를 변환합니다.
                 *                   이렇게 변환한 에러를 다시 발생시키며, 이 에러를 구독자에게 전달합니다. 이를 통해 에러 처리를 좀 더 세밀하게 조작할 수 있습니다.
                 */
                .onErrorResume(throwable -> {
                    HttpHeaders editrequestHeaders = new HttpHeaders();
                    editrequestHeaders.addAll(exchange.getRequest().getHeaders());
                    log.info("요청 헤더 가공 전 : {}, {}", editrequestHeaders, editrequestHeaders.size());

                    editrequestHeaders.add(TIMEOUT_HEADER, throwable.getMessage());
                    exchange.mutate().request(r -> r.headers(h -> {
                        h.clear();
                        h.addAll(editrequestHeaders);
                    }));
                    log.info("요청 헤더 가공 후 : {}", exchange.getRequest().getHeaders());
                    return Mono.empty();
                });

        return vMono.then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
