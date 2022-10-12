package application.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "review", name = "bog")
@Builder
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bog {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "titel", nullable = false)
    private String titel;

    @Column(name = "forfatter", nullable = false)
    private String forfatter; // TODO own entity

    @Column(name = "blurb", nullable = false)
    private String blurb;
}
