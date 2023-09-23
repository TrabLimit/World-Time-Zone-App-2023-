package ui.exceptions;

public class InvalidCityIndexException extends IndexOutOfBoundsException {
    private int input;

    public InvalidCityIndexException() {
        super();
    }

    public InvalidCityIndexException(String errorMsg, int input) {
        super(errorMsg);
        this.input = input;
    }

    public int getInput() {
        return this.input;
    }
}
