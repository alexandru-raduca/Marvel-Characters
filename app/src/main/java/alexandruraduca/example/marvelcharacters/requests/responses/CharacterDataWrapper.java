package alexandruraduca.example.marvelcharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CharacterDataWrapper {

    @SerializedName("data")
    @Expose
    private CharacterDataContainer data;

    @SerializedName("code")
    @Expose
    private Integer code;


    @SerializedName("stats")
    @Expose
    private String status;

    public CharacterDataContainer getCharacterDataContainer() {
        return data;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
