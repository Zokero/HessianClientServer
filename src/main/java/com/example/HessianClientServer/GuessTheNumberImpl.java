package com.example.HessianClientServer;

import java.util.ArrayList;
import java.util.List;

public class GuessTheNumberImpl implements IGuessTheNumberService {

    private boolean isGameStarted = false;
    private int randomNumber;
    private int shoot;
    private List<Integer> integers = new ArrayList<>();

    @Override
    public boolean startGame() {
        integers.clear();
        if(!isGameStarted){
            randomNumber = (int) (Math.random() * 10 + 1);
            System.out.println(randomNumber);
            isGameStarted = true;
            for (int i = 1; i < 11; i++) {
                integers.add(i);
            }
            return true;
        }
        return false;
    }

    @Override
    public void restart() {
        isGameStarted = false;
    }

    @Override
    public String sendNumber(int number) {
        shoot = number;
        if (shoot == randomNumber) {
            startGame();
            integers.clear();
            isGameStarted = false;
            return "You Won!\n";
        } else {
            int index = integers.indexOf(shoot);
            if (index > -1) {
                integers.remove(index);
            }
            return "Try Again\n";
        }
    }

    @Override
    public List<Integer> getNumber() {
        return integers;
    }
}
