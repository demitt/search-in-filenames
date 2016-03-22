package ua.ho.demitt.searchinfilenames.data;

public class Data {

    //Первый аргумент командной строки, к-рый указываем при запуске через BAT-ник *на винде*:
    public static final String FIRST_ARG_WINDOWS = "windows";
    //Кодировка вводимой строки для винды:
    public static final String INPUT_ENCODING_WINDOWS = "IBM866";

    //Файл настроек: относительный путь к файлу:
    public static final String PARAMS_WIN_FILE = "params.ini";
    public static final String PARAMS_NIX_FILE = "params.nix.ini";

    //Файл настроек: если этот символ является первым в строке, то строка считается комментарием:
    public static final char COMMENT_MARKER = '#';
    //Файл настроек: значение переменных, соответствующее булевому true:
    public static final String TRUE_FLAG = "1";

    //Файл настроек: описание переменных:
    public enum Parameter {
        DOCS_DIR("docs_dir"),
        SEARCH_FROM_START_FLAG("searchFromStart_flag"),
        SEARCH_IN_FILENAMES_ONLY_FLAG("searchInFilenamesOnly_flag")
        ;
        private final String name; //имя переменной в файле
        Parameter(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
    }

    //Литералы:
    public enum Literal { //м.б. НАДО сделать через enum?
        ERR_NOTARGETDIRPATH("Не найден путь к целевой папке (см. файл настроек)"),
        INF_ONLYFILENAMES("Имена папок не смотрим"),
        INF_NOSPACES("Пробелы я ищу только по пятницам с 00:00 до 03:17."),
        LIT_FOUNDENTRYMARKER(" - "),
        LIT_FOUNDENTRYPATHMARKER(" --> ")
        ;
        private final String value;

        Literal(String value) {
            this.value = value;
        }

        public String getVal() {
            return this.value;
        }

    }


}