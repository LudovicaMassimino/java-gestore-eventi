package it.java.gestore.eventi;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		// Chiedi all'utente di inserire i dettagli dell'evento
        System.out.println("Inserisci il titolo dell'evento:");
        String titolo = input.nextLine();

        System.out.println("Inserisci la data dell'evento (dd/MM/yyyy):");
        String dataString = input.nextLine();
        LocalDate data = LocalDate.parse(dataString, formatter);

        int postiTotali = 50;

        // crea un nuovo evento
        
        Evento myEvento = new Evento(titolo, data, postiTotali);
        
        // controllo data
        if (!myEvento.controlloPrenota()) {
            input.close();
            return;
        }
        
        // chiedo all'utente quante prenotazioni vuole fare
        System.out.printf("Quanti posti vuoi prenotare per l'evento '%s'?\n" , titolo);
        int prenotazioni = input.nextInt();
        input.nextLine();
        
        // controllo posti
        if (prenotazioni > (myEvento.getPostiTotali() - myEvento.getPostiPrenotati())) {
            System.out.println("Errore: stai cercando di prenotare più posti di quanti siano disponibili.");
        } else {
            for (int i = 0; i < prenotazioni; i++) {
                myEvento.prenota();
            }
        }

        // stampo i posti prenotati e disponibili
        System.out.printf("Hai prenotato n° %s posti. \n" , myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n" , (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));
        
        
        // chiedo all'utente quante prenotazioni vuole disdire
        System.out.println("Vuoi disdire un posto/posti?");
        String risposta = input.nextLine();
        if(risposta.equalsIgnoreCase("si")) {
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
        } else if(risposta.equalsIgnoreCase("no")) {
        	System.out.printf("Hai disdetto n° 0 posti. \n");
        }
        
        // stampa aggiornata dopo eventuale disdetta
        System.out.printf("Hai prenotato n° %s posti. \n" , myEvento.getPostiPrenotati());
        System.out.printf("Ci sono ancora n° %s posti disponibili. \n" , (myEvento.getPostiTotali() - myEvento.getPostiPrenotati()));
        input.close();
	}
}