package alexandruraduca.example.marvelcharacters.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;
import alexandruraduca.example.marvelcharacters.repositories.MarvelRepository;

public class MainActivityViewModel extends ViewModel {

    private MarvelRepository mRepository;
    private boolean mDidRetrieveMarvel;


    public MainActivityViewModel() {
        mRepository = MarvelRepository.getInstance();
        mDidRetrieveMarvel = false;
    }

    public LiveData<List<MarvelCharacter>> getCharacters() {
        return mRepository.getCharacters();
    }

    public LiveData<Boolean> isMarvelRequestTimedOut() {
        return mRepository.isMarvelRequestTimedOut();
    }

    public void getNextResults(){
        mRepository.getNextResults();
    }

    public void getMarvelApi(Integer offset){
        mRepository.getMarvelApi(offset);
    }

    public void searchMarvelApi(String name){
        mRepository.searchMarvelApi(name);
    }

    public void searchMarvelApiByInitials(String nameStartsWith){
        mRepository.searchMarvelApiByInitials(nameStartsWith);
    }

    public boolean didRetrieveMarvel() {
        return mDidRetrieveMarvel;
    }

    public void setRetrievedMarvel(boolean mDidRetrieveMarvel) {
        this.mDidRetrieveMarvel = mDidRetrieveMarvel;
    }
}
