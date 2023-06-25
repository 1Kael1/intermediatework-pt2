package Intermedwork2;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        ArrayList<Toy> toys = new ArrayList<>();
        ArrayList<Toy> prize = new ArrayList<>();
        boolean notStop = true;
        String answer;
        Path path = Path.of("Intermedwork", "GivenAway.scv");
        Path directoryPath = Path.of("Java", "Intermedwork");

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        while (notStop) {
            System.out.println("Choose one:");
            System.out.println("1. Add a new toy");
            System.out.println("2. Add random toy to the prize");
            System.out.println("3. Get luck and win the prize");
            System.out.println("4. Exit");
            answer = scanner.nextLine();
            System.out.println();

            switch (answer) {
                case "1":
                    System.out.print("Enter toy`s name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter number of toys: ");
                    String count = scanner.nextLine();
                    System.out.println();
                    Toy toy;

                    if (stringToInt(count) == -1) {
                        System.out.println("Illegal value for number of toys. One toy added instead.");
                        toy = new Toy(answer);
                    } else {
                        toy = new Toy(name, Integer.parseInt(count));
                    }

                    System.out.println("Toy with drop frequency = " + toy.getDropFrequency() + " added.");
                    System.out.println("Do you want to change it`s drop frequency?");
                    System.out.print("Y/N ");
                    if (scanner.nextLine().toLowerCase().equals("y")) {
                        System.out.print("New drop friquency = ");
                        int newFriquency = stringToInt(scanner.nextLine());

                        if (newFriquency == -1) {
                            System.out.println("Illigal value for drop friquency. Changes denied.");
                        } else {
                            if (newFriquency < 1) {
                                newFriquency = 1;
                            } else if (newFriquency > 100) {
                                newFriquency = 100;
                            }
                            toy.editDropFrequency(newFriquency);
                        }
                    }
                    toys.add(toy);
                    System.out.println();
                    break;
                case "2":
                    if (toys.isEmpty()) {
                        System.out.println("There are no availible toys to add.");
                        System.out.println();
                    } else {
                        Toy randomToy = selectRandomToy(toys);
                        prize.add(randomToy);
                        if (randomToy.removeOneToy()) {
                            toys.remove(randomToy);
                        }
                        System.out.println("Toy " + randomToy.getName() + " sucssessfully added");
                        System.out.println();
                    }
                    break;
                case "3":
                    if (prize.isEmpty()) {
                        System.out.println("There are no toys to give away.");
                    } else {
                        Toy bet = prize.get(0);
                        
                        if (random.nextInt(0, 100) <= bet.getDropFrequency()) {
                            System.out.println("Congratulations! Your win is " + bet.getName());
                            prize.remove(0);
                            saveGivenToy(path, bet);
                        } else {
                            System.out.println("Sorry, you lost. Try again and you`ll win!");
                        }
                    }
                    System.out.println();
                    break;
                case "4":
                    notStop = false;
                    break;
                default:
                    System.out.println("Illigal choise. Try again.");
                    System.out.println();
                    break;
            }
        }
        scanner.close();
    }

    private static Toy selectRandomToy(ArrayList<Toy> toys) {
        Random random = new Random();
        return toys.get(random.nextInt(toys.size()));
    }

    private static int stringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }

    private static void saveGivenToy(Path path, Toy toy) {
        String text = toy.getInfo();
        try {
            Files.write(path, text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}