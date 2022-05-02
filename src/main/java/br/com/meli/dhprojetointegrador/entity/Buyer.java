package br.com.meli.dhprojetointegrador.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "buyer")
public class Buyer implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String email;

    @ManyToMany
    @JoinTable(name = "favorites_products", joinColumns = @JoinColumn(name = "buyer_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Buyer> buyers;

}
