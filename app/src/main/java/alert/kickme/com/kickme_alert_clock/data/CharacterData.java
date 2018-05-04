package alert.kickme.com.kickme_alert_clock.data;


import java.io.File;
import java.io.Serializable;

import alert.kickme.com.kickme_alert_clock.database.BaseSqlite;


public class CharacterData implements Serializable, BaseSqlite.SaverObject {

    private int id;
    private final String name;
    private final File iconImage;
    private File gifImage;
    private File soundGif;



    //--------------------------
    //--------Constructor-------
    //--------------------------

    public CharacterData(String name, File iconImage){
         this(0,name,iconImage,null,null);
    }

    public CharacterData(int id, String name, File iconImage, File gifImage, File soundGif) {
        this.id = id;
        this.name = name;
        this.iconImage = iconImage;
        this.gifImage = gifImage;
        this.soundGif = soundGif;
    }

    //--------------------------
    //----------Getter----------
    //--------------------------

    @Override
    public int getPrimaryId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public File getIconImage() {
        return iconImage;
    }

    public File getGifImage() {
        return gifImage;
    }

    public File getSoundGif() {
        return soundGif;
    }


    //--------------------------
    //----------Setter----------
    //--------------------------

    public void setId(int id) {
        this.id = id;
    }

    public void setGifImage(File gifImage) {
        this.gifImage = gifImage;
    }

    public void setSoundGif(File soundGif) {
        this.soundGif = soundGif;
    }

    //--------------------------
    //---------Override---------
    //--------------------------

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + name.hashCode();

        if (iconImage != null){
            result = 31 * result + iconImage.hashCode();
        }else{
            result = 31 * result + result;
        }

        if (gifImage != null){
            result = 31 * result + gifImage.hashCode();
        }else{
            result = 31 * result + result;
        }

        if (soundGif != null){
            result = 31 * result + soundGif.hashCode();
        }else{
            result = 31 * result + result;
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CharacterData)) return false;
        if (obj == this) return true;
        CharacterData otherCharacter = ((CharacterData) obj);
        return this.name.equals(otherCharacter.name);
    }

    @Override
    public String toString() {
        return name;
    }

}
