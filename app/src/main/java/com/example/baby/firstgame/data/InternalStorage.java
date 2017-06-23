package com.example.baby.firstgame.data;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Saves or loads an object to the Internal storage of the device
 */
public final class InternalStorage{

    private InternalStorage() {}

    /**
     * saves the given Object to the internal storage
     * @param context
     * @param key
     * @param object
     * @throws IOException
     */
    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    /**
     * reads an object from the internal storage and returns it.
     * @param context
     * @param key
     * @return object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        fis.close();
        ois.close();
        return object;
    }
}