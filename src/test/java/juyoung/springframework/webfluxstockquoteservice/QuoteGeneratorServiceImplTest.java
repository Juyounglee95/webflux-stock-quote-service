package juyoung.springframework.webfluxstockquoteservice;

import juyoung.springframework.webfluxstockquoteservice.model.Quote;
import juyoung.springframework.webfluxstockquoteservice.service.QuoteGeneratorServiceImpl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

public class QuoteGeneratorServiceImplTest {

    QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();


    public void setUp() throws Exception{

    }

    @Test
    public void fetchQuoteStream() throws Exception{

        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(1L));
        quoteFlux.take(22000)
                .subscribe(System.out::println);
//        Thread.sleep(1000); This is not good way
//        Thread.sleep(1000);
//        Thread.sleep(1000);
//        Thread.sleep(1000);
    }

    @Test
    public void fetchQuoteStreamCountDown() throws Exception{

        Flux<Quote> quoteFlux =quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        Consumer<Quote> println = System.out::println;

        Consumer<Throwable> errorHandler = e -> System.out.println("Some Error Occurred");

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable allDone = ()-> countDownLatch.countDown();
        quoteFlux.take(10)
                .subscribe(println, errorHandler, allDone);
        countDownLatch.await();
    }
}
