package socialbook.model;

import java.util.Objects;

public class Author {
    private int id;
    private String name;
    private String surname;

    public Author() { }

    public Author(String n, String s) {
        name = n;
        surname = s;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String s) {
        surname = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.name) && Objects.equals(surname, author.surname);
    }
}
