package it.java.gestore.eventi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Concerto extends Evento {
    private LocalTime ora;
    private double prezzo;

    // construttore
    public Concerto(String titolo, LocalDate data, LocalTime ora, int postiTotali, double prezzo) {
        super(titolo, data, postiTotali);
        this.ora = ora;
        this.prezzo = prezzo;
    }

    // getter e setter
    public LocalTime getOra() {
        return ora;
    }

    public void setOra(LocalTime ora) {
        this.ora = ora;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    // metodo per formattare data e ora
    public String getDataOraFormattata() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return getData().format(dateFormatter) + " - " + ora.format(timeFormatter);
    }

    // metodo per formattare il prezzo
    public String getPrezzoFormattato() {
        return String.format(Locale.ITALY, "%.2f€", prezzo);
    }

    // override del metodo toString
    @Override
    public String toString() {
        return getDataOraFormattata() + " - " + "\033[1;94m\"" + getTitolo() + "\"\033[0m" + " - "
                + getPrezzoFormattato();
    }
}
