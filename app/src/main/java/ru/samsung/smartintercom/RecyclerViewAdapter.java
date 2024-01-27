package ru.samsung.smartintercom;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.samsung.smartintercom.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final ArrayList<Call> mData;
    private final LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.callTime);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<Call> dataSet) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = dataSet;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int index) {
        Call item = mData.get(index);
        TextView time = holder.itemView.findViewById(R.id.callTime);
        time.setText(item.time);

        TextView result = holder.itemView.findViewById(R.id.callStatus);
        result.setText(item.stat);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

