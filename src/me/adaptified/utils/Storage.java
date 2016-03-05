package me.adaptified.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Storage {

    private File storageFile;
    private ArrayList<Player> MutedPlayers = new ArrayList<>();

    public Storage(File file) {
        this.storageFile = file;
        this.MutedPlayers = new ArrayList<>();

        if (storageFile.exists() == false) {
            try {
                this.storageFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void load() {
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                String line;

                while ((line = reader.readLine()) != null) {
                    if (!this.MutedPlayers.contains(player)) {
                        this.MutedPlayers.add(player);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        try {
            FileWriter stream = new FileWriter(this.storageFile);
            BufferedWriter out = new BufferedWriter(stream);

            for (Player players : this.MutedPlayers) {
                out.write(players.getName());
                out.newLine();
            }

            out.close();
            stream.close();
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean contains(Player player) {
        return this.MutedPlayers.contains(player);
    }

    public boolean add(Player player) {
        return this.MutedPlayers.add(player);
    }

    public boolean remove(Player player) {
        return this.MutedPlayers.remove(player);
    }

    public ArrayList<Player> getValues() {
        return this.MutedPlayers;
    }
}
