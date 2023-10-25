package it.unimib.food_alchemy.models;

public class Favorites {

    private String title;

    private String description;

    private String content;

    public Favorites() {}

    public Favorites(String title,String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Favorites(String author, String title) {
        this(author, title, null);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
