package alexandruraduca.example.marvelcharacters;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MarvelExecutors {

    private static MarvelExecutors instance;

    public static MarvelExecutors getInstance(){
        if(instance == null) {
            instance = new MarvelExecutors();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}
