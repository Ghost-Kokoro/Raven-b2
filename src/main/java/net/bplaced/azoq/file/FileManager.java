package net.bplaced.azoq.file;

import java.util.ArrayList;
import java.util.List;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.file.files.FileClickGui;
import net.bplaced.azoq.file.files.FileFriends;
import net.bplaced.azoq.file.files.FileModules;
import net.bplaced.azoq.file.files.FileValues;

public class FileManager {
    private List<File> files;
    
    public FileManager() {
        (this.files = new ArrayList<File>()).add(new FileClickGui());
        this.files.add(new FileFriends());
        this.files.add(new FileModules());
        this.files.add(new FileValues());
    }
    
    public String getPath() {
        return Client.PATH;
    }
    
    public File getFile(final Class file) {
        for (final File f : this.files) {
            if (f.getClass() == file) {
                return f;
            }
        }
        return null;
    }
    
    public List<File> getFiles() {
        return this.files;
    }
    
    public void setup() {
        try {
            for (final File f : Client.INSTANCE.getFileManager().getFiles()) {
                final java.io.File file = new java.io.File(f.getPath());
                if (!file.exists()) {
                    file.createNewFile();
                }
            }
            for (final File f : Client.INSTANCE.getFileManager().getFiles()) {
                f.setup();
                f.load();
            }
        }
        catch (Exception ex) {}
    }
}
