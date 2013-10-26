package io.github.geraudster.chainreaction;

import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: geraud
 * Date: 10/26/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello !!");
        App app = new App();
        app.run();

    }

    private void run() {
        Element e = new Element();
        e.setName("A");
        e.changeState(StatesEnum.IDLE);
        e.hit();
        e.propagate();
        e.propagate();
    }


    public class Board extends Observable {
        private int nRow, nCol;
        public void init(int nRow, int nCol) {
            this.nRow = nRow;
            this.nCol = nCol;
        }

        public Board(int nRow, int nCol){
            init(nRow, nCol);
        }
    }
    public enum StatesEnum {
        IDLE(0), HIT(1), EXPLODED(2);
        private final int value;

        StatesEnum(int i) {
            value = i;
        }

    }

    public class Element {
        private String name;
        private State[] states = {new Idle(), new Hit(), new Exploded()};
        private StatesEnum current = StatesEnum.IDLE;

        public void changeState(StatesEnum newState) {
            current = newState;
        }

        public void propagate() {
            states[current.value].propagate(this);
        }

        public void hit() {
            states[current.value].hit(this);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * States of Element are : Idle -> Hit -> Exploded
     * Actions are : propagate -> hit
     */
    public abstract class State {
        public void propagate(Element e) {
            System.out.println("error");
        }

        public void hit(Element e) {
            System.out.println("error");
        }

        public abstract String print(Element e);
    }

    public class Idle extends State {
        @Override
        public void propagate(Element e) {
            System.out.println("Propagate e = " + e.getName() + " / state = Idle");
            e.changeState(StatesEnum.EXPLODED);
        }

        @Override
        public void hit(Element e) {
            System.out.println("Hit e = " + e.getName() + " / state = Idle");
            e.changeState(StatesEnum.HIT);
        }

        @Override
        public String print(Element e) {
            return e.getName();
        }
    }

    public class Hit extends State {
        @Override
        public void propagate(Element e) {
            System.out.println("Propagate e = " + e.getName() + " / state = Hit");
            e.changeState(StatesEnum.EXPLODED);
        }

        @Override
        public void hit(Element e) {
            System.out.println("Cannot hit e = " + e.getName() + " / state = Hit");
            // State doesn't change
        }

        @Override
        public String print(Element e) {
            return "H";
        }
    }

    /**
     * Dead-end state
     */
    public class Exploded extends State {
        @Override
        public void propagate(Element e) {
            System.out.println("Cannot propagate e = " + e.getName() + " / state = Exploded");
            // State doesn't change
        }

        @Override
        public void hit(Element e) {
            System.out.println("Cannot hit e = " + e.getName() + " / state = Exploded");
            // State doesn't change
        }

        @Override
        public String print(Element e) {
            return "X";
        }
    }


}
