/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package views.tui;

import controllers.IController;
import models.IPlayer;
import observer.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tui implements IObserver {

    private IController controller;
    private Scanner sc = new Scanner(System.in);
    Logger logger = LoggerFactory.getLogger("view.tui.Tui");

    public Tui(IController controller) {
        this.controller = controller;
        controller.registerObserver(this);
        this.display();
    }


    private void loggerMethod(String log) {
        logger.info(log);
    }


    public boolean handleInput(int vertex) {
        if (controller.getCurrentStonesToDelete() > 0) {
            controller.millDeleteStone(vertex);
        } else if (controller.requireInitial()) {
            if (!controller.setStone(vertex)) {
                String log = "Falsche Eingabe!";
                this.loggerMethod(log);
            }
        } else {
            String log;
            log = "Bitte Endknoten eingeben: ";
            this.loggerMethod(log);
            int end = sc.nextInt();
            if (!controller.moveStone(vertex, end)) {
                log = "Falsche Eingabe!";
                this.loggerMethod(log);
            }
        }
        return true;
    }

    public String display() {
        String log;

        log = "\n%1-----------%2-----------%3\t\t" + "1-----------2-----------3\n" +
                "|           |           |\t\t"             + "|           |           |\n"  +
                "|   %4-------%5-------%6   |\t\t"          + "|   4-------5-------6   |\n"  +
                "|   |       |       |   |\t\t"             + "|   |       |       |   |\n"  +
                "|   |   %7---%8---%9   |   |\t\t"          + "|   |   7---8---9   |   |\n"  +
                "|   |   |       |   |   |\t\t"             + "|   |   |       |   |   |\n"  +
                "%10---%11---%12       %13---%14---%15\t\t" + "10--11--12      13--14--15\n" +
                "|   |   |       |   |   |\t\t"             + "|   |   |       |   |   |\n"  +
                "|   |   %16---%17---%18   |   |\t\t"       + "|   |   16--17--18  |   |\n"  +
                "|   |       |       |   |\t\t"             + "|   |       |       |   |\n"  +
                "|   %19-------%20-------%21   |\t\t"       + "|   19------20------21  |\n"  +
                "|           |           |\t\t"             + "|           |           |\n"  +
                "%22-----------%23-----------%24\t\t"       + "22----------23----------24\n";

        Map<Integer, Character> map = new HashMap<>();
        for(int i = 1; i <= 24; i++) {
            map.put(i, controller.getVertexColor(i));
        }

        StringBuilder builder = new StringBuilder(log);
        for(Map.Entry<Integer, Character> e : map.entrySet()) {
            String key = e.getKey().toString();
            String value = e.getValue().toString();
            String pattern = "%" + key;
            int start = builder.indexOf(pattern);

            builder.replace(start, start + pattern.length(), value);
        }
        this.loggerMethod(builder.toString());
        return builder.toString();
    }

    private void clear() {
        String log = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
        this.loggerMethod(log);
    }

    @Override
    public void update(IPlayer currentPlayer, int anzMills, boolean gameEnded) {
        String log = "";
        if (gameEnded) {
            log = currentPlayer.getName() + " hat gewonnen!";
        } else if (anzMills > 0) {
            if (anzMills == 1) {
                log = currentPlayer.getName() + " hat eine Muehle, loesche einen Stein!";
            } else if (anzMills == 2) {
                log = currentPlayer.getName() + " hat zwei Muehlen, loesche zwei Steine!";
            }
        } else {
            log = currentPlayer.getName() + " ist an der Reihe!";

        }
        this.clear();
        this.display();
        this.loggerMethod(log);
    }

}

