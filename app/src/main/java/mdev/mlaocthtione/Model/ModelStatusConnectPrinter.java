package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MDEV on 15/8/2559.
 */
public class ModelStatusConnectPrinter implements Parcelable {
    private String textStatus;
    private int colorText,status;
    private boolean connectComplete;

    public ModelStatusConnectPrinter(){}
    protected ModelStatusConnectPrinter(Parcel in) {
        textStatus = in.readString();
        colorText = in.readInt();
        connectComplete = in.readByte() != 0;
        status = in.readInt();
    }

    public static final Creator<ModelStatusConnectPrinter> CREATOR = new Creator<ModelStatusConnectPrinter>() {
        @Override
        public ModelStatusConnectPrinter createFromParcel(Parcel in) {
            return new ModelStatusConnectPrinter(in);
        }

        @Override
        public ModelStatusConnectPrinter[] newArray(int size) {
            return new ModelStatusConnectPrinter[size];
        }
    };

    public boolean isConnectComplete() {
        return connectComplete;
    }

    public void setConnectComplete(boolean connectComplete) {
        this.connectComplete = connectComplete;
    }

    public String getTextStatus() {
        return textStatus;
    }

    public void setTextStatus(String textStatus) {
        this.textStatus = textStatus;
    }

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textStatus);
        dest.writeInt(colorText);
        dest.writeByte((byte) (connectComplete ? 1 : 0));
        dest.writeInt(status);
    }
}
