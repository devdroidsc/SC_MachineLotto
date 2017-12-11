package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KHUNTONGDANG on 24/4/2560.
 */

public class ModelDataPrinter implements Parcelable {
    private String sName,sIp;
    private int TypeContent;
    public ModelDataPrinter(){}
    protected ModelDataPrinter(Parcel in) {
        sName = in.readString();
        sIp = in.readString();
    }

    public static final Creator<ModelDataPrinter> CREATOR = new Creator<ModelDataPrinter>() {
        @Override
        public ModelDataPrinter createFromParcel(Parcel in) {
            return new ModelDataPrinter(in);
        }

        @Override
        public ModelDataPrinter[] newArray(int size) {
            return new ModelDataPrinter[size];
        }
    };

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsIp() {
        return sIp;
    }

    public void setsIp(String sIp) {
        this.sIp = sIp;
    }

    public int getTypeContent() {
        return TypeContent;
    }

    public void setTypeContent(int typeContent) {
        TypeContent = typeContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sName);
        dest.writeString(sIp);
    }
}
