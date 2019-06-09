package com.example.HessianClientServer;

import java.util.List;

public interface IGuessTheNumberService {
    String sendNumber(int number);
    List<Integer> getNumber();
    boolean startGame();
    void restart();
}

