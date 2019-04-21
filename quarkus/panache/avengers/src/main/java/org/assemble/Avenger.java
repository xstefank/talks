package org.assemble;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Avenger extends PanacheEntity {
    
    public String name;
    
    @Column(name = "real_name")
    public String civilName;
    
    public boolean snapped;

    public String getCivilName() {
        return civilName.toUpperCase();
    }

    public static PanacheQuery<Avenger> searchByName(String searchValue) {
        return Avenger.find("name like :search or real_name like :search", 
            Parameters.with("search", "%" + searchValue + "%"));
    }

    public void setCivilName(String civilName) {
        this.civilName = civilName.toLowerCase();
    }
    
    public static List<Avenger> findUnsnapped() {
        return list("snapped", false);
    }
}
