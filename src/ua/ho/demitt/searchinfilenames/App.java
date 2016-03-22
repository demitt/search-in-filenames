package ua.ho.demitt.searchinfilenames;

import ua.ho.demitt.searchinfilenames.controller.Searcher;
import ua.ho.demitt.searchinfilenames.controller.Validator;
import ua.ho.demitt.searchinfilenames.data.Data;
import ua.ho.demitt.searchinfilenames.data.Result;
import ua.ho.demitt.searchinfilenames.io.Dialog;

import java.io.File;

public class App {

    public static void main(String[] args) {

        //Чтение настроек:
        Options options = new Options();
        if ( !options.readOptions().isGood() ) {
            Dialog.printMessage(options.readOptions().getMessage());
            return;
        }

        //Указана ли целевая папка:
        if (options.docsDir == null) {
            Dialog.printMessage(Data.Literal.ERR_NOTARGETDIRPATH.getVal());
            return;
        }

        Dialog.printMessage("Целевая папка: " + options.docsDir + File.separator);
        File targetDirectoryObj = new File(options.docsDir);

        //Проверки целевой папки:
        Result checkResult = Validator.checkTargetDirectoryObject(targetDirectoryObj);
        if ( !checkResult.isGood() ) {
            Dialog.printMessage(checkResult.getMessage());
            return;
        }

        if (options.searchInFilenamesOnlyFlag) {
            Dialog.printMessage(Data.Literal.INF_ONLYFILENAMES.getVal());
        }
        /*if (options.searchFromStartFlag) {
            Utils.printMessage("Ищем с начала имени.");
        }*/

        //Флаг того, что запускаем bat-ником из-под винды (передан спец. аргумент):
        boolean isWindowsBat = Validator.hasWindowsBatFlag(args);

        int counter = 0;
        boolean continueWhile = true;
        while (continueWhile) {
            counter++;
            Dialog.printMessage("Введите искомую строку (чтобы выйти - Enter)  [" + counter + "]");
            String searchString = Dialog.getString(isWindowsBat);
            StringBuilder resultString = new StringBuilder();
            continueWhile = Searcher.search(searchString, resultString, targetDirectoryObj, options);
            Dialog.printMessage(resultString.toString());
        }

    }

}
