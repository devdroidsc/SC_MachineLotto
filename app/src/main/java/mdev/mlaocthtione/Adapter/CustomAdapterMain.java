package mdev.mlaocthtione.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.Modeldetail;
import mdev.mlaocthtione.ModelBus.Onclickmain;
import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 16-11-2017.
 */

public class CustomAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Modeldetail> modeldetails;
    private Context context;
    private AllCommand allCommand = new AllCommand();

    public CustomAdapterMain(List<Modeldetail> modeldetails, Context context) {
        this.modeldetails = modeldetails;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_detail_lot,parent,false);
        return new ViewItemHoder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Modeldetail modellist = modeldetails.get(position);

        ViewItemHoder viewitem = (ViewItemHoder) holder;
        viewitem.textnumber.setText(modellist.getNumber());
        viewitem.textnumber.setSelected(true);
        viewitem.texttop.setText(modellist.getTop());
        viewitem.texttop.setSelected(true);
        viewitem.textbutton.setText(modellist.getButton());
        viewitem.textbutton.setSelected(true);
        viewitem.texttoad.setText(modellist.getToad());
        viewitem.texttoad.setSelected(true);
        viewitem.tv_return_number.setText(allCommand.GetStringShare(context,allCommand.text_returns,"Back"));

        viewitem.Liner_Number.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        viewitem.Liner_Top.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        viewitem.Liner_Lower.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        viewitem.Liner_Toad.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        if (modellist.getFocus_number().equals("1")&&modellist.getNumber().length()<=0){

            viewitem.Liner_Number.setBackgroundResource(R.drawable.bg_item_focust);

        }else if (modellist.getFocus_top().equals("1")){

            viewitem.Liner_Top.setBackgroundResource(R.drawable.bg_item_focust);

        }else if (modellist.getFocus_button().equals("1")){

            viewitem.Liner_Lower.setBackgroundResource(R.drawable.bg_item_focust);

        }else if (modellist.getFocus_toad().equals("1")){

            viewitem.Liner_Toad.setBackgroundResource(R.drawable.bg_item_focust);

        }

        if (modellist.getNo_focus_button().equals("1")){
            viewitem.Liner_Lower.setBackgroundResource(R.drawable.bg_item_un_select);

        }
        if (modellist.getNo_focus_toad().equals("1")){
            viewitem.Liner_Toad.setBackgroundResource(R.drawable.bg_item_un_select);

        }
        if (modellist.getNo_focus_top().equals("1")){

            viewitem.Liner_Top.setBackgroundResource(R.drawable.bg_item_un_select);
        }

        if (position % 2 == 1)
            viewitem.Liner_lot.setBackgroundResource(R.drawable.bg_item_lot_f);
        else
            viewitem.Liner_lot.setBackgroundResource(R.drawable.bg_item_lot_l);


        if (!modellist.isRetun_number()){
            viewitem.return_number.setVisibility(View.GONE);
            viewitem.number_return.setVisibility(View.GONE);
        }else {
            viewitem.return_number.setVisibility(View.VISIBLE);
            viewitem.number_return.setVisibility(View.VISIBLE);
            if (modellist.getNumber().length()<=2){
                viewitem.number_return.setVisibility(View.INVISIBLE);
            }
            if (modellist.getNo_return().equals("6")){
                viewitem.number_return.setVisibility(View.VISIBLE);
                viewitem.number_return.setText("6");
            }else if (modellist.getNo_return().equals("3")){
                viewitem.number_return.setVisibility(View.VISIBLE);
                viewitem.number_return.setText("3");
            }
        }

    }

    public void removeItem(int position) {
        modeldetails.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, modeldetails.size());
    }

    @Override
    public int getItemCount() {
        return modeldetails.size();
    }

    static class ViewItemHoder extends RecyclerView.ViewHolder {

        private TextView textnumber,texttop,textbutton,texttoad;
        private LinearLayout Liner_Top,Liner_Toad,Liner_Lower,Liner_Number,Liner_lot;
        private LinearLayout return_number;
        private TextView number_return,tv_return_number;

        public ViewItemHoder(View itemView) {
            super(itemView);
            textnumber = itemView.findViewById(R.id.textnumber);
            texttop = itemView.findViewById(R.id.texttop);
            textbutton = itemView.findViewById(R.id.textbutton);
            texttoad = itemView.findViewById(R.id.texttoad);

            Liner_Top = itemView.findViewById(R.id.Liner_Top);
            Liner_Toad = itemView.findViewById(R.id.Liner_Toad);
            Liner_Lower = itemView.findViewById(R.id.Liner_Lower);
            Liner_Number = itemView.findViewById(R.id.Liner_Number);
            Liner_lot = itemView.findViewById(R.id.Liner_lot);

            return_number = itemView.findViewById(R.id.return_number);
            number_return = itemView.findViewById(R.id.number_return);
            tv_return_number = itemView.findViewById(R.id.tv_return_number);
        }
    }
}
