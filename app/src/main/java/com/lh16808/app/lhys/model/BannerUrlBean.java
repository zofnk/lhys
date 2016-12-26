package com.lh16808.app.lhys.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 广告
 */
public class BannerUrlBean implements Parcelable {
	//图片链接
	String titlepic;
	//跳转链接
	String title;

	public String getTitlepic() {
		return titlepic;
	}

	public void setTitlepic(String titlepic) {
		this.titlepic = titlepic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public int describeContents() {
        return 0;
    }
	public BannerUrlBean(){
		
	}
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(titlepic);
        out.writeString(title);
    }

    public static final Creator<BannerUrlBean> CREATOR
            = new Creator<BannerUrlBean>() {
        public BannerUrlBean createFromParcel(Parcel in) {
            return new BannerUrlBean(in);
        }

        public BannerUrlBean[] newArray(int size) {
            return new BannerUrlBean[size];
        }
    };
    
    private BannerUrlBean(Parcel in) {
    	titlepic = in.readString();
    	title = in.readString();
    }
}
