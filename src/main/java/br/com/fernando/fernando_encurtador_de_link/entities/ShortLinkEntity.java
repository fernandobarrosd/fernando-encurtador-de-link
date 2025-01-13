package br.com.fernando.fernando_encurtador_de_link.entities;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "short_link_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShortLinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "original_link", nullable = false)
    private String originalLink;

    @Column(name = "short_link", nullable = false, unique = true)
    private String shortLink;


    public ShortLinkEntity(String originalLink, String shortLink) {
        this(null, originalLink, shortLink);
    }

    @Override
    public String toString() {
        return "ShortLinkEntity(id=%s, originalLink=%s, shortLink=%s)"
        .formatted(id.toString(), originalLink, shortLink);
    }
}