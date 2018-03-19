package player.model;

public class ContactModel1 {


    private String ID;
    private String poster;
    private String censor_rating;
    public boolean cast_crew;
    private String release_date;
    private String story;

    private String SreamId="";

    public String getStreamId() {
        return SreamId;
    }

    public void setStreamId(String SreamId) {
        this.SreamId = SreamId;
    }

    public String getCensor_rating() {
        return censor_rating;
    }

    public void setCensor_rating(String censor_rating) {
        this.censor_rating = censor_rating;
    }

    public boolean isCast_crew() {
        return cast_crew;
    }

    public void setCast_crew(boolean cast_crew) {
        this.cast_crew = cast_crew;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getMuviid() {
        return muviid;
    }

    public void setMuviid(String muviid) {
        this.muviid = muviid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String token;
    private String path;
    private String contentid;
    private String genere;
    private String muviid;
    private String duration;

    private String download_content_type="";

    public String getDownloadContentType() {
        return download_content_type;
    }

    public void setDownloadContentType(String download_content_type) {
        this.download_content_type = download_content_type;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }

    private String MUVIID;
    private String USERNAME;
    private String UniqueId;
    private int DOWNLOADID;

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    private int DSTATUS;
    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getMUVIID() {
        return MUVIID;
    }

    public void setMUVIID(String MUVIID) {
        this.MUVIID = MUVIID;
    }

    public int getDSTATUS() {
        return DSTATUS;
    }

    public void setDSTATUS(int DSTATUS) {
        this.DSTATUS = DSTATUS;
    }

    public int getDOWNLOADID() {
        return DOWNLOADID;
    }

    public void setDOWNLOADID(int DOWNLOADID) {
        this.DOWNLOADID = DOWNLOADID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }




    @Override
    public String toString() {
        return "ID : "+ID+",poster: "+poster+",token:"+token+",path:"+path+",contentid:"+contentid+",genere:"+genere+"," +
                ",duration:"+duration+",censor_rating:"+censor_rating+",cast_crew:"+cast_crew+",release_date:"+release_date+","+
                ",story:"+story+",MUVIID"+MUVIID+",USERNAME:"+USERNAME+",UniqueId:"+UniqueId+",DOWNLOADID:"+DOWNLOADID+","+
                ",DSTATUS:"+DSTATUS+",progress"+progress;
    }






}
