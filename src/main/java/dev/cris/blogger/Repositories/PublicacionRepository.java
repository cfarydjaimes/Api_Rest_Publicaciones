package dev.cris.blogger.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.cris.blogger.Models.Publicacion;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

}
