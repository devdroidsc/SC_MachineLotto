package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 25-12-2017.
 */

public class ModelitemNumberFull implements Parcelable{

    private String Header,Detail;

    public ModelitemNumberFull() {
    }

    protected ModelitemNumberFull(Parcel in) {
        Header = in.readString();
        Detail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Header);
        dest.writeString(Detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelitemNumberFull> CREATOR = new Creator<ModelitemNumberFull>() {
        @Override
        public ModelitemNumberFull createFromParcel(Parcel in) {
            return new ModelitemNumberFull(in);
        }

        @Override
        public ModelitemNumberFull[] newArray(int size) {
            return new ModelitemNumberFull[size];
        }
    };

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
