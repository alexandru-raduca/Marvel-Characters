package alexandruraduca.example.marvelcharacters.requests;

import alexandruraduca.example.marvelcharacters.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static MarvelApi marvelApi = retrofit.create(MarvelApi.class);

    public static MarvelApi getMarvelApi() {
        return marvelApi;
    }
}


