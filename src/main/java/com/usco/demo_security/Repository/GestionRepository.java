package com.usco.demo_security.Repository;

import com.usco.demo_security.models.Gestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionRepository extends JpaRepository<Gestion, Long> {
}
