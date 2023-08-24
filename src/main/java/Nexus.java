import Exceptions.MissingInputException;
import Exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Nexus {
    public static void main(String[] args) {
        System.out.println("Hello! I'm NEXUS");
        System.out.println("What can I do for you?");
        Scanner scanner = new Scanner(System.in);
        List<Task> list = new ArrayList<>();
        int index;
        String desc;
        StringBuilder builder = new StringBuilder();
        boolean exit = false;

        while (!exit) {
            try {
                String input = scanner.nextLine();
                String[] data = input.split(" ");
                switch (data[0]) {
                    case "bye":
                        System.out.println("Bye. Hope to see you again soon!");
                        exit = true;
                        break;
                    case "list":
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.print(i + 1);
                            System.out.println("." + list.get(i));
                        }
                        break;
                    case "mark":
                        if (data.length == 1) {
                            throw new MissingInputException("Task index must be indicated");
                        }
                        index = Integer.parseInt(data[1]) - 1;
                        list.get(index).setDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(list.get(index));
                        break;
                    case "unmark":
                        if (data.length == 1) {
                            throw new MissingInputException("Task index must be indicated");
                        }
                        index = Integer.parseInt(data[1]) - 1;
                        list.get(index).setUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(list.get(index));
                        break;
                    case "todo":
                        if (data.length == 1) {
                            throw new MissingInputException("Todo description cannot be empty");
                        }
                        for (int i = 1; i < data.length; i++) {
                            builder.append(data[i]);
                            if (i < data.length - 1) {
                                builder.append(" ");
                            }
                        }
                        desc = builder.toString();
                        list.add(new Todo(desc));
                        System.out.println("Got it. I've added this task:");
                        System.out.println(list.get(list.size() - 1));
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        break;
                    case "deadline":
                        if (data.length == 1) {
                            throw new MissingInputException("Deadline description cannot be empty");
                        }
                        index = 1;
                        while(!data[index].equals("/by")) {
                            builder.append(data[index]).append(" ");
                            index++;
                        }
                        desc = builder.toString().trim();
                        builder.setLength(0);
                        index++;
                        while(index < data.length) {
                            builder.append(data[index]).append(" ");
                            index++;
                        }
                        String by = builder.toString().trim();
                        list.add(new Deadline(desc, by));
                        System.out.println("Got it. I've added this task:");
                        System.out.println(list.get(list.size() - 1));
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        break;
                    case "event":
                        if (data.length == 1) {
                            throw new MissingInputException("Event description cannot be empty");
                        }
                        index= 1;
                        while(!data[index].equals("/from")) {
                            builder.append(data[index]).append(" ");
                            index++;
                        }
                        desc = builder.toString().trim();
                        builder.setLength(0);
                        index++;
                        while(!data[index].equals("/to")) {
                            builder.append(data[index]).append(" ");
                            index++;
                        }
                        String start = builder.toString().trim();
                        builder.setLength(0);
                        index++;
                        while(index < data.length) {
                            builder.append(data[index]).append(" ");
                            index++;
                        }
                        String end = builder.toString().trim();
                        list.add(new Event(desc, start, end));
                        System.out.println("Got it. I've added this task:");
                        System.out.println(list.get(list.size() - 1));
                        System.out.println("Now you have " + list.size() + " tasks in the list.");
                        break;
                    default:
                        scanner.reset();
                        throw new InvalidInputException("I don't understand. Please check your input again.");
                }
                scanner.reset();
                builder.setLength(0);
            } catch (InvalidInputException | MissingInputException e) {
                scanner.reset();
                System.out.println(e.getMessage());
            } catch (Exception e) {
                scanner.reset();
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
