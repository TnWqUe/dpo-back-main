package ru.mpei.fqw.utils;


import lombok.Data;

import java.util.List;

@Data
public class ComtradeToJson {
    private String name;
    private String type;
    private List<?> values;
    private List<?> times;
    private boolean clicked = false;
    private List<Double> RMS;
}
