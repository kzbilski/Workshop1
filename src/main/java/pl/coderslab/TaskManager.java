import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskManager {

    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;

    public static void main(String[] args) {
        do{
            printAllOptions();
            String result = readInput();
            executeDecision(result);
        }while (running);
    }


    public static void printAllOptions() {
        System.out.println("Please select an option:");
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    public static String readInput() {
        String input = scanner.nextLine();
        return input;
    }

    public static void executeDecision(String decision) {
        switch (decision) {
            case "add":
                executeAdd();
                break;
            case "remove":
                executeRemove();
                break;
            case "list":
                executeList();
                break;
            case "exit":
                executeExit();
                break;
            default:
                System.out.println("Unrecognized input");
        }
    }

    public static void executeAdd() {
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dateString = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        boolean important = scanner.nextBoolean();
        scanner.nextLine(); // odetkać scanner z pozostawionego \n

        // teraz zlecamy te rzeczy innej metodzie ktora to doda do pliku
        addTaskToFile(description, dateString, important);

    }

    public static void addTaskToFile(String description, String dateString, boolean important) {
        try {
            FileOutputStream fos = new FileOutputStream("task.csv", true);
            PrintWriter writer = new PrintWriter(fos);
            writer.println(description + ", " + dateString + ", " + important);
            writer.close();
        } catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException!");
        }
    }

    public static void executeRemove() {
        ArrayList<String> tasks = readTasksFromFile();
        int indexToRemove = scanner.nextInt();
        scanner.nextLine(); // odetkanie scannera
        tasks.remove(indexToRemove);
        addTaskToFile(tasks);

    }

    public static void addTaskToFile(ArrayList<String> tasks) {  //wersja metody zapisywania taskow na potrzeby remove
        try {
            FileOutputStream fos = new FileOutputStream("task.csv", false);
            PrintWriter writer = new PrintWriter(fos);

            for( String task : tasks){
                writer.println(task);
            }

            writer.close();
        } catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException!");
        }
    }

    public static void executeList() {
        ArrayList<String> tasks = readTasksFromFile();
        for (int i = 0; i < tasks.size(); i++) {
            String task = tasks.get(i);
            System.out.println(i + " : " + task);
        }
    }

    public static ArrayList<String> readTasksFromFile() {
        // String[] tasksArray = new String[100]; // problem z tablicą - stały rozmiar
        ArrayList<String> tasks = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File("task.csv"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                tasks.add(line);
            }
            fileScanner.close();
        } catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException!");
        }
        return tasks;
    }

    public static void executeExit() {
        System.err.println("bye, bye");
        running = false;
    }

}

