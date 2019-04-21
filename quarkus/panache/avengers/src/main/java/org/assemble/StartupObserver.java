package org.assemble;

import io.quarkus.runtime.StartupEvent;
import org.assemble.service.AvengerNameGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

@ApplicationScoped
public class StartupObserver {
    
    @Transactional
    public void observeStart(@Observes StartupEvent event) {
        for (int i = 0; i < 10000; i++) {
            String name = AvengerNameGenerator.generateName();

            Avenger avenger = new Avenger();
            avenger.name = name;
            avenger.civilName = "Fake";
            avenger.snapped = true;

            avenger.persist();
        }
    }
}
