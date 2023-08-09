package com.mjc.school;

import com.mjc.school.controller.AuthorControllerRequest;
import com.mjc.school.controller.NewsControllerRequest;
import com.mjc.school.controller.TagControllerRequest;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.implementation.TagController;
import com.mjc.school.spring.SpringConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.*;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        NewsController newsController = context.getBean("newsController", NewsController.class);
        AuthorController authorController = context.getBean("authorController", AuthorController.class);
        TagController tagController = context.getBean("tagController", TagController.class);
        context.close();

        //authorController.createTestDataBase();
        //tagController.createTestDataBase();
        //newsController.createTestDataBase();

        boolean exit = false;
        Scanner reader = new Scanner(System.in);

        Map<Integer, Command> commandsMap = new HashMap<>();
        commandsMap.put(1, () -> String.valueOf(newsController.create(newsRequest(getUserInputAsString("Input title:", reader),
                getUserInputAsString("Input content:", reader),
                (long) getUserInputAsInt("Input author id:", reader),
                getUserInputAsList("Input list of tags id: (optional)  Example:1,2,3", reader)))));
        commandsMap.put(2, () -> String.valueOf(authorController.create(authorRequest(getUserInputAsString("Input author name:", reader)))));
        commandsMap.put(3, () -> String.valueOf(tagController.create(tagRequest(getUserInputAsString("Input tag name:", reader)))));
        commandsMap.put(4, () -> String.valueOf(newsController.readAll()));
        commandsMap.put(5, () -> String.valueOf(authorController.readAll()));
        commandsMap.put(6, () -> String.valueOf(tagController.readAll()));
        commandsMap.put(7, () -> String.valueOf(newsController.readById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(8, () -> String.valueOf(authorController.readById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(9, () -> String.valueOf(tagController.readById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(10, () -> String.valueOf(newsController.update(newsRequest(getUserInputAsString("Input title:", reader),
                getUserInputAsString("Input content", reader),
                (long) getUserInputAsInt("Input news id:", reader),
                (long) getUserInputAsInt("Input author id:", reader),
                getUserInputAsList("Input list of tags id: (optional) Example:1,2,3", reader)))));
        commandsMap.put(11, () -> String.valueOf(authorController.update(authorRequest(getUserInputAsString("Input author name", reader),
                (long) getUserInputAsInt("Input user id:", reader)))));
        commandsMap.put(12, () -> String.valueOf(tagController.update(tagRequest(getUserInputAsString("Input tag name:", reader),
                (long) getUserInputAsInt("Input tag id:", reader)))));
        commandsMap.put(13, () -> String.valueOf(newsController.deleteById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(14, () -> String.valueOf(authorController.deleteById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(15, () -> String.valueOf(tagController.deleteById((long) getUserInputAsInt("Input id:", reader))));
        commandsMap.put(16, () -> String.valueOf(newsController.getAuthorByNewsId((long) getUserInputAsInt("Input news id:", reader))));
        commandsMap.put(17, () -> String.valueOf(newsController.getTagsByNewsId((long) getUserInputAsInt("Input news id:", reader))));
        commandsMap.put(18, () -> String.valueOf(newsController.getNewsByParams(
                getUserInputAsString("Input tag name: print - if null", reader),
                getUserInputAsList("Input list of tags id: Example:1,2,3 print - if null)",reader),
                getUserInputAsString("Input author name: print - if null", reader),
                getUserInputAsString("Input news title: print - if null", reader),
                getUserInputAsString("Input news content: print - if null", reader))));

        while (!exit) {
            showMenu();
            int inputKey = reader.nextInt();

            if (commandsMap.containsKey(inputKey)) {
                catchThis(commandsMap.get(inputKey));
            } else {
                if (inputKey == 0) {
                    exit = true;

                } else {
                    System.out.println("Unsupported operation");
                }
            }
        }
    }

    private static void showMenu() {
        System.out.println("Enter the number of operation:");
        System.out.println("1 - Create news.");
        System.out.println("2 - Create author.");
        System.out.println("3 - Create tag.");
        System.out.println("4 - Read all news.");
        System.out.println("5 - Read all authors.");
        System.out.println("6 - Read all tags.");
        System.out.println("7 - Read news by id.");
        System.out.println("8 - Read author by id.");
        System.out.println("9 - Read tag by id.");
        System.out.println("10 - Update news.");
        System.out.println("11 - Update author.");
        System.out.println("12 - Update tag.");
        System.out.println("13 - Delete news.");
        System.out.println("14 - Delete author.");
        System.out.println("15 - Delete tag.");
        System.out.println("16 - Get author by news id.");
        System.out.println("17 - Get tags by news id.");
        System.out.println("18 - Get news by tag names, tag id's, author name, title, content.");
        System.out.println("0 - Exit.");
    }

    public interface Command {
        String run();
    }

    private static void catchThis(Command command) {
        try {
            String actionValueOf = command.run();
            System.out.println(actionValueOf);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int getUserInputAsInt(String message, Scanner reader) {
        System.out.println(message);
        try {
            return reader.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    private static String getUserInputAsString(String message, Scanner reader) {
        System.out.println(message);
        try {
            return reader.next();
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    private static List<Long> getUserInputAsList(String message, Scanner reader) {
        System.out.println(message);
        try {
            String parse = reader.next();
            String[] lines = parse.split(",");
            List<Long> list = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                list.add(Long.parseLong(lines[i]));
            }
            return list;
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    private static NewsControllerRequest newsRequest(String title, String content, Long authorId, List<Long> tagsId) {
        NewsControllerRequest req = new NewsControllerRequest();
        req.setTitle(title);
        req.setContent(content);
        req.setAuthorId(authorId);
        req.setTagsId(tagsId);
        return req;
    }

    private static NewsControllerRequest newsRequest(String title, String content, Long newsId, Long authorId, List<Long> tagsId) {
        NewsControllerRequest req = new NewsControllerRequest();
        req.setTitle(title);
        req.setContent(content);
        req.setId(newsId);
        req.setAuthorId(authorId);
        req.setTagsId(tagsId);
        return req;
    }

    private static AuthorControllerRequest authorRequest(String name, Long id) {
        AuthorControllerRequest req = new AuthorControllerRequest();
        req.setName(name);
        req.setId(id);
        return req;
    }

    private static AuthorControllerRequest authorRequest(String name) {
        AuthorControllerRequest req = new AuthorControllerRequest();
        req.setName(name);
        return req;
    }

    private static TagControllerRequest tagRequest(String name) {
        TagControllerRequest req = new TagControllerRequest();
        req.setName(name);
        return req;
    }

    private static TagControllerRequest tagRequest(String name, Long id) {
        TagControllerRequest req = new TagControllerRequest();
        req.setName(name);
        req.setId(id);
        return req;
    }
}

