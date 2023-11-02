package ui;

import java.util.Arrays;
import java.util.Scanner;
import algorithm.*;

public class UI {

    private Scanner _scanner;
    private String _output;

    public UI() {
        _scanner = new Scanner(System.in);
        _output = "None.";
    }

    public void run() {
        while (true) {
            clearScreen();
            System.out.println("Output: " + _output);
            System.out.println("Choose an option:");
            System.out.println("1. Timsort");
            System.out.println("2. Exit");
            System.out.print("Input: ");
            String choice = _scanner.nextLine();
            switch (choice) {
                case "1":
                    timsortTesting();
                    break;
                case "2":
                    System.out.println("Exiting program.");
                    _scanner.close();
                    System.exit(0);
                default:
                    _output = "Invalid choice.";
                    break;
            }
        }
    }

    private void timsortTesting() {

        _output = "None.";
        boolean running = true;

        while (running) {
            clearScreen();
            System.out.println("Output: " + _output);
            System.out.println("Choose an option:");
            System.out.println("1. Launch");
            System.out.println("2. Exit");
            System.out.print("Input: ");
            String choice = _scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Input array size: ");
                    int arraySize;
                    try {
                        arraySize = Integer.parseInt(_scanner.nextLine());
                    } catch (NumberFormatException e) {
                        _output = "Invalid input!";
                        break;
                    }
                    // int[] array = Timsort.generateGallopTestArray(arraySize, 1, 10, 50, 60);
                    int[] array = Timsort.generateRandomArray(arraySize);
                    _output = "\n\nRandom array: " + Arrays.toString(array) + "\n\nSorted array: "
                            + Timsort.launch(array) + "\n";
                    break;
                case "2":
                    running = false;
                    _output = "None.";
                    break;
                default:
                    _output = "Invalid choice.";
                    break;
            }
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
