package net.bplaced.azoq.file;

import net.bplaced.azoq.*;
import java.lang.annotation.*;

public abstract class File {
    private String fileName;
    
    public File() {
        this.fileName = this.getClass().getAnnotation(Information.class).fileName();
    }
    
    public String getPath() {
        return Client.INSTANCE.getFileManager().getPath() + "/" + this.fileName + ".txt";
    }
    
    public abstract void setup();
    
    public abstract void load();
    
    public abstract void save();
    
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Information {
        String fileName();
    }
}
