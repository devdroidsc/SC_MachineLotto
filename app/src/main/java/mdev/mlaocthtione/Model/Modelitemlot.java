package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 07-12-2017.
 */

public class Modelitemlot implements Parcelable{
    private String numberlot,statuslot;

    public Modelitemlot() {
    }

    protected Modelitemlot(Parcel in) {
        numberlot = in.readString();
        statuslot = in.readString();
    }

    public static final Creator<Modelitemlot> CREATOR = new Creator<Modelitemlot>() {
        @Override
        public Modelitemlot createFromParcel(Parcel in) {
            return new Modelitemlot(in);
        }

        @Override
        public Modelitemlot[] newArray(int size) {
            return new Modelitemlot[size];
        }
    };

    public String getNumberlot() {
        return numberlot;
    }

    public void setNumberlot(String numberlot) {
        this.numberlot = numberlot;
    }

    public String getStatuslot() {
        return statuslot;
    }

    public void setStatuslot(String statuslot) {
        this.statuslot = statuslot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(numberlot);
        parcel.writeString(statuslot);
    }
}
