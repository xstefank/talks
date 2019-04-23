package org.acme.quickstart;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return message;
    }
    
    @GET
    @Path("/planet")
    public List<Planet> getPlanets() {
        return Arrays.asList(new Planet("Tatooine", 10200, Climate.DRY),
            new Planet("Hoth", 12200, Climate.POLAR));
    }
    
    @GET
    @Path("/async")
    public CompletionStage<String> async() {
        return ReactiveStreams.of(message.split("\\s"))
            .map(String::toUpperCase)
            .distinct()
            .collect(Collectors.toList())
            .run()
            .thenApply(Object::toString);
    }
    
    @GET
    @Path("/async2")
    public CompletionStage<String> async2() {
        CompletableFuture<String> result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "hello";
        });

        System.out.println("hi");
        System.out.println("hi");
        System.out.println("hi");
        System.out.println("hi");
        System.out.println("hi");
        
        return result;
    }
    
    @Inject
    PublisherBean publisherBean;
    
    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> stream() {
        return publisherBean.stream();
            
    }
}
