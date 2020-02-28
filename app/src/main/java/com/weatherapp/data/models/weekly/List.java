
package com.weatherapp.data.models.weekly;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.weatherapp.data.utils.DateUtils;

public class List implements Parcelable {

    private String formattedDate;
    private String maxT;
    private String humid;

    public String getMaxT() {
        return this.getMain().getTempMax().toString();
    }

    public String getHumid() {
        return this.getMain().getHumidity().toString();
    }

    public String getFormattedDate() {
        return DateUtils.parseDate(this.dtTxt);
    }

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    @SerializedName("rain")
    @Expose
    private Rain rain;
    public final static Creator<List> CREATOR = new Creator<List>() {


        @SuppressWarnings({
                "unchecked"
        })
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        public List[] newArray(int size) {
            return (new List[size]);
        }

    };

    protected List(Parcel in) {
        this.dt = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.main = ((Main) in.readValue((Main.class.getClassLoader())));
        in.readList(this.weather, (Weather.class.getClassLoader()));
        this.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
        this.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
        this.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
        this.dtTxt = ((String) in.readValue((String.class.getClassLoader())));
        this.rain = ((Rain) in.readValue((Rain.class.getClassLoader())));
    }

    public List() {
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dt);
        dest.writeValue(main);
        dest.writeList(weather);
        dest.writeValue(clouds);
        dest.writeValue(wind);
        dest.writeValue(sys);
        dest.writeValue(dtTxt);
        dest.writeValue(rain);
    }

    public int describeContents() {
        return 0;
    }

}
