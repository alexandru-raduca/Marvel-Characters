package alexandruraduca.example.marvelcharacters.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import alexandruraduca.example.marvelcharacters.MarvelExecutors;
import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;
import alexandruraduca.example.marvelcharacters.requests.responses.CharacterDataWrapper;
import alexandruraduca.example.marvelcharacters.util.Constants;
import retrofit2.Call;
import retrofit2.Response;

import static alexandruraduca.example.marvelcharacters.util.Constants.NETWORK_TIMEOUT;

public class MarvelApiClient {
    private static final String TAG = "MarvelApiClient";

    private static MarvelApiClient instance;
    private SearchCharacterRunnable mSearchCharacterRunnable;
    private GetCharactersRunnable mGetCharactersRunnable;
    private SearchByInitialsRunnable mSearchByInitialsRunnable;

    private MutableLiveData<List<MarvelCharacter>> mCharacters;
    private MutableLiveData<Boolean> mMarvelRequestTimeout = new MutableLiveData<>();


    public static MarvelApiClient getInstance() {
        if (instance == null) {
            instance = new MarvelApiClient();
        }
        return instance;
    }

    private MarvelApiClient(){
        mCharacters = new MutableLiveData<>();
    }

    public LiveData<List<MarvelCharacter>> getCharacters() {
        return mCharacters;
    }
    public LiveData<Boolean> isMarvelRequestTimedOut() {
        return mMarvelRequestTimeout;
    }

    /**
     * Search character by name
     * @param name
     */
    public void searchMarvelApi(String name){
        if(mSearchCharacterRunnable != null){
            mSearchCharacterRunnable = null;
        }
        mSearchCharacterRunnable = new SearchCharacterRunnable(name);

        final Future handler = MarvelExecutors.getInstance().networkIO().submit(mSearchCharacterRunnable);


        MarvelExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know if the request to API fails or something else happens
//                mMarvelRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //searching...
    private class SearchCharacterRunnable implements Runnable{

        private String name;
        boolean cancelRequest;

        public SearchCharacterRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {

            try {
                Response<CharacterDataWrapper> response = searchCharacter(name).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MarvelCharacter> list = response.body().getCharacterDataContainer().getResults();
                    mCharacters.postValue(list);
                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mCharacters.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mCharacters.postValue(null);
            }

        }

        private Call<CharacterDataWrapper> searchCharacter(String name){
            return ServiceGenerator.getMarvelApi().searchCharacter(
                    Constants.TS,
                    Constants.API_KEY,
                    Constants.HASH,
                    name
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }

    /**
     *Get all characters
     */
    public void getMarvelApi(Integer offset){
        if(mGetCharactersRunnable != null){
            mGetCharactersRunnable = null;
        }

        mGetCharactersRunnable = new GetCharactersRunnable(offset);

        final Future handler = MarvelExecutors.getInstance().networkIO().submit(mGetCharactersRunnable);

        MarvelExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                mMarvelRequestTimeout.postValue(true);
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //getting characters...
    private class GetCharactersRunnable implements Runnable{

        Integer offset;
        boolean cancelRequest;

        public GetCharactersRunnable(Integer offset) {
            this.offset = offset;
        }

        @Override
        public void run() {

            try {
                Response<CharacterDataWrapper> response = getCharacters().execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MarvelCharacter> list = response.body().getCharacterDataContainer().getResults();
                    if(offset == 0){
                        mCharacters.postValue(list);
                    }else{
                        List<MarvelCharacter> currentCharacters = mCharacters.getValue();
                        currentCharacters.addAll(list);
                        mCharacters.postValue(currentCharacters);
                    }
                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mCharacters.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mCharacters.postValue(null);
            }

        }

        private Call<CharacterDataWrapper> getCharacters(){
            return ServiceGenerator.getMarvelApi().getCharacters(
                    Constants.TS,
                    Constants.API_KEY,
                    Constants.HASH,
                    Constants.LIMIT,
                    offset
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }

    /**
     * Search character by initials
     * @param nameStartsWith
     */
    public void searchMarvelApiByInitials(String nameStartsWith){
        if(mSearchByInitialsRunnable != null){
            mSearchByInitialsRunnable = null;
        }
        mSearchByInitialsRunnable = new SearchByInitialsRunnable(nameStartsWith);

        final Future handler = MarvelExecutors.getInstance().networkIO().submit(mSearchByInitialsRunnable);

        MarvelExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know its timed out
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    //searching...
    private class SearchByInitialsRunnable implements Runnable{

        private String nameStartsWith;
        boolean cancelRequest;

        public SearchByInitialsRunnable(String nameStartsWith) {
            this.nameStartsWith = nameStartsWith;
        }

        @Override
        public void run() {

            try {
                Response<CharacterDataWrapper> response = searchByInitials(nameStartsWith).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MarvelCharacter> list = response.body().getCharacterDataContainer().getResults();
                    mCharacters.postValue(list);
                }else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mCharacters.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mCharacters.postValue(null);
            }

        }

        private Call<CharacterDataWrapper> searchByInitials(String nameStartsWith){
            return ServiceGenerator.getMarvelApi().searchByInitials(
                    Constants.TS,
                    Constants.API_KEY,
                    Constants.HASH,
                    nameStartsWith
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }
    }
}
