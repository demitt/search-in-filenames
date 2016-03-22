package ua.ho.demitt.searchinfilenames;

import ua.ho.demitt.searchinfilenames.controller.Validator;
import ua.ho.demitt.searchinfilenames.data.Data;
import ua.ho.demitt.searchinfilenames.data.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Options {
    private File paramsFile;
    public String docsDir = null;
    public boolean searchFromStartFlag = false;
    public boolean searchInFilenamesOnlyFlag = false;

    public Result readOptions() {
        String paramsFile = Validator.isWindows() ? Data.PARAMS_WIN_FILE : Data.PARAMS_NIX_FILE;
        this.paramsFile = new File(paramsFile);
        if ( !isValidParamsFile() ) {
            String message = "Файл с настройками (" + paramsFile + ") отсутствует или недоступен для чтения.";
            return new Result(false, message);
        }
        //*** Начало метода чтения файла readParamsFile().
        try ( BufferedReader br = new BufferedReader( new FileReader(this.paramsFile) ) ) {
            String curLine, curParamName, curParamValue;
            while ( (curLine = br.readLine()) != null ) {
                if (Validator.isComment(curLine)) { //это строка комментария, пропускаем ее
                    continue;
                }
                String[] stringElemsArr = curLine.split("=");
                curParamName = stringElemsArr[0];
                curParamValue = stringElemsArr[1];
                if ( Data.Parameter.DOCS_DIR.getName().equals(curParamName) ) { //это путь к папке с документами
                    this.docsDir = curParamValue;
                } else if ( Data.Parameter.SEARCH_FROM_START_FLAG.getName().equals(curParamName) ) {
                    this.searchFromStartFlag = Data.TRUE_FLAG.equals(curParamValue);
                }  else if ( Data.Parameter.SEARCH_IN_FILENAMES_ONLY_FLAG.getName().equals(curParamName) ) {
                    this.searchInFilenamesOnlyFlag = Data.TRUE_FLAG.equals(curParamValue);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //*** Конец метода чтения файла.
        return new Result(true);

    }

    /*
    */
    private void readParamsFile() {

    }

    /*
    */
    public boolean isValidParamsFile() {
        return ( this.paramsFile.exists() && this.paramsFile.canRead() );
    }

    /*
    */
    /*public boolean isValidDocsDir() {
        return true;
    }*/

}
