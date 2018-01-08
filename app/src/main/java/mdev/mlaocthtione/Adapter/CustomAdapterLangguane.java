package mdev.mlaocthtione.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import mdev.mlaocthtione.Model.Modeldetail;
import mdev.mlaocthtione.Model.ModelitemLanguane;
import mdev.mlaocthtione.R;

/**
 * Created by Lenovo on 03-01-2018.
 */

public class CustomAdapterLangguane  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ModelitemLanguane> list;
    private Context context;
    private onItemClickNo onItemClickNo;

    public CustomAdapterLangguane(ArrayList<ModelitemLanguane> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_page_language,parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ModelitemLanguane modelitemLanguane = list.get(position);
        ItemView itemView = (ItemView) holder;
        itemView.text_language.setText(modelitemLanguane.getTitle());

        itemView.ic_use_languane.setVisibility(View.GONE);
        Glide.with(context)
                .load(modelitemLanguane.getUrl())
                .into(itemView.ic_language);

        if (modelitemLanguane.getCheck_use().equals("1")){
            itemView.ic_use_languane.setVisibility(View.VISIBLE);
        }
        itemView.liner_Languane_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickNo.onItemClickNo(position,"1",modelitemLanguane.getFile(),modelitemLanguane.getKeys());


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onItemClickNo {
        public void onItemClickNo(int position, String check,String url_file,String key_langane);
    }
    public void setOnItemClickNo(onItemClickNo onItemClickNo){
        this.onItemClickNo = onItemClickNo;
    }
    public class ItemView extends RecyclerView.ViewHolder {

        private TextView text_language;
        private ImageView ic_language,ic_use_languane;
        private LinearLayout liner_Languane_click;

        public ItemView(View itemView) {
            super(itemView);
            text_language = itemView.findViewById(R.id.text_language);
            ic_language = itemView.findViewById(R.id.ic_language);
            ic_use_languane = itemView.findViewById(R.id.ic_use_languane);
            liner_Languane_click = itemView.findViewById(R.id.liner_Languane_click);
        }
    }
}
