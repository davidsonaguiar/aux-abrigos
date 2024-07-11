package controllers.exceptions;

public class ItemCreationCancelledException extends RuntimeException {
    public ItemCreationCancelledException() {
        super("Processo de criação de item cancelado");
    }
}