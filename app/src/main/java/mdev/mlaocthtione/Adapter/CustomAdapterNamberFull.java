package mdev.mlaocthtione.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mdev.mlaocthtione.Model.ModelitemNumberFull;
import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 25-12-2017.
 */

public class CustomAdapterNamberFull extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<ModelitemNumberFull> list;

    public CustomAdapterNamberFull(Context context, ArrayList<ModelitemNumberFull> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_number_header_full,parent,false);
        return new itemview(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ModelitemNumberFull modelitemNumberFull = list.get(position);
        itemview itemviews = (itemview) holder;
        itemviews.tv_head_full.setText(modelitemNumberFull.getHeader());
        itemviews.tv_detail_full.setText(modelitemNumberFull.getDetail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class itemview extends RecyclerView.ViewHolder{

        private TextView tv_head_full,tv_detail_full;
        public itemview(View itemView) {
            super(itemView);
            tv_head_full = itemView.findViewById(R.id.tv_head_full);
            tv_detail_full = itemView.findViewById(R.id.tv_detail_full);
        }
    }
}
