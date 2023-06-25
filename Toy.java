package Intermedwork2;

import java.util.Random;

public class Toy {
    // #region init
    protected static long toyId;

    private long id;
    public String name;
    protected int count;
    private int dropFrequency;
    // #endregion

    static {
        toyId = 1L;
    }

    public Toy(String name) {
        this.id = toyId++;
        this.name = name;
        this.count = 1;
        this.dropFrequency = new Random().nextInt(1, 100);
    }

    public Toy(String name, int count) {
        this.id = toyId++;
        this.name = name;
        this.count = count;
        this.dropFrequency = new Random().nextInt(1, 100);
    }

    public void editDropFrequency(int dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    public int getDropFrequency() {
        return this.dropFrequency;
    }

    public String getName() {
        return this.name;
    }

    public boolean removeOneToy() {
        if (--count == 0) {
            return true;
        }
        return false;
    }

    public String getInfo() {
        return this.id + ";" + this.name + "\n";
    }
}