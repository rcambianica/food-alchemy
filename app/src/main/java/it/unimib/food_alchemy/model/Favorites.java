package it.unimib.food_alchemy.model;

import androidx.room.Entity;

@Entity(tableName = "favorites")
public class Favorites {

    private String title;

    private String description;

    private String content;

    public Favorites(String title,String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
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
        return "Favorite{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
