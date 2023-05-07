package net.bplaced.azoq.file.files;

import net.bplaced.azoq.file.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import net.bplaced.azoq.*;
import net.bplaced.azoq.gui.click.settings.*;

@File.Information(fileName = "Values")
public class FileValues extends File {
    public void setup() {
    }
    
    public void load() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            String line;
            while ((line = br.readLine()) != null) {
                final String[] args = line.split(":");
                for (final Setting s : Client.INSTANCE.getSettingsManager().getSettings()) {
                    if (s.getName().equalsIgnoreCase(args[0] + ":" + args[1])) {
                        if (s.isCombo()) {
                            s.setValString(args[2]);
                        }
                        else if (s.isCheck()) {
                            s.setValBoolean(Boolean.valueOf(args[2]));
                        }
                        else {
                            if (!s.isSlider()) {
                                continue;
                            }
                            s.setValDouble(Double.valueOf(args[2]));
                        }
                    }
                }
            }
            br.close();
        }
        catch (Exception ex) {}
    }
    
    public void save() {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(this.getPath()));
            for (final Setting s : Client.INSTANCE.getSettingsManager().getSettings()) {
                if (s.isCombo()) {
                    bw.write(s.getName() + ":" + s.getValString());
                    bw.newLine();
                }
                else if (s.isCheck()) {
                    bw.write(s.getName() + ":" + s.getValBoolean());
                    bw.newLine();
                }
                else {
                    if (!s.isSlider()) {
                        continue;
                    }
                    bw.write(s.getName() + ":" + s.getValDouble());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex) {}
    }
}
