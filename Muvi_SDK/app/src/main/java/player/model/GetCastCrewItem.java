package player.model;

/**
 * Created by Muvi on 23-Jan-17.
 */

public class GetCastCrewItem {

    String title;
    String desc;
    String castImage;

    public GetCastCrewItem(String title, String desc, String castImage) {
        this.title = title;
        this.desc = desc;
        this.castImage = castImage;
    }

    public String getCastImage() {
        return castImage;
    }

    public void setCastImage(String castImage) {
        this.castImage = castImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
