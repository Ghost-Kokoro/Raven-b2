package net.bplaced.azoq.file.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import net.bplaced.azoq.Client;
import net.bplaced.azoq.file.File;
import net.bplaced.azoq.module.Module;

@File.Information(fileName = "Modules")
public class FileModules extends File {
    public void setup() {
    }
    
    public void load() {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(this.getPath()));
            String line;
            while ((line = br.readLine()) != null) {
                final String[] args = line.split(":");
                for (final Module m : Client.INSTANCE.getModuleManager().getModules()) {
                    if (m.getModuleName().equalsIgnoreCase(args[0])) {
                        m.setKeyBind(Integer.valueOf(args[1]));
                        m.setEnabled(Boolean.valueOf(args[2]));
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
            for (final Module m : Client.INSTANCE.getModuleManager().getModules()) {
                bw.write(m.getModuleName() + ":" + m.getKeyBind() + ":" + m.isEnabled());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (Exception ex) {}
    }
}
