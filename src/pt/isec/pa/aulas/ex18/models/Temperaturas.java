package pt.isec.pa.aulas.ex18.models;

import java.util.HashSet;

public class Temperaturas {
    private HashSet<TemperaturaDiaria> temps = new HashSet<>();
    public void acrescenta(TemperaturaDiaria td) {
        if (temps.contains(td)) {
            System.out.println("Já está registado um dia" +
                    " com máximos e mínimos similares");
        } else {
            System.out.println("Dia com novos máximos e mínimos");
            temps.add(td);
        }
    }
}
