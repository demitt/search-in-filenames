package ua.ho.demitt.searchinfilenames.data;

public class Result {

    private final boolean result;
    private final String message;

    public Result(boolean result) {
        this(result, null);
    }

    public Result(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isGood() {
        return this.result;
    }

    public String getMessage() {
        return this.message;
    }

}
