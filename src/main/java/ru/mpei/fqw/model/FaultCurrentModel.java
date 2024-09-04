package ru.mpei.fqw.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fault_current_info")
@Data
@NoArgsConstructor
public class FaultCurrentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Double value;

    @Column
    private Double time;

    public FaultCurrentModel(String name, Double value, Double time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }
}
