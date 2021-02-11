package socialbook.model.GestioneDatabase;

import java.util.Objects;

public class BookList {
    private int id;
    private String name;
    private boolean favorite;
    private String image;

    public BookList(int id, String name, boolean favorite, String image){
        this.id=id;
        this.name=name;
        this.favorite=favorite;
        this.image=image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookList bookList = (BookList) o;
        return id == bookList.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, favorite, image);
    }

    public BookList() {

    }

    public BookList(String name, boolean favorite, String image) {
        this.name=name;
        this.favorite=favorite;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
