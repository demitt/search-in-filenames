package ua.ho.demitt.searchinfilenames.controller;

import ua.ho.demitt.searchinfilenames.Options;
import ua.ho.demitt.searchinfilenames.data.Data;

import java.io.File;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Searcher {

    /*Поиск.
    */
    public static boolean search(String searchString, StringBuilder resultString, File targetDirectory, Options options) {
        if ( "".equals(searchString.trim()) ) { //в строке были только пробельные символы или она была пустой
            if (searchString.length()!=0) { //не была пустой
                resultString.append(Data.Literal.INF_NOSPACES.getVal());
            }
            return false;
        }
        //resultString.append("[ Ищем строку: ").append(searchString).append(" ]");

        SearchData searchData = new SearchData(searchString, options);
        recursiveSearch(targetDirectory, searchData);

        resultString.
            append(searchData.outputSB).  //список
            append("Найдено ").append(searchData.matchesCount).
            append("  [ просмотрено ").append(searchData.objsForSearchCount).
            append(", всего ").append(searchData.objsTotalCount).
            append(" ]\n")
        ;
        return true;
    }

    /*Поиск по папкам.
    Вызывается рекурсивно.
    */
    public static void recursiveSearch(File currentDir, SearchData searchData) {
        File[] listFiles = currentDir.listFiles();
            int objsCount = listFiles.length;
        if (objsCount == 0) {
            return;
        }
        searchData.objsTotalCount += objsCount;
        searchData.setLastPathElement(currentDir.getName());
        Arrays.sort(listFiles);
        String typeMarker, currentName, currentNameForSearch, path;
        boolean isDirectory;
        for (File curFileObj : listFiles) {
            isDirectory = curFileObj.isDirectory(); //флаг "это папка"
            if ( !(searchData.options.searchInFilenamesOnlyFlag && isDirectory) ) { //НЕ "только *файлы* И это папка"
                searchData.objsForSearchCount++;
                typeMarker = (curFileObj.isDirectory()) ? File.separator : "" ;
                currentName = curFileObj.getName();
                currentNameForSearch = currentName.toLowerCase();
                if ( currentNameForSearch.contains(searchData.searchString) ) {
                    searchData.matchesCount++;
                    path = searchData.getPathToObj();
                    searchData.outputSB.
                        append(Data.Literal.LIT_FOUNDENTRYMARKER.getVal()).
                        append(currentName) . append(typeMarker)
                    ;
                    if (!"".equals(path)) { //объект находится не в корне
                        searchData.outputSB.
                            append(Data.Literal.LIT_FOUNDENTRYPATHMARKER.getVal()).
                            append(searchData.getPathToObj());
                    }
                    searchData.outputSB.append("\n");
                }
            }
            if (isDirectory) {
                recursiveSearch(curFileObj, searchData);
            }
        }
        searchData.setLastPathElement(null);
    }

    /*Класс, хранящий данные о поиске - в процессе поиска и по завершению поиска.
    */
    public static class SearchData {
        public String searchString;
        public int objsTotalCount = 0; //общее кол-во найденных объектов
        public int objsForSearchCount = 0; //кол-во просмотреных файлов/папок
        public int matchesCount = 0; //кол-во файлов/папок, попавших под условие совпадения
        public StringBuilder outputSB = new StringBuilder();
        public Options options = null; //ссылка на объект настроек
        //Стек с эл-тами относительного пути (нулевой его эл-т - целевая папка):
        private Deque<String> pathStack = new LinkedList<>();

        public SearchData(String searchString, Options options) {
            this.searchString = searchString;
            this.options = options;
        }

        /*Добавление/удаление последнего эл-та в this.pathStack.
        Если передан null, то удаление.
        Если передан не-null, то его добавление.
        */
        private void setLastPathElement(String currentElement) {
            if (currentElement != null) {
                //this.outputSB.append("Добавляем в путь папку: ").append(currentElement).append("\n"); //<<<
                this.pathStack.addLast(currentElement);
            } else {
                //this.outputSB.append("Удаляем из пути папку: ").append(this.pathStack.peekLast()).append("\n"); //<<<
                this.pathStack.removeLast(); //удаление
            }
            //this.outputSB.append(this.pathStack).append("\n");
        }

        /*Формирование пути к найденному файлу/папке.
        */
        public String getPathToObj() {
            //this.pathStack.removeFirst();
            StringBuilder path = new StringBuilder();
            boolean firstElementFlag = true;
            for (String currentDir : this.pathStack ) {
                if (firstElementFlag) { //нулевой эл-т - имя исходной папки, поэтому пропускаем
                    firstElementFlag = false;
                    continue;
                }
                path.append(currentDir).append(File.separator);
            }
            return path.toString();
        }
    }

}
