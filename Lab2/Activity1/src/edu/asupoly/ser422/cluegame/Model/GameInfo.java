package edu.asupoly.ser422.cluegame.Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameInfo {
    final private String[] SUSPECTS = {"Miss Scarlet", "Professor Plum", "Mrs. Peacock", "Reverend Green", "Colonel Mustard", "Mrs. White"};
    final private String[] ROOMS = {"Kitchen", "Ballroom", "Conservatory", "Dining room", "Lounge", "Hall", "Study", "Library", "Billiard Room"};
    final private String[] WEAPONS = {"Candlestick", "Dagger", "Lead pipe", "Revolver", "Rope", "Spanner"};

    private List<String> playerRooms;
    private List<String> playerSuspects;
    private List<String> playerWeapons;

    private List<String> computerRooms;
    private List<String> computerSuspects;
    private List<String> computerWeapons;
    private LinkedList<Guess> guessHistory;
    private Guess winningSecret;

    private Guess playerGuess;
    private Guess computerGuess;

    public Guess getPlayerGuess() {
        return playerGuess;
    }

    public Guess getComputerGuess() {
        return computerGuess;
    }

    public Guess getWinningSecret() {
        return winningSecret;
    }

    public LinkedList<Guess> getGuessHistory() {
        return guessHistory;
    }

    public String[] getSUSPECTS() {
        return SUSPECTS;
    }

    public String[] getROOMS() {
        return ROOMS;
    }

    public String[] getWEAPONS() {
        return WEAPONS;
    }

    public List<String> getPlayerRooms() {
        return playerRooms;
    }

    public List<String> getPlayerSuspects() {
        return playerSuspects;
    }

    public List<String> getPlayerWeapons() {
        return playerWeapons;
    }

    public List<String> getComputerRooms() {
        return computerRooms;
    }

    public List<String> getComputerSuspects() {
        return computerSuspects;
    }

    public List<String> getComputerWeapons() {
        return computerWeapons;
    }

    public GameInfo(){
        playerRooms = new ArrayList<>();
        playerSuspects = new ArrayList<>();
        playerWeapons = new ArrayList<>();

        computerRooms = new ArrayList<>();
        computerSuspects = new ArrayList<>();
        computerWeapons = new ArrayList<>();
        guessHistory = new LinkedList<Guess>();

        Random r = new Random();
        winningSecret = new Guess(SUSPECTS[r.nextInt(SUSPECTS.length)],
                WEAPONS[r.nextInt(WEAPONS.length)],
                ROOMS[r.nextInt(ROOMS.length)]);

        for(int i = 0; i< SUSPECTS.length; i++){
            if(winningSecret.suspect.equals(SUSPECTS[i])){
                playerSuspects.add(SUSPECTS[i]);
                computerSuspects.add(SUSPECTS[i]);
            } else if(i%2 == 0)
                playerSuspects.add(SUSPECTS[i]);
            else
                computerSuspects.add(SUSPECTS[i]);
        }
        for(int i = 0; i< ROOMS.length; i++){
            if(winningSecret.room.equals(ROOMS[i])){
                playerRooms.add(ROOMS[i]);
                computerRooms.add(ROOMS[i]);
            }else if(i%2 == 0)
                playerRooms.add(ROOMS[i]);
            else
                computerRooms.add(ROOMS[i]);
        }
        for(int i = 0; i< WEAPONS.length; i++){
            if(winningSecret.weapon.equals(WEAPONS[i])){
                playerWeapons.add(WEAPONS[i]);
                computerWeapons.add(WEAPONS[i]);
            }else if(i%2 == 0)
                playerWeapons.add(WEAPONS[i]);
            else
                playerWeapons.add(WEAPONS[i]);
        }
        System.out.println("Winning Secret " + winningSecret);
    }

    public void addGuess(Guess newGuess){
        guessHistory.add(newGuess);
    }

    public void newComputerGuess(){
        computerGuess = new Guess();
        Random r = new Random();
        do {
            computerGuess.room = computerRooms.get(r.nextInt(computerRooms.size()));
            computerGuess.weapon = computerWeapons.get(r.nextInt(computerWeapons.size()));
            computerGuess.suspect = computerSuspects.get(r.nextInt(computerSuspects.size()));

        }while (guessHistory.contains(computerGuess));
        addGuess(computerGuess);
    }

    public void newPlayerGuess(String suspect, String room, String weapon){
        playerGuess = new Guess(suspect,weapon,room);
    }
}
