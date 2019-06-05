package alexandruraduca.example.marvelcharacters.requests;

import alexandruraduca.example.marvelcharacters.requests.responses.CharacterDataWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApi {

    @GET("v1/public/characters")
    Call<CharacterDataWrapper> getCharacters(@Query("ts")Integer  ts,
                                             @Query("apikey") String apikey,
                                             @Query("hash") String hash,
                                             @Query("limit") Integer limit,
                                             @Query("offset") Integer offset);

    @GET("v1/public/characters")
    Call<CharacterDataWrapper> searchCharacter(@Query("ts")Integer  ts,
                                                 @Query("apikey") String apikey,
                                                 @Query("hash") String hash,
                                                 @Query("name") String name);

    @GET("v1/public/characters")
    Call<CharacterDataWrapper> searchByInitials(@Query("ts")Integer  ts,
                                               @Query("apikey") String apikey,
                                               @Query("hash") String hash,
                                               @Query("nameStartsWith") String nameStartsWith);
}
