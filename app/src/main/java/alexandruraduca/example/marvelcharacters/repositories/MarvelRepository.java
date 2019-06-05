package alexandruraduca.example.marvelcharacters.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import alexandruraduca.example.marvelcharacters.requests.MarvelApiClient;
import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;
import alexandruraduca.example.marvelcharacters.util.Constants;

/**
 * Singleton pattern
 */
public class MarvelRepository {

    private static final String TAG = "MarvelCharacterReposito";

    private static MarvelRepository instance = null;
    private MarvelApiClient mMarvelApiClient;
    private Integer mOffset;

    public static MarvelRepository getInstance() {
        if (instance == null) instance = new MarvelRepository();
        return instance;
    }

    private MarvelRepository() {
        mMarvelApiClient = MarvelApiClient.getInstance();
    }

    public LiveData<List<MarvelCharacter>> getCharacters() {
        return mMarvelApiClient.getCharacters();
    }

    public LiveData<Boolean> isMarvelRequestTimedOut() {
        return mMarvelApiClient.isMarvelRequestTimedOut();
    }

    public void getNextResults(){
        getMarvelApi(mOffset+Constants.MAX_OFFSET);
    }

    public void getMarvelApi(Integer offset){
        mOffset = offset;
        mMarvelApiClient.getMarvelApi(offset);
    }

    public void searchMarvelApi(String name){
        mMarvelApiClient.searchMarvelApi(name);
    }

    public void searchMarvelApiByInitials(String nameStartsWith){
        mMarvelApiClient.searchMarvelApiByInitials(nameStartsWith);
    }
}

