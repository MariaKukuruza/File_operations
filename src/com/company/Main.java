package com.company;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    final static String path = "C:\\Users\\Mary\\IdeaProjects\\7 лаба 5 задание\\nw";

    public static void main(String[] args) {
        createPath();
        int answer;
        do {
            menu();
            answer = intInput();
            switch (answer) {
                case 1 -> createFile();
                case 2 -> renameFile();
                case 3 -> writeFile();
                case 4 -> deleteFile();
                case 5 -> readFile();
                case 0 -> {}
                default -> System.out.println("Такого пункта в меню нет");
            }
        } while (answer != 0);
        deletePath();
    }

    static void createFile() {
        String fileName;
        do{
            fileName = inputFileName();
        }
        while (wasCreated(fileName));
        File newFile = new File(path + fileName);
        try {
            boolean created = newFile.createNewFile();
            if(created)
                System.out.println("Файл был создан");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    static void readFile(){
        String fileName = getFileName();
        if(!fileName.equals("empty")) {
            System.out.println();
            File file = new File(path + fileName);
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                String s;
                while((s = br.readLine()) != null){
                    System.out.println(s);
                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    static void renameFile(){
        String fileName = getFileName();
        if(!fileName.equals("empty")) {
            String dir = path + fileName;
            File file = new File(dir);
            fileName = inputFileName();
            dir = path + fileName;
            File newFile = new File(dir);
            boolean renamed = file.renameTo(newFile);
            if (renamed) {
                System.out.println("Файл был переименован");
            } else {
                System.out.println("Не удалось переименовать файл");
            }
        }
    }

    static void deleteFile(){
        String fileName = getFileName();
        if(!fileName.equals("empty")) {
            String dir = path + fileName;
            File file = new File(dir);
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Файл был удален");
            } else {
                System.out.println("Не удалось удалить файл");
            }
        }
    }

    static void createPath(){
        File file = new File(path);
        boolean created = file.mkdir();
        if (created) {
            System.out.println("Папка была создана");
        } else {
            System.out.println("Не удалось создать папку");
        }
    }

    static String inputFileName(){
        String fileName = "";
        while (true) {
            try {
                System.out.print("Введите имя файла: ");
                Scanner in = new Scanner(System.in);
                fileName = in.nextLine();
                break;
            } catch (Exception ex) {
                System.out.println("Не корректный ввод, попробуйте еще раз");
            }
        }
        return "/" + fileName + ".txt";
    }

    static int inputInt(){
        int Int;
        while (true) {
            try {
                System.out.print("Введите номер файла: ");
                Scanner in = new Scanner(System.in);
                Int = in.nextInt();
                break;
            } catch (Exception ex) {
                System.out.println("Не корректный ввод, попробуйте еще раз");
            }
        }
        return Int;
    }

    static int intInput(){
        int Int;
        while (true) {
            try {
                Scanner in = new Scanner(System.in);
                Int = in.nextInt();
                break;
            } catch (Exception ex) {
                System.out.println("Не корректный ввод, попробуйте еще раз");
            }
        }
        return Int;
    }

    static void showPath(){
        File file = new File(path);
        String[] filesInDir = file.list();
        for (int i = 0; i < Objects.requireNonNull(filesInDir).length; i++){
            System.out.println((i + 1) + ". " + filesInDir[i]);
        }
        if (filesInDir.length == 0){
            System.out.println("В текущей папке нет файлов");
        }
    }

    static String getFileName(){ // Возвращает имя файла по его номеру в списке файлов иначе возвращает эмпти
        showPath();
        File file = new File(path);
        String[] filesInDir = file.list();
        int numberFile;
        assert filesInDir != null;
        if(filesInDir.length != 0) {
            do {
                numberFile = inputInt();
                if (numberFile < 1 || numberFile > filesInDir.length) {
                    System.out.println("Файла с таким номером нет, попробуйте еще раз");
                } else {
                    numberFile--;
                    break;
                }
            } while (true);
            return "/" + filesInDir[numberFile];
        }
        return "empty";
    }

    static void writeFile(){
        String fileName = getFileName();
        if(!fileName.equals("empty")){
            File file = new File(path + fileName);
            try(FileWriter writer = new FileWriter(file, false)) {
                System.out.println("Введите текст для записи в файл (что бы закончить ввод введите \"стоп\")\n");
                String text = "";
                while(true) {
                    Scanner in = new Scanner(System.in);
                    text = in.nextLine();
                    if(text.equals("стоп")) break;
                    else text += "\n";
                    writer.write(text);
                    writer.flush();
                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    static boolean wasCreated(String fileName){
        fileName = new StringBuilder(fileName).substring(1); // обрезали стринг(оставили 1 - конец)
        File file = new File(path);
        String[] filesInDir = file.list();
        assert filesInDir != null;
        for (String e: filesInDir){
            if(e.equals(fileName)) {
                System.out.println("Такое имя занято");
                return true;
            }
        }
        return false;
    }

    static void deletePath(){
        File file = new File(path);
        file.delete();
    }

    static void menu(){
        System.out.println("""

                1. Создать файл
                2. Переименовать файл
                3. Записать информацию в файл
                4. Удалить файл
                5. Показать содержимое файла
                0. Выход
                """);
    }
}