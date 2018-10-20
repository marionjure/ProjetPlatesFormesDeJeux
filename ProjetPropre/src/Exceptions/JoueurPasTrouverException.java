package src.Exceptions;

public class JoueurPasTrouverException extends Exception {
    private String err;

    public JoueurPasTrouverException(String err){
        super();
        this.err = err;
    }


    @Override
    public String toString() {
        return err;
    }
}