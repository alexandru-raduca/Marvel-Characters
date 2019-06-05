package alexandruraduca.example.marvelcharacters.util;

import android.util.Log;

import java.util.List;

import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;

public class Testing {

    public static void printRecipes(List<MarvelCharacter> list, String tag){
        for(MarvelCharacter character : list){
            Log.d(tag, "onChanged: "+character.getName() + " - id "+character.getId());
        }
    }
}
