package ua.ho.demitt.searchinfilenames.controller;

import ua.ho.demitt.searchinfilenames.data.Data;
import ua.ho.demitt.searchinfilenames.data.Result;

import java.io.File;

public class Validator {

    // Определения:


    /*Передан ли спец. аргумент комм. строки, сообщающий о том, что запустили из виндового BAT-ника.
    */
    public static boolean hasWindowsBatFlag(String[] argsMain) {
        return (argsMain.length!=0 && Data.FIRST_ARG_WINDOWS.equals(argsMain[0]) );
    }

    /*Определение ОС. Флаг "Это windows".
    */
    public static boolean isWindows() {
        return ";".equals(File.pathSeparator);
    }

    /*Является ли эта строка файла настроек комментарием.
    */
    public static boolean isComment(String currentLine) {
        return currentLine.charAt(0) == Data.COMMENT_MARKER;
    }


    //Проверки:


    /*Проверки целевой папки.
    */
    public static Result checkTargetDirectoryObject(File targetDirectoryObj) {
        //Существует ли целевая папка:
        if (!targetDirectoryObj.exists()) {
            return new Result(false, "Целевая папка не существует.");
        }
        //Папка ли это:
        if (!targetDirectoryObj.isDirectory()) {
            return new Result(false, "Это не папка.");
        }
        File[] listFiles = targetDirectoryObj.listFiles();
        if (listFiles.length == 0) {
            return new Result(false, "Целевая папка пуста.");
        }
        return new Result(true);
    }

}
