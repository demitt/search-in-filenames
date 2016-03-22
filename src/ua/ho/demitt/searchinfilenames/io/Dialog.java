package ua.ho.demitt.searchinfilenames.io;

import ua.ho.demitt.searchinfilenames.data.Data;

import java.util.Scanner;

public class Dialog {

    /*Печать сообщения.
    */
    public static void printMessage(String message) {
        System.out.println(message);
    }

    /*Чтение строки, вводимой пользователем.
    */
    public static String getString(boolean isWindows) {
        Scanner scr = isWindows ?
            new Scanner(System.in, Data.INPUT_ENCODING_WINDOWS) : new Scanner(System.in) ;
        return scr.nextLine().toLowerCase();
    }

}
