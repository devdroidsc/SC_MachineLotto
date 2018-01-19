package mdev.mlaocthtione.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import mdev.mlaocthtione.Model.Modelitemlot;
import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 06-12-2017.
 */

public class CustomAdapterDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Modelitemlot> list;
    private Context context;

    public CustomAdapterDetail(ArrayList<Modelitemlot> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_lot_detail,parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Modelitemlot modelitemlot = list.get(position);
        ItemView itemView = (ItemView) holder;
        itemView.text_lotnumber.setTextColor(Color.parseColor("#000000"));
        if (modelitemlot.getStatuslot().equals("1")) {
            itemView.text_lotnumber.setText(modelitemlot.getNumberlot());
            itemView.text_lotnumber.setTextColor(Color.parseColor("#026f02"));
        }else {
            itemView.text_lotnumber.setText(modelitemlot.getNumberlot());
            itemView.text_lotnumber.setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{

        private TextView text_lotnumber;
        public ItemView(View itemView) {
            super(itemView);
            text_lotnumber = itemView.findViewById(R.id.text_lotnumber);
        }
    }

}
