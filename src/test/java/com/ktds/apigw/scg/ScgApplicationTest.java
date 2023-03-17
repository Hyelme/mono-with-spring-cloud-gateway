package com.ktds.apigw.scg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
class ScgApplicationTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void contextLoads() throws Exception {


//        //flux test
//        Mono<String> flux = Mono.just("A").log();
//
//
//
//        //누가 구독할 것인가?
//        flux.subscribe(s->{
//            System.out.println();
//
//        });
//
//        Mono<String> flux2 = flux.map(i -> "foo"+i );
//        flux2.subscribe(System.out::println);
//
//
//
//
//
//        //subscribe -> Mono를 구독하고 결과 값을 처리할 Subscriber를 등록하는 예시.
//
//        Mono<String> iamMono = Mono.justOrEmpty(null);
//        Mono<Integer> iamMono2 = Mono.justOrEmpty(null);
//
//        iamMono.just("Hello")
//                .map(s -> s + " World").doOnNext(System.out::println).then(iamMono2.just(3))
//                // .then(s -> Mono.just(s.toUpperCase())) then : 다른 publisher를 구독하고싶을때 이어서
//                .subscribe(ScgApplication::goodJob);
//                //System.out::println
//
//        //의문을 가져야 할점: 여러 Mono, publisher들이 어디서 어떻게 사용이 되는지? 우리 상황에 맞춰서 알아야 한다.
//        //왜 publisher가 여러개여야 하는지?
//
//        List<String> goodStrings = new ArrayList<>();
//
//
//        goodStrings.stream().map(i->i+"foo").collect(Collectors.toList()).stream();
//
//
//        //실험실 defer & just
//        Mono<String> goodMono = Mono.just("hold");
//
//        goodMono.subscribe(System.out::println);
//
//
//        //subscribe가 구독하고 나서 한다. (defer -> 혹시 모를 블로킹)
//        Mono<String> deferMono = Mono.defer(() -> {
//
//            System.out.println("hey!");
//            try {
//                System.out.println("과연11111");
//                System.out.println(Thread.currentThread().getName());
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            return Mono.just("////////////////////////////////////////////////////////////////////////////////////");
//
//        });
//
//
//
//        System.out.println("1111");
//
//        deferMono.subscribe(System.out::println);
//
////        while(true){
////            System.out.println("과연");
////            Thread.sleep(1000);
////            System.out.println(Thread.currentThread().getName());
////            System.out.println();
////
////            deferMono.subscribe(System.out::println);
////        }
//
//         Mono.just("gggg");
//
//
//
//
//
//
//         System.out.println("keep going On");
//
//
//         for(int i=0; i<10000; i++){
//         	System.out.println();
//         }
//
//
//
//
//
//         //block -> Mono의 결과 값을 동기적으로 반환하는 예시. Mono가 완료될 때까지 블로킹합니다.
//         Mono<Integer> mono = Mono.just(1);
//         Mono<String> mappedMono = mono.map(num -> "Number is " + num);
//         mappedMono.subscribe(System.out::println); // 출력: Number is 1
//
//
//         //map ->Mono가 방출하는 결과 값을 Function 인터페이스를 구현한 객체로 변환하는 예시입니다. Mono가 방출하는 정수 1을 "Number is 1"로 변환한 후, 출력합니다.
//         mono = Mono.just(3);
//         mappedMono = mono.map(num -> "Number is " + num);
//         mappedMono.subscribe(System.out::println); // 출력: Number is 1
//
//         // //flatMap -> Mono가 방출하는 결과 값을 Publisher로 변환한 후, 이를 구독하여 결과 값을 방출하는 Flux로 반환하는 예시입니다. Mono가 방출하는 정수 1을 Flux.range(1, 1)로 변환한 후, Flux가 방출하는 1을 출력합니다.
//         // Flux.just(1)
//         // 	.flatMap(num -> Mono.range(1, num))
//         // 	.subscribe(System.out::println); // 출력: 1
//
//
//
//         //defaultIfEmpty(T defaultValue) -> Mono가 완료되었을 때 방출한 결과 값이 없으면, 지정한 기본 값으로 결과 값을 대체하는 예시입니다. Mono.empty()는 결과 값을 방출하지 않으므로, defaultIfEmpty(1)을 호출하여 기본 값 1을 방출하고 출력합니다.
//         //Mono<Void> monoEm = Mono.empty();
//         Mono.empty()
//         	.defaultIfEmpty(1)
//         	.subscribe(System.out::println); // 출력: 1
//
//
//
//
//
//         //switchIfEmpty(Mono<? extends T> other) -> Mono가 완료되었을 때 방출한 결과 값이 없으면, 지정한 다른 Mono로 대체하는 예시입니다. Mono.empty()는 결과 값을 방출하지 않으므로, switchIfEmpty(Mono.just(1))을 호출하여 다른 Mono인 Mono.just(1)을 구독하고 결과 값을 방출합니다.
//         Mono.empty()
//         	.switchIfEmpty(Mono.just(1))
//         	.subscribe(System.out::println); // 출력: 1
//
//
//         //onErrorResume(Function<? super Throwable, ? extends Mono<? extends T>> fallback) -> Mono에서 에러가 발생했을 때, 다른 Mono를 반환하여 결과 값을 대체하는 예시입니다. Mono.error(new RuntimeException("error"))는 에러를 방출하는 Mono이므로, onErrorResume(e -> Mono.just(1))을 호출하여 에러가 발생했을 때 다른 Mono인 Mono.just(1)을 구독하고 결과 값을 방출합니다.
//         Mono.error(new RuntimeException("error"))
//         	.onErrorResume(e -> Mono.just(1))
//         	.subscribe(System.out::println); // 출력: 1
//
//
//         //
//         Mono.just("hello")
//         	.doOnNext(s -> System.out.println("doOnNext: " + s))
//         	.doOnError(e -> System.out.println("doOnError: " + e.getMessage()))
//         	.doOnSuccess(s -> System.out.println("doOnSuccess: " + s))
//         	.subscribe(System.out::println);
//
//
////        SpringApplication.run(ScgApplication.class, args);
//    }
//
//    private static void goodJob(Integer integer) {
//    }
    }
}
