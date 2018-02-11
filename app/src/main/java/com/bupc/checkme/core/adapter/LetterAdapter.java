package com.bupc.checkme.core.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bupc.checkme.R;
import com.bupc.checkme.core.model.Item;
import com.bupc.checkme.core.model.SimpleString;
import com.bupc.checkme.core.widget.FontedTextView;

import java.util.List;

import timber.log.Timber;

/**
 * Created by casjohnpaul on 1/8/2018.
 */

public class LetterAdapter extends RecyclerView.Adapter<LetterAdapter.BaseViewHolder> {


    private LayoutInflater inflater;

    private Context context;
    private List<SimpleString> letters;

    private OnItemClickListener listener;

    int colorCtr = 0;
    private int[] colors = {
        R.color.red, R.color.pink, R.color.purple, R.color.deep_purple, R.color.indigo, R.color.blue, R.color.light_blue,
        R.color.cyan, R.color.teal, R.color.green, R.color.light_green, R.color.lime, R.color.orange, R.color.deep_orange
    };

    public LetterAdapter(Context context, List<SimpleString> letters, OnItemClickListener listener) {

        inflater = LayoutInflater.from(context);

        this.context = context;
        this.letters = letters;
        this.listener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.item_letter, parent, false);
        return new LetterHolder(row);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof LetterHolder) {
            ((LetterHolder)holder).bind(letters.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }


    public class LetterHolder extends BaseViewHolder implements View.OnClickListener {
        FontedTextView ftvLetter;
        RelativeLayout rlLetterBackground;

        public LetterHolder(View view) {
            super(view);
            ftvLetter = (FontedTextView) view.findViewById(R.id.ftvLetter);
            rlLetterBackground = view.findViewById(R.id.rlLetterBackground);
            view.setOnClickListener(this);
        }

        @Override
        public void bind(Item item) {
            if (colorCtr == 14) {
                colorCtr = 0;
            }
            SimpleString simpleString = (SimpleString) item;
            ftvLetter.setText(simpleString.toString());

            rlLetterBackground.setBackgroundColor(ContextCompat.getColor(context, colors[colorCtr]));

            colorCtr ++;
            Timber.e("color counter %s", colorCtr);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View view) {
            super(view);
        }

        public abstract void bind(Item view);

    }


    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

}
