package com.mygame.flyingfoxtwo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.mygame.framework.FileIO;


/**
 * The Settings class provides an higher level of abstraction to the reading and writing of application states to the permanent storage.
 */
public class Settings {
    
    /** A flag to indicate weather sound is enabled. this variable will be used to read and write sound settings for this game */
    public static boolean soundEnabled = true;
    
    /** An array of integers, holds five highscores.  */
    public static int[] highscores = new int[] { 50, 40, 30, 20, 10 };

    /**
     * This method reads a file. In this game it will read the settings file to retrieve previous highscores and sound setting.  
     * The load method will load highscores array with last five highscores.
     *
     * @param files, FileIO reference object 
     */
    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".flyfox")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
            }
        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * This method will write current sound status and highscores back to the storage file.
     *
     * @param files, FileIO reference object 
     */
    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".flyfox")));
            out.write(Boolean.toString(soundEnabled));
            out.write("\n");
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]));
                out.write("\n");
            }

        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * This method will check if the passed integer value is higher than one of the highscores then it will be replaced by passed value. 
     *
     * @param score, value to check and add to highscores.
     */
    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}
