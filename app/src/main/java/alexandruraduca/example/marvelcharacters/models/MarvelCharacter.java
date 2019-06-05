package alexandruraduca.example.marvelcharacters.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import alexandruraduca.example.marvelcharacters.requests.responses.Thumbnail;

public class MarvelCharacter {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;

    @SerializedName("description")
    @Expose
    private String description;

    public MarvelCharacter(Integer id, String name, Thumbnail thumbnail, String description) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public java.lang.String toString() {
        return "MarvelCharacter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail=" + thumbnail +
                ", description='" + description + '\'' +
                '}';
    }
}
