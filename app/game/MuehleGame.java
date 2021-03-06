/**
 * Muehlegame
 * Copyright (c) 2015, Thomas Ammann, Johannes Finckh
 *
 * @author Thomas Amann, Johannes Finckh
 * @version 1.0
 */

package game;

import controllers.IController;
import play.api.Play;
import play.inject.Injector;
import views.gui.Gui;
import views.tui.Tui;

import java.util.Scanner;


public final class MuehleGame {

    private static Tui tui;
    private static Gui gui;
    private static IController controller;
    private static Scanner sc;
    private static MuehleGame instance = null;

    private MuehleGame () {
        Injector injector = Play.current().injector().asJava();

        controller = injector.instanceOf(IController.class);
        gui = new Gui(this.controller);
        tui = new Tui(this.controller);
    }

    public static void main(String[] args) {
        MuehleGame game = getInstance();
        gui.beginSetPlayerInfo();
        sc = new Scanner(System.in);

        while (sc.hasNextInt()) {
            game.getTui().handleInput(sc.nextInt());
        }
        sc.close();
    }

    public static MuehleGame getInstance() {
        if(instance == null) {
            instance = new MuehleGame();
        }
        return instance;
    }

    public Tui getTui() {
        return this.tui;
    }

}
