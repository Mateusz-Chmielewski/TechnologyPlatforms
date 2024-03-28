package org.example.repositories;

import org.example.models.Mage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MageRepository extends JpaRepository<Mage, String> {
    @Query("select m from Mage m where m.level < ?1")
    public List<Mage> findMagesWeakerThan(int level);
}
