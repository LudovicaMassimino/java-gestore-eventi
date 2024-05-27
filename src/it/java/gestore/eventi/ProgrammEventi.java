package it.java.gestore.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProgrammEventi {
    private String titolo;
    private List<Evento> eventi;

    // Costruttore
    public ProgrammEventi(String titolo) {
        this.titolo = titolo;
        this.eventi = new ArrayList<>();
        // creazione eventi fissi aggiunti al programma
        Evento evento1 = new Evento("Coldplay. X&Y", LocalDate.now(), 60000);
        aggiungiEvento(evento1);
        Evento evento2 = new Evento("Teresa Mannino. Sono nata il ventitré.", LocalDate.now().plusDays(30), 1000);
        aggiungiEvento(evento2);
        Evento evento3 = new Evento("Tedx", LocalDate.now().plusDays(100), 3000);
        aggiungiEvento(evento3);
        Evento evento4 = new Evento("Ludovico Einaudi", LocalDate.now().plusDays(200),
                30000);
        aggiungiEvento(evento4);
        Evento evento5 = new Evento("Apologia di Socrate", LocalDate.now().plusDays(300), 1000);
        aggiungiEvento(evento5);
        Evento evento6 = new Evento("Conferenza sulla termodinamica", LocalDate.now().plusDays(600), 3000);
        aggiungiEvento(evento6);

    }

    // Metodo per aggiungere un evento alla lista
    public void aggiungiEvento(Evento myEvento) {
        eventi.add(myEvento);
    }

    // Metodo che restituisce una lista di eventi presenti in una certa data
    public List<Evento> getEventiInData(LocalDate data) {
        List<Evento> eventiInData = new ArrayList<>();
        for (Evento myEvento : eventi) {
            if (myEvento.getData().equals(data)) {
                eventiInData.add(myEvento);
            }
        }
        return eventiInData;
    }

    // Metodo che restituisce quanti eventi sono presenti nel programma
    public int getNumeroEventi() {
        return eventi.size();
    }

    // Metodo che svuota la lista di eventi
    public void svuotaEventi() {
        eventi.clear();
    }

    // Metodo che restituisce una stringa con il titolo del programma e tutti gli
    // eventi ordinati per data
    public String getEventiOrdinatiPerData() {
        // Ordina gli eventi per data
        Collections.sort(eventi, new Comparator<Evento>() {
            @Override
            public int compare(Evento e1, Evento e2) {
                return e1.getData().compareTo(e2.getData());
            }
        });

        // Costruisci la stringa con tutti gli eventi
        String risultato = titolo + "   \n";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        /*
         * uso il foreach,
         * sta per for(int = 0; i < eventi.size(); i++)
         * Evento evento = eventi.get(i);
         */
        for (Evento myEvento : eventi) {
            risultato = String.format("%s   ➤ %s - %s\n", risultato, myEvento.getData().format(formatter),
                    myEvento.getTitolo());
        }

        return risultato;
    }
}
