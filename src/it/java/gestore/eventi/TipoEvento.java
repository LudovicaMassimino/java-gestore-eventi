package it.java.gestore.eventi;

import java.util.ArrayList;
import java.util.List;

public class TipoEvento {
    private List<String> tipoEventoArray;

    public TipoEvento() {
        tipoEventoArray = new ArrayList<>();
        tipoEventoArray.add("Concerto");
        tipoEventoArray.add("Spettacolo");
        tipoEventoArray.add("Conferenza");
    }

    public List<String> getTipoEventoArray() {
        return tipoEventoArray;
    }
}
