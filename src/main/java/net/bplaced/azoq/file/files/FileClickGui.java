package net.bplaced.azoq.file.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import net.bplaced.azoq.file.File;
import net.bplaced.azoq.module.Module;


@File.Information(fileName = "ClickGui")
public class FileClickGui extends File {
    public static List<String> clickGui;
    
    public void setup() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            int i = 0;
            while (br.readLine() != null) {
                ++i;
            }
            br.close();
            if (i == Module.Category.values().length) {
                return;
            }
            final BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()));
            int x = 5;
            for (final Module.Category c : Module.Category.values()) {
                bw.write(c.toString() + ":" + x + ":" + "5" + ":" + "false");
                bw.newLine();
                x += 100;
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex) {}
    }
    
    public void load() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            int i = 0;
            String line;
            while ((line = br.readLine()) != null) {
                final String[] args = line.split(":");
                FileClickGui.clickGui.add(args[0] + ":" + args[1] + ":" + args[2] + ":" + args[3]);
                ++i;
            }
            br.close();
        }
        catch (Exception ex) {}
    }
    
    public void save() {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()));
            for (int i = 0; i < FileClickGui.clickGui.size(); ++i) {
                final String args = FileClickGui.clickGui.get(i);
                bw.write(args);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex) {}
    }
    
    static {
        FileClickGui.clickGui = new ArrayList<String>();
    }
}
