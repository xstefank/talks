package io.xstefank;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@Path("/ping")
@ApplicationScoped
public class PingResource {

    @GET
    @Path("rs1")
    public void getRs() {
        CompletionStage<List<String>> cs = ReactiveStreams.of("Luke", "Leia", "Han")
            .map(String::toUpperCase)
            .filter(s -> s.startsWith("L"))
            .limit(1)
            .toList()
            .run();

        cs.thenAccept(System.out::println);
    }
    
    @GET
    @Path("rs2")
    public void getRs2() {
        ReactiveStreams.fromPublisher(publisher())
            .via(processor())
            .to(subscriber())
            .run();
    }

    private Processor<Long, String> processor() {
        return ReactiveStreams.<Long>builder()
            .filter(aLong -> aLong % 2 == 0)
            .map(aLong -> aLong + " iteration")
            .buildRs();
    }

    private Subscriber<? super String> subscriber() {
        return new Subscriber<String>() {
            private Subscription subscribtion;

            @Override
            public void onSubscribe(Subscription s) {
                this.subscribtion = s;
                subscribtion.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("s = " + s);
                subscribtion.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("t = " + t);
            }

            @Override
            public void onComplete() {
                System.out.println("PingResource.onComplete");
            }
        };
    }

    private Publisher<Long> publisher() {
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
//            .map(aLong -> aLong / 0)
            .limit(10);
    }
    
    
    @Outgoing("ticks")
    public Publisher<Long> producer() {
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
//            .map(aLong -> aLong / 0)
            .limit(10);
    }

    @Incoming("ticks")
    @Outgoing("values")
    public String processor(Long tick) {
        return tick + "processing iteration";
    }

    @Incoming("values")
    public void consumer(String value) {
        System.out.println("value = " + value);
    }

}
