package com.vistula.magazyn.repository;

import com.vistula.magazyn.domain.Produkt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data Crud HQL repository for the Produkt entity.
 */

@Repository
public interface ProduktCrudRepository extends CrudRepository<Produkt, Long> {
    @Query("SELECT p FROM Produkt p")
    public List<Produkt> findAllProdukts();
}
