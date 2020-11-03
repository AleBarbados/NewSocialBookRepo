package socialBook.model;

public class BookList {

    private int id;
    private String nome;
    private int utente;
    private boolean creatore;
    private int libro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getUtente() {
        return utente;
    }

    public void setUtente(int utente) {
        this.utente = utente;
    }

    public boolean isCreatore() {
        return creatore;
    }

    public void setCreatore(boolean creatore) {
        this.creatore = creatore;
    }

    public int getLibro() {
        return libro;
    }

    public void setLibro(int libro) {
        this.libro = libro;
    }
}
