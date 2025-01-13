package br.com.fernando.fernando_encurtador_de_link.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;

public interface ShortLinkRepository extends JpaRepository<ShortLinkEntity, UUID>{
    
}