package net.bplaced.azoq.file.files;

import net.bplaced.azoq.file.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import net.bplaced.azoq.*;

@File.Information(fileName = "Friends")
public class FileFriends extends File {
    public void setup() {
    }
    
    public void load() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            String line;
            while ((line = br.readLine()) != null) {
                Client.INSTANCE.getFriendManager().addFriend(line);
            }
            br.close();
        }
        catch (Exception ex) {}
    }
    
    public void save() {
        try {
            this.delete();
            final BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()));
            for (int i = 0; i < Client.INSTANCE.getFriendManager().getFriends().size(); ++i) {
                bw.write(Client.INSTANCE.getFriendManager().getFriends().get(i));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex) {}
    }
    
    private void delete() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            final BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()));
            while (br.readLine() != null) {
                bw.write("");
            }
            bw.flush();
            bw.close();
            br.close();
        }
        catch (Exception ex) {}
    }
}
