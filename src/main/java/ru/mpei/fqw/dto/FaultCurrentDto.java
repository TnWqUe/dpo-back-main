package ru.mpei.fqw.dto;

import lombok.Data;


@Data
public class FaultCurrentDto {
    private String name;
    private Double value;
    private Double time;
    public FaultCurrentDto(String name, Double value, Double time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }
}
