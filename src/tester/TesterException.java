package tester;

public class TesterException extends Exception {
    @Override
    public String getMessage() {
        return "TesterException";
    }
}
