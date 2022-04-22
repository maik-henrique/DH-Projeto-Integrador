package br.com.meli.dhprojetointegrador.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BatchStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchNumber;

    private int currentQuantity;
    private LocalDate dueDate;
    private int initialQuantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private float currentTemperature;
}
