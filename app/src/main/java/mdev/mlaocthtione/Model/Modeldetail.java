package mdev.mlaocthtione.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Lenovo on 16-11-2017.
 */

public class Modeldetail implements Parcelable{
    public Modeldetail() {
    }

    private String number,top,button,toad;
    private String focus_top,focus_button,focus_toad,focus_number;
    private String no_focus_top,no_focus_button,no_focus_toad;
    private boolean retun_number;
    private String no_return;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getToad() {
        return toad;
    }

    public void setToad(String toad) {
        this.toad = toad;
    }

    public String getFocus_top() {
        return focus_top;
    }

    public void setFocus_top(String focus_top) {
        this.focus_top = focus_top;
    }

    public String getFocus_button() {
        return focus_button;
    }

    public void setFocus_button(String focus_button) {
        this.focus_button = focus_button;
    }

    public String getFocus_toad() {
        return focus_toad;
    }

    public void setFocus_toad(String focus_toad) {
        this.focus_toad = focus_toad;
    }

    public String getFocus_number() {
        return focus_number;
    }

    public void setFocus_number(String focus_number) {
        this.focus_number = focus_number;
    }

    public String getNo_focus_top() {
        return no_focus_top;
    }

    public void setNo_focus_top(String no_focus_top) {
        this.no_focus_top = no_focus_top;
    }

    public String getNo_focus_button() {
        return no_focus_button;
    }

    public void setNo_focus_button(String no_focus_button) {
        this.no_focus_button = no_focus_button;
    }

    public String getNo_focus_toad() {
        return no_focus_toad;
    }

    public void setNo_focus_toad(String no_focus_toad) {
        this.no_focus_toad = no_focus_toad;
    }

    public boolean isRetun_number() {
        return retun_number;
    }

    public void setRetun_number(boolean retun_number) {
        this.retun_number = retun_number;
    }

    public String getNo_return() {
        return no_return;
    }

    public void setNo_return(String no_return) {
        this.no_return = no_return;
    }

    public static Creator<Modeldetail> getCREATOR() {
        return CREATOR;
    }

    public static Comparator<Modeldetail> StuRollno = new Comparator<Modeldetail>() {

        @Override
        public int compare(Modeldetail s1, Modeldetail s2) {

           /* String StudentName1 = s1.getNumber().toUpperCase().toString();
            String StudentName2 = s2.getNumber().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);*/

            int rollno1 = Integer.parseInt(s1.getNumber());
            int rollno2 = Integer.parseInt(s2.getNumber());

            return rollno1-rollno2;

        }

    };

    protected Modeldetail(Parcel in) {
        number = in.readString();
        top = in.readString();
        button = in.readString();
        toad = in.readString();
        focus_top = in.readString();
        focus_button = in.readString();
        focus_toad = in.readString();
        focus_number = in.readString();
        no_focus_top = in.readString();
        no_focus_button = in.readString();
        no_focus_toad = in.readString();
        retun_number = in.readByte() != 0;
        no_return = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(top);
        dest.writeString(button);
        dest.writeString(toad);
        dest.writeString(focus_top);
        dest.writeString(focus_button);
        dest.writeString(focus_toad);
        dest.writeString(focus_number);
        dest.writeString(no_focus_top);
        dest.writeString(no_focus_button);
        dest.writeString(no_focus_toad);
        dest.writeByte((byte) (retun_number ? 1 : 0));
        dest.writeString(no_return);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Modeldetail> CREATOR = new Creator<Modeldetail>() {
        @Override
        public Modeldetail createFromParcel(Parcel in) {
            return new Modeldetail(in);
        }

        @Override
        public Modeldetail[] newArray(int size) {
            return new Modeldetail[size];
        }
    };
}
