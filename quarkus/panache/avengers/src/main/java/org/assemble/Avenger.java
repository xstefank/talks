package org.assemble;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Avenger extends PanacheEntity {
    
    public String name;
    
    @Column(name = "real_life_name")
    public String civilName;
    public boolean snapped;
    
}
