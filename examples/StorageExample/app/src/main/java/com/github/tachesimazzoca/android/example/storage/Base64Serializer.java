package com.github.tachesimazzoca.android.example.storage;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Base64Serializer {
    public static Object unserialize(String serialized)
            throws IOException, ClassNotFoundException {
        byte[] data = Base64.decode(serialized, Base64.DEFAULT);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object obj = ois.readObject();
        ois.close();
        return obj;
    }

    public static String serialize(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
    }
}
