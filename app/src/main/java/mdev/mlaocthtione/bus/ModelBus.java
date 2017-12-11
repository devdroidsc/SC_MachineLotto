package mdev.mlaocthtione.bus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MDEV on 3/2/2559.
 */
public class ModelBus implements Parcelable{
    private String msg;
    private int page;

    public ModelBus(){}
    protected ModelBus(Parcel in) {
        msg = in.readString();
        page = in.readInt();
    }

    public static final Creator<ModelBus> CREATOR = new Creator<ModelBus>() {
        @Override
        public ModelBus createFromParcel(Parcel in) {
            return new ModelBus(in);
        }

        @Override
        public ModelBus[] newArray(int size) {
            return new ModelBus[size];
        }
    };

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(msg);
        parcel.writeInt(page);
    }
}
