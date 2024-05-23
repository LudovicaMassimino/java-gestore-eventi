package java.gestore.eventi.utente;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SpettacoloUtente extends EventoUtente {
    private LocalTime ora;
    private double prezzo;

    // costruttore
    public SpettacoloUtente() {
        super("Spettacolo di Teresa Mannino - Padova", LocalDate.of(2025, 10, 13), 2500);
        this.ora = LocalTime.of(21, 00);
        this.prezzo = 37.00;
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

        return getData().format(dateFormatter) + " " + ora.format(timeFormatter);
    }

    // metodo per formattare il prezzo
    public String getPrezzoFormattato() {
        return String.format(Locale.ITALY, "%.2f€", prezzo);
    }

    // override del metodo toString
    @Override
    public String toString() {
        return getDataOraFormattata() + " - " + getTitolo() + " - " + getPrezzoFormattato();
    }
}
