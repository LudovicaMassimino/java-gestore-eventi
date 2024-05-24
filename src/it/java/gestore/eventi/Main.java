package it.java.gestore.eventi;

import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n\n\n       CREA IL TUO EVENTO");
        // chiedo all'utente che tipo di evento vuole creare
        TipoEvento myTipoEvento = new TipoEvento();
        List<String> tipoEventoArray = myTipoEvento.getTipoEventoArray();
        System.out.println(
                "\nQuale tipo di evento vuoi creare? \nScegli tra questi:\n [1] Concerto\n [2] Spettacolo\n [3] Conferenza\n");

        int scelta = input.nextInt();
        input.nextLine(); // per consumare il newline

        // controllo nel caso la scelta fosse <1 o >3
        if (scelta < 1 || scelta > 3) {
            System.out.println("Evento inserito non valido");
            input.close();
            return;
        }
        String tipoEventoScelto = tipoEventoArray.get(scelta - 1);

        // Chiedo all'utente di inserire i dettagli dell'evento:

        // TITOLO
        System.out.println("\nInserisci il titolo dell'evento:");
        String titoloInput = input.nextLine();

        // DATA
        LocalDate data = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formattazione della data
        boolean dataValida = false;
        while (!dataValida) {
            System.out.printf("\nInserisci la data dell'evento '%s' (formato dd/MM/yyyy):\n", titoloInput);

            String dataInput = input.nextLine();
            data = LocalDate.parse(dataInput, dateFormatter);

            try {
                // System.out.printf("\nInserisci la data dell'evento '%s' (formato
                // dd/MM/yyyy):\n", titoloInput);

                data = LocalDate.parse(dataInput, dateFormatter);
                if (data.isBefore(LocalDate.now())) {
                    System.out.println("L'evento è già passato. Inserisci una data valida.");
                } else {
                    dataValida = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Inserisci una data nel formato dd/MM/yyyy.");
            }
        }

        // ORA
        LocalTime ora = null;
        boolean oraValida = false;
        while (!oraValida) {
            try {
                System.out.printf("Inserisci l'ora dell'evento '%s' (formato HH:mm):\n", titoloInput);
                String oraInput = input.nextLine();
                ora = LocalTime.parse(oraInput);
                oraValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato ora non valido. Inserisci un'orario nel formato HH:mm.");
            }
        }

        // PREZZO
        System.out.println("Inserisci il prezzo del biglietto:");
        double prezzo = input.nextDouble();

        // POSTI TOTALI
        System.out.println("Inserisci il numero totale di posti: ");
        int postiTotali = input.nextInt();
        input.nextLine();

        // POSTI PRENOTATI
        System.out.println("Quanti posti vuoi prenotare?");
        int postiPrenotati = input.nextInt();
        input.nextLine();

        // inizializzo un oggetto myEvento di tipo Evento
        Evento myEvento = new Evento(titoloInput, data, postiTotali);

        // controllo data
        if (!myEvento.controlloPrenota()) {
            input.close();
            return;
        }

        // controllo posti
        if (postiPrenotati > (myEvento.getPostiTotali() - myEvento.getPostiPrenotati())) {
            System.out.println("Errore: stai cercando di prenotare più posti di quanti siano disponibili.");
        } else {
            for (int i = 0; i < postiPrenotati; i++) {
                myEvento.prenota();
            }
        }

        // Formattazione del prezzo
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
        String prezzoFormattato = currencyFormatter.format(prezzo);

        // stampo i posti prenotati e disponibili
        System.out.printf("Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        System.out.println("Evento creato: " + myEvento.toString());

        // chiedo all'utente quante prenotazioni vuole disdire
        System.out.println("Vuoi disdire un posto/posti?");
        String risposta = input.nextLine();
        if (risposta.equalsIgnoreCase("si")) {
            System.out.println("Quanti posti vuoi disdire?");
            int disdette = input.nextInt();
            input.nextLine();
            if (disdette > myEvento.getPostiPrenotati()) {
                System.out.println("Errore: non ci sono abbastanza prenotazioni da disdire.");
            } else {
                for (int i = 0; i < disdette; i++) {
                    myEvento.disdici();
                }
                System.out.printf("Hai disdetto n° %d posti.\n", disdette);
            }
        } else if (risposta.equalsIgnoreCase("no")) {
            System.out.printf("Hai disdetto n° 0 posti. \n");
        }

        // stampa aggiornata dopo eventuale disdetta
        System.out.printf("Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        // Formattazione data finale
        String dataFormattata = data.format(dateFormatter);

        // Messaggio riepilogativo
        System.out.printf("Hai creato l'evento %s \"%s\" che si svolgerà il %s alle ore %s al costo di %s.\n",
                tipoEventoScelto.toLowerCase(), titoloInput, dataFormattata, ora, prezzoFormattato);

        input.close();
    }
}