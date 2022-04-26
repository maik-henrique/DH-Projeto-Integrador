package br.com.meli.dhprojetointegrador.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private float capacity;

    @ManyToOne
    @JoinColumn(name = "warehouse", nullable = false)
    private Warehouse warehouse;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
