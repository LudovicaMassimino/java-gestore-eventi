package it.java.gestore.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String titolo;
    private LocalDate data;
    private int postiTotali;
    private int postiPrenotati;

    // costruttore
    public Evento(String titolo, LocalDate data, int postiTotali) {
        this.titolo = titolo;
        this.data = data;
        this.postiTotali = postiTotali;
        this.postiPrenotati = 0;

    }

    // getter e setter
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(
            String titolo) {
        this.titolo = titolo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getPostiTotali() {
        return postiTotali;
    }

    public int getPostiPrenotati() {
        return postiPrenotati;
    }

    /*
     * metodo controlloPrenota: se l’evento è già passato o non ha posti disponibili
     * deve restituire due messaggi di avviso.
     */

    public boolean controlloPrenota() {
        if (data.isBefore(LocalDate.now())) {
            System.out.println("L'evento è già passato");
            return false;
        } else if (postiTotali < postiPrenotati) {
            System.out.println("Non ci sono posti disponibili");
            return false;
        }
        return true;
    }

    /*
     * metodo controlloDisdici: se l’evento è già passato o non ci sono prenotazioni
     * restituisce un messaggio di avviso.
     */

    public boolean controlloDisdici() {
        if (data.isBefore(LocalDate.now())) {
            System.out.println("L'evento è già passato");
            return false;
        } else if (postiPrenotati <= 0) {
            System.out.println("Non hai effettuato prenotazioni da disdire");
            return false;
        }
        return true;
    }

    // metodo per prenotare
    public void prenota() {
        if (controlloPrenota()) {
            postiPrenotati++;
        } else {
            System.out.println("Errore: non ci sono abbastanza posti disponibili.");
        }
    }

    // metodo per disdire
    public void disdici() {
        if (controlloDisdici()) {
            postiPrenotati--;
        } else {
            System.out.println("Errore: non ci sono prenotazioni da disdire");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter) + " - " + titolo;
    }
}
