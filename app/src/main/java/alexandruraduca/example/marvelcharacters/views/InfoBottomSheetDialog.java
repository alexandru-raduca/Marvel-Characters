package alexandruraduca.example.marvelcharacters.views;

import android.app.Dialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import alexandruraduca.example.marvelcharacters.R;


public class InfoBottomSheetDialog extends BottomSheetDialogFragment {

    private ImageView characterPhoto;
    private TextView characterName;
    private TextView characterDescription;

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        characterPhoto = v.findViewById(R.id.fragmentPhoto_id);
        characterName = v.findViewById(R.id.fragmentName_id);
        characterDescription = v.findViewById(R.id.fragmentDescription_id);

        Bundle args = getArguments();
        characterName.setText(args.getString("name"));
        Glide.with(this).load(args.getString("imgUrl")).into(characterPhoto);
        characterDescription.setText(args.getString("description"));
        characterDescription.setMovementMethod(new ScrollingMovementMethod());

        return v;
    }
}
