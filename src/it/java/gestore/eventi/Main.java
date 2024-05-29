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
        System.out.println("\n\n\n       \033[0;104m                      \033[0m");
        System.out.println("       \033[0;104m  \033[1;97mCREA IL TUO EVENTO  \033[0m");
        System.out.println("       \033[0;104m                      \033[0m");
        System.out.println(
                "\nQuale tipo di evento vuoi creare? \n   Scegli tra questi:\n   [1] Concerto\n   [2] Spettacolo\n   [3] Conferenza\n");

        int tipoEvento = input.nextInt();
        input.nextLine(); // per consumare il newline
        String tipoEventoNome = ""; // per la stampa del messaggio riepilogativo

        // Chiedo all'utente di inserire i dettagli dell'evento:

        // TITOLO
        System.out.println("\nInserisci il titolo dell'evento:");
        String titoloInput = input.nextLine();

        // DATA
        LocalDate data = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formattazione della data
        boolean dataValida = false;
        while (!dataValida) {
            System.out.printf(
                    "\nInserisci la data dell'evento \033[1;94m\"%s\"\033[0m (formato dd/MM/yyyy):\n",
                    titoloInput);

            String dataInput = input.nextLine();

            try {
                data = LocalDate.parse(dataInput, dateFormatter);
                if (data.isBefore(LocalDate.now())) {
                    System.out.println("    \033[0;91mL'evento è già passato. Inserisci una data valida.\033[0m");
                } else {
                    dataValida = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println(
                        "    \033[0;91mFormato data non valido. Inserisci una data nel formato dd/MM/yyyy.\033[0m");
            }
        }
        // Formattazione data finale
        String dataFormattata = data.format(dateFormatter);

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
                System.out
                        .println("    \033[0;91mFormato ora non valido. Inserisci un'orario nel formato HH:mm.\033[0m");
            }
        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        // Formattazione ora finale
        String oraFormattata = ora.format(timeFormatter);

        // PREZZO
        System.out.println("Inserisci il prezzo del biglietto:");
        double prezzoInput = input.nextDouble();
        // // Formattazione prezzo finale
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
        String prezzoFormattato = currencyFormatter.format(prezzoInput);

        // POSTI TOTALI
        System.out.println("Inserisci il numero totale di posti: ");
        int postiTotaliInput = input.nextInt();
        input.nextLine();

        // creo l'evento in base al tipo scelto

        Evento myEvento = null;

        switch (tipoEvento) {
            case 1:
                myEvento = new Concerto(titoloInput, data, ora, postiTotaliInput, prezzoInput);
                tipoEventoNome = "concerto";
                break;
            case 2:
                myEvento = new Spettacolo(titoloInput, data, ora, postiTotaliInput, prezzoInput);
                tipoEventoNome = "spettacolo";
                break;
            case 3:
                myEvento = new Conferenza(titoloInput, data, ora, postiTotaliInput, prezzoInput);
                tipoEventoNome = "conferenza";
                break;
            default:
                System.out.println("\033[0mEvento inserito non valido\033[0m");
                break;
        }
        // Prenotazioni
        System.out.println("Quanti posti vuoi prenotare?");
        int postiPrenotatiInput = input.nextInt();
        input.nextLine();

        for (int i = 0; i < postiPrenotatiInput; i++) {
            myEvento.prenota();
        }

        // stampo i posti prenotati
        System.out.printf("   Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("   Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        // Didette
        System.out.printf("\nVuoi disdire un posto/posti?\n");
        String risposta = input.nextLine();
        if (risposta.equalsIgnoreCase("si")) {
            System.out.println("Quanti posti vuoi disdire?");
            int disdette = input.nextInt();
            input.nextLine();
            for (int i = 0; i < disdette; i++) {
                myEvento.disdici();
            }
            System.out.printf("   Hai disdetto n° %d posti.\n", disdette);
        } else if (risposta.equalsIgnoreCase("no")) {
            System.out.printf("   Hai disdetto n° 0 posti. \n");
        }

        // stampa aggiornata dopo eventuale disdetta
        System.out.printf("   Hai prenotato n° %s posti. \n", myEvento.getPostiPrenotati());
        System.out.printf("   Ci sono ancora n° %s posti disponibili. \n",
                (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));

        // Messaggio riepilogativo versione secondo consegna
        System.out.printf("\n\nRIEPILOGO:\n\n   %s\n",
                myEvento.toString());

        // Messaggio riepilogativo seconda versione
        System.out.printf(
                "\n   Hai creato l'evento %s \"%s\" che si svolgerà il %s alle ore %s al costo di %s.\n",
                tipoEventoNome, titoloInput, dataFormattata, oraFormattata, prezzoFormattato);

        // Creazione del programma eventi

        ProgrammEventi myProgrammEventi = new ProgrammEventi(
                "\n\n\033[0;102m\033[1;90mPROGRAMMA EVENTI(ordinati per data):\033[0m\n");

        // Aggiungo myEvento nel programmEventi

        myProgrammEventi.aggiungiEvento(myEvento);

        // Stampare gli eventi presenti nel programma, ordinati per data
        String eventiOrdinati = myProgrammEventi.getEventiOrdinatiPerData();
        System.out.println(eventiOrdinati);

        input.close();
    }
}