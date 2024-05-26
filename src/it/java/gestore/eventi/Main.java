package it.java.gestore.eventi;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n       CREA IL TUO EVENTO");
        System.out.println(
                "\nQuale tipo di evento vuoi creare? \nScegli tra questi:\n [1] Concerto\n [2] Spettacolo\n [3] Conferenza\n");

        int tipoEvento = input.nextInt();
        input.nextLine(); // per consumare il newline
        String tipoEventoNome = "";

        // Chiedo all'utente di inserire i dettagli dell'evento:

        // TITOLO
        System.out.println("\nInserisci il titolo dell'evento:");
        String titoloInput = input.nextLine();

        // DATA
        LocalDate data = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formattazione della data
        boolean dataValida = false;
        while (!dataValida) {
            System.out.printf("\nInserisci la data dell'evento \"%s\" (formato dd/MM/yyyy):\n", titoloInput);

            String dataInput = input.nextLine();

            try {
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
                System.out.printf("Inserisci l'ora dell'evento \"%s\" (formato HH:mm):\n", titoloInput);
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

        // creo l'evento in base al tipo scelto

        Evento myEvento = null;

        switch (tipoEvento) {
            case 1:
                myEvento = new Concerto(titoloInput, data, ora, postiTotali, prezzo);
                tipoEventoNome = "concerto";
                break;
            case 2:
                myEvento = new Spettacolo(titoloInput, data, ora, postiTotali, prezzo);
                tipoEventoNome = "spettacolo";
                break;
            case 3:
                myEvento = new Conferenza(titoloInput, data, ora, postiTotali, prezzo);
                tipoEventoNome = "conferenza";
                break;
            default:
                System.out.println("Evento inserito non valido");
                break;
        }
        // Prenotazioni
        System.out.println("Quanti posti vuoi prenotare?");
        int postiPrenotati = input.nextInt();
        input.nextLine();

        for (int i = 0; i < postiPrenotati; i++) {
            myEvento.prenota();
        }

        // stampo i posti prenotati
        System.out.printf("Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        // Didette
        System.out.println("Vuoi disdire un posto/posti?");
        String risposta = input.nextLine();
        if (risposta.equalsIgnoreCase("si")) {
            System.out.println("Quanti posti vuoi disdire?");
            int disdette = input.nextInt();
            input.nextLine();
            for (int i = 0; i < disdette; i++) {
                myEvento.disdici();
            }
            System.out.printf("Hai disdetto n° %d posti.\n", disdette);
        } else if (risposta.equalsIgnoreCase("no")) {
            System.out.printf("Hai disdetto n° 0 posti. \n");
        }

        // stampa aggiornata dopo eventuale disdetta
        System.out.printf("Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        // Formattazione data finale
        String dataFormattata = data.format(dateFormatter);
        // Formattazione prezzo finale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
        String prezzoFormattato = currencyFormatter.format(prezzo);

        // Messaggio riepilogativo
        System.out.printf("Hai creato l'evento %s \"%s\" che si svolgerà il %s alle ore %s al costo di %s.\n",
                tipoEventoNome, titoloInput, dataFormattata, ora, prezzoFormattato);

        // Creazione del programma eventi

        ProgrammEventi myProgrammEventi = new ProgrammEventi("Programma Eventi");

        // Aggiunta degli eventi al programma
        Evento evento1 = new Evento("Coldplay. X&Y", LocalDate.now(), 60000);
        Evento evento2 = new Evento("Teresa Mannino. Sono nata il ventitré.", LocalDate.now().plusDays(30), 1000);
        Evento evento3 = new Evento("Tedx", LocalDate.now().plusDays(100), 3000);
        Evento evento4 = new Evento("Ludovico Einaudi", LocalDate.now().plusDays(200), 30000);
        Evento evento5 = new Evento("Apologia di Socrate", LocalDate.now().plusDays(300), 1000);
        Evento evento6 = new Evento("Conferenza sulla termodinamica", LocalDate.now().plusDays(600), 3000);

        myProgrammEventi.aggiungiEvento(evento1);
        myProgrammEventi.aggiungiEvento(evento2);
        myProgrammEventi.aggiungiEvento(evento3);
        myProgrammEventi.aggiungiEvento(evento4);
        myProgrammEventi.aggiungiEvento(evento5);
        myProgrammEventi.aggiungiEvento(evento6);
        myProgrammEventi.aggiungiEvento(myEvento);

        // Stampare gli eventi presenti nel programma, ordinati per data
        String eventiOrdinati = myProgrammEventi.getEventiOrdinatiPerData();
        System.out.println("\nEventi nel programma (ordinati per data):\n" + eventiOrdinati);

        input.close();
    }
}