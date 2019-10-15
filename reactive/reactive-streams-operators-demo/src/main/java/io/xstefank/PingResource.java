package io.xstefank;

import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

@Path("/ping")
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
        ReactiveStreams.fromPublisher(createPublisher())
            .via(createProcessor())
            .to(createSubscriber())
            .run();
    }

    private Subscriber<String> createSubscriber() {
        return ReactiveStreams.<String>builder()
            .to(new Subscriber<String>() {
                private Subscription subscription;

                @Override
                public void onSubscribe(Subscription subscription) {
                    this.subscription = subscription;
                    System.out.println("PingResource.onSubscribe");
                    subscription.request(1);
                }

                @Override
                public void onNext(String s) {
                    System.out.println("s = " + s);
                    subscription.request(1);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println("throwable = " + throwable);
                }

                @Override
                public void onComplete() {
                    System.out.println("PingResource.onComplete");
                }
            })
            .build();
    }

    private Publisher<String> createPublisher() {
        List<String> strings = Arrays.asList("Luke", "Leia", "Han");

        return ReactiveStreams.fromIterable(strings)
            .map(String::toUpperCase)
            .buildRs();
            
    }
    
    private Processor<String, String> createProcessor() {
        return ReactiveStreams.<String>builder()
            .filter(s -> s.startsWith("L"))
            .map(s -> s.substring(0, 3))
            .buildRs();
    }
    
    
    
}
