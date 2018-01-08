package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lenovo on 03-01-2018.
 */

public class ModelitemLanguane implements Parcelable{

    private String title,keys,file,url;
    private String check_use;

    public ModelitemLanguane() {
    }

    protected ModelitemLanguane(Parcel in) {
        title = in.readString();
        keys = in.readString();
        file = in.readString();
        url = in.readString();
        check_use = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(keys);
        dest.writeString(file);
        dest.writeString(url);
        dest.writeString(check_use);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ModelitemLanguane> CREATOR = new Creator<ModelitemLanguane>() {
        @Override
        public ModelitemLanguane createFromParcel(Parcel in) {
            return new ModelitemLanguane(in);
        }

        @Override
        public ModelitemLanguane[] newArray(int size) {
            return new ModelitemLanguane[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCheck_use() {
        return check_use;
    }

    public void setCheck_use(String check_use) {
        this.check_use = check_use;
    }
}
