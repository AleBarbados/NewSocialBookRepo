package socialbook.utility;

import java.sql.SQLIntegrityConstraintViolationException;

public class BookAlreadyInsertException extends Throwable {
    public BookAlreadyInsertException(SQLIntegrityConstraintViolationException sicve) {
        super("Libro già in carrello");
    }
}
