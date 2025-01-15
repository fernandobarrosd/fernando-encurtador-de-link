package br.com.fernando.fernando_encurtador_de_link.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fernando.fernando_encurtador_de_link.entities.ShortLinkEntity;
import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLinkEntity, UUID>{
    Optional<ShortLinkEntity> findByShortLink(String shortLink);
}