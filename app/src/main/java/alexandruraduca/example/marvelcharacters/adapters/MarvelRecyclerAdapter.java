package alexandruraduca.example.marvelcharacters.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import alexandruraduca.example.marvelcharacters.views.InfoBottomSheetDialog;
import alexandruraduca.example.marvelcharacters.R;
import alexandruraduca.example.marvelcharacters.models.MarvelCharacter;

public class MarvelRecyclerAdapter extends RecyclerView.Adapter<MarvelRecyclerAdapter.CharacterViewHolder> {

    private List<MarvelCharacter> mCharacters;
    Context context;


    public MarvelRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // creating a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_view, parent, false);

        return new CharacterViewHolder((CardView) view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, final int position) {

        holder.characterName.setText(mCharacters.get(position).getName());
        Glide.with(context)
                .load(mCharacters.get(position).getThumbnail().getPath() + "/portrait_fantastic." +
                        mCharacters.get(position).getThumbnail().getExtension())
                .into(holder.characterImage);

        //Set onClick listener
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoBottomSheetDialog bottomSheet = new InfoBottomSheetDialog();

                //passing data to fragment through Bundle
                Bundle args = new Bundle();
                args.putString("name", mCharacters.get(position).getName());
                args.putString("description", mCharacters.get(position).getDescription());
                args.putString("imgUrl", mCharacters.get(position).getThumbnail().getPath() + "/portrait_medium." +
                        mCharacters.get(position).getThumbnail().getExtension());


                bottomSheet.setArguments(args);
                //show bottom sheet dialog
                bottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(), "infoBottomSheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mCharacters!=null){
            return mCharacters.size();
        }
        return 0;
    }

    public void setCharacters(List<MarvelCharacter> characters){
        mCharacters = characters;
        notifyDataSetChanged();
    }


    public static class CharacterViewHolder extends RecyclerView.ViewHolder {

        TextView characterName;
        ImageView characterImage;
        CardView mCardView;

        public CharacterViewHolder(CardView v) {
            super(v);
            characterName = v.findViewById(R.id.characterName);
            characterImage = v.findViewById(R.id.characterPhoto);
            mCardView = v.findViewById(R.id.cardView);
        }
    }
}
