package cz.fei.upce.cv05.evidence.chatek;

import java.util.Scanner;

public class EvidenceChatekApp {

    // Konstanty pro definovani jednotlivych operaci (pouze pro cisty kod)
    static final int KONEC_PROGRAMU = 0;
    static final int VYPIS_CHATEK = 1;
    static final int VYPIS_KONKRETNI_CHATKU = 2;
    static final int PRIDANI_NAVSTEVNIKU = 3;
    static final int ODEBRANI_NAVSTEVNIKU = 4;
    static final int CELKOVA_OBSAZENOST = 5;
    static final int VYPIS_PRAZDNE_CHATKY = 6;

    static final int VELIKOST_KEMPU = 10;
    static final int MAX_VELIKOST_CHATKY = 4;

    // Definovani pole podle velikosti kempu (poctu chatek)
    static int[] chatky = new int[VELIKOST_KEMPU];
    static Scanner scanner = new Scanner(System.in);

    static int cisloChatky = 0;
    static int pocetNavstevniku = 0;

    static String menu = """
                    MENU:
                                        
                    1 - vypsani vsech chatek
                    2 - vypsani konkretni chatky
                    3 - Pridani navstevniku
                    4 - Odebrani navstevniku
                    5 - Celkova obsazenost kempu
                    6 - Vypis prazdne chatky
                    0 - Konec programu
                    """;

    public static void main(String[] args) {
        int operace;
        do {
            System.out.println(menu);
            System.out.print("Zadej volbu: ");
            operace = scanner.nextInt();

            switch (operace) {
                case VYPIS_CHATEK ->
                    vypisChatek();
                case VYPIS_KONKRETNI_CHATKU ->
                    vypisKonkretniChatku();
                case PRIDANI_NAVSTEVNIKU ->
                    pridejNavstevnikyDoChatky();
                case ODEBRANI_NAVSTEVNIKU ->
                    odeberNavstevnikyZChatky();
                case CELKOVA_OBSAZENOST ->
                    vypisCelkovouObsazenostChatek();
                case VYPIS_PRAZDNE_CHATKY ->
                    vypisPrazdneChatky();
                case KONEC_PROGRAMU -> {
                    System.out.println("Konec programu");
                }

                default -> {
                    System.err.println("Neplatna volba");
                }
            }
        } while (operace != 0);
    }

    private static void pridejNavstevnikyDoChatky() {
        if (zadaniChatkyANavstevniku()) {
            return;
        }
        if (vlozNavstevnikyDoChatky(cisloChatky, pocetNavstevniku) == false) {
            System.err.println(
                    "Prekrocen maximalni pocet navstevniku chatky");
            return;
        }
        System.out.format(
                "Navstevnici v poctu %d jsou pridany do chatky cislo %d",
                chatky[prevodCislaChatkyNaIndex(cisloChatky)], cisloChatky);
    }

    private static boolean zadaniChatkyANavstevniku() {
        cisloChatky = zadejCisloChatky();
        if (kontrolaCislaChatky(cisloChatky) == false) {
            System.err.println("Tato chatka neexistuje");
            return false;
        }
        pocetNavstevniku = zadejPocetNavstevniku();
        if (kontrolaMaxPoctuNavstevnikuVChatce(pocetNavstevniku) == false) {
            System.err.println(
                    "Neplatna hodnota pro pocet navstevniku v chatce");
            return false;
        }
        return true;
    }

    private static int prevodCislaChatkyNaIndex(int cisloChatky) {
        return cisloChatky - 1;
    }

    private static boolean vlozNavstevnikyDoChatky(
            int cisloChatky, int pocetNavstevniku) {

        int indexChatka = prevodCislaChatkyNaIndex(cisloChatky);
        if ((chatky[indexChatka] + pocetNavstevniku) > MAX_VELIKOST_CHATKY) {
            return false;
        }
        chatky[indexChatka] = pocetNavstevniku + chatky[indexChatka];
        return true;
    }

    private static boolean kontrolaMaxPoctuNavstevnikuVChatce(
            int pocet) {

        return !(pocet <= 0 || pocet > MAX_VELIKOST_CHATKY);
        //  0 < pocet && pocet <= MAX_VELIKOST_CHATKY
    }

    private static int zadejPocetNavstevniku() {
        // Ziskani poctu navstevniku, kteri se chteji v chatce ubytovat
        System.out.print("Zadej pocet navstevniku: ");
        int pocetNavstevniku = scanner.nextInt();
        return pocetNavstevniku;
    }

    private static int zadejCisloChatky() {
        // Ziskani cisla chatky od uzivatele
        System.out.print("Zadej cislo chatky: ");

        int cisloChatky = scanner.nextInt();

        return cisloChatky;
    }

    private static boolean kontrolaCislaChatky(int cisloChatky) {

        return (0 < cisloChatky && cisloChatky < VELIKOST_KEMPU);
    }

    private static boolean vypisKonkretniChatku() {
        cisloChatky = zadejCisloChatky();
        if (kontrolaCislaChatky(cisloChatky) == false) {
            System.err.println("Tato chatka neexistuje");
            return false;
        }
        int indexChatky = prevodCislaChatkyNaIndex(cisloChatky);
        System.out.println("Chatka [" + (cisloChatky) + "] = " + chatky[indexChatky]);
        return true;
    }

    private static void vypisChatek() {
        // Projdi cele pole od <0, VELIKOST) a vypis kazdy index
        for (int i = 1; i < VELIKOST_KEMPU; i++) {
            int indexChatky = prevodCislaChatkyNaIndex(i);
            System.out.println("Chatka [" + (i) + "] = " + chatky[indexChatky]);
        }
    }

    private static void odeberNavstevnikyZChatky() {
        if (zadaniChatkyANavstevniku() == false) {
            return;
        }
        int index = prevodCislaChatkyNaIndex(cisloChatky);
        if (chatky[index] >= pocetNavstevniku) {
            chatky[index] -= pocetNavstevniku;
        } else {
            System.out.println("Nelze odebrat");
        }
    }

    private static void vypisCelkovouObsazenostChatek() {
        int soucet = 0;
        for (int i = 0; i < chatky.length; i++) {
            soucet += chatky[i];
        }
        System.out.println("Obsazenost chatek je " + soucet);
    }

    private static void vypisPrazdneChatky() {
        for (int i = 0; i < chatky.length; i++) {
            if (chatky[i] == 0) {
                System.out.format("Chatka cislo %d je prazdna", chatky[i]);
            }
        }
    }
}
