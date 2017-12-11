package mdev.mlaocthtione.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mdev.mlaocthtione.Manager.AllCommand;
import mdev.mlaocthtione.Model.ModelDataPrinter;
import mdev.mlaocthtione.R;

public class CustomAdapterPrinter extends BaseAdapter {
    private AllCommand allCommand;
	private Context mContext;
	private List<ModelDataPrinter> mDataSet;
	public CustomAdapterPrinter(Context context, List<ModelDataPrinter> mDataSet){
        this.allCommand = new AllCommand();
		this.mContext = context;
		this.mDataSet = mDataSet;
	}

	@Override
	public int getCount() {
		return mDataSet.size();
	}

	@Override
	public ModelDataPrinter getItem(int position) {
		return mDataSet.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		//return super.getViewTypeCount();
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		return getItem(position).getTypeContent();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ModelDataPrinter printer = getItem(position);
		int viewType = getItemViewType(position);
		switch (viewType){
			case 0:
				convertView = inflatePrinter(convertView, parent, printer ,position);
				break;
		}
		return convertView;
	}
	private View inflatePrinter(View convertView, ViewGroup parent, ModelDataPrinter printer, int position) {
		ViewHolderPrinter viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_printer, parent, false);
			viewHolder = new ViewHolderPrinter(convertView);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolderPrinter) convertView.getTag();
		}
		viewHolder.tvItemNamePrinter.setText(printer.getsName());
		viewHolder.tvItemIPPrinter.setText(printer.getsIp());
		viewHolder.lnContentItemPrinter.setBackgroundResource(R.color.bg_item_first);
		if (position % 2 ==0){
			viewHolder.lnContentItemPrinter.setBackgroundResource(R.color.bg_item_one);
		}else {
			viewHolder.lnContentItemPrinter.setBackgroundResource(R.color.bg_item_two);
		}

		return convertView;
	}

	static class ViewHolderPrinter {
		private TextView tvItemNamePrinter;
		private TextView tvItemIPPrinter;
		private LinearLayout lnContentItemPrinter;

		public ViewHolderPrinter(View rootView) {
			this.tvItemNamePrinter = (TextView) rootView.findViewById(R.id.tvItemNamePrinter);
			this.tvItemIPPrinter = (TextView) rootView.findViewById(R.id.tvItemIPPrinter);
			this.lnContentItemPrinter = (LinearLayout) rootView.findViewById(R.id.lnContentItemPrinter);
		}
	}
}
