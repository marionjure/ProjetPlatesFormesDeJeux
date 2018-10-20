package src.Exceptions;

public class JoueurDejaExistantException extends Exception {
    private String err;

    public JoueurDejaExistantException(String err){
        super();
        this.err = err;
    }


    @Override
    public String toString() {
        return err;
    }
}
