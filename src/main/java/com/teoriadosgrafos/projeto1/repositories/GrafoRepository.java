package com.teoriadosgrafos.projeto1.repositories;

import com.teoriadosgrafos.projeto1.model.Grafo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrafoRepository extends JpaRepository<Grafo, Long> {

}
