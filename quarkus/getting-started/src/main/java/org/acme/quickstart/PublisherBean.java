package org.acme.quickstart;

import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class PublisherBean {

    private AtomicInteger counter = new AtomicInteger();
    
    public Publisher<String> stream() {
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
            .map(i -> counter.incrementAndGet())
            .map(Object::toString);
    }
}
