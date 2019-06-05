package alexandruraduca.example.marvelcharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;

public class CharacterDataContainer {

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("results")
    @Expose
    private ArrayList<MarvelCharacter> results;

    public Integer getTotal() {
        return total;
    }

    public ArrayList<MarvelCharacter> getResults() {
        return results;
    }
}
