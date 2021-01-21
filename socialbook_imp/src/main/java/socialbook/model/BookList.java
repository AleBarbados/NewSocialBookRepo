package socialbook.model;

public class BookList {
    private int id;
    private String name;
    private int favorite;
    private String image;

    public BookList(int id, String name, int favorite, String image){
        this.id=id;
        this.name=name;
        this.favorite=favorite;
        this.image=image;
    }

    public BookList() {

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

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
