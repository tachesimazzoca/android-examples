package com.github.tachesimazzoca.android.example.storage;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public final class IOUtils {
    public static String readString(File f) {
        String str = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            str = readString(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fis);
        }
        if (null == str)
            str = "";
        return str;
    }

    public static String readString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(is, Charset.defaultCharset());
            int n = 0;
            char[] buffer = new char[64];
            while (-1 != (n = isr.read(buffer))) {
                sb.append(buffer, 0, n);
            }
        } catch (IOException e) {
            // Fail gracefully.
            e.printStackTrace();
        } finally {
            closeQuietly(isr);
        }
        return sb.toString();
    }

    public static void writeString(String str, File f) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            writeString(str, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fos);
        }
    }

    public static void writeString(String str, OutputStream os) {
        try {
            os.write(str.getBytes(Charset.defaultCharset()));
        } catch (IOException e) {
            // Fail gracefully.
            e.printStackTrace();
        } finally {
            closeQuietly(os);
        }
    }

    public static void closeQuietly(Closeable obj) {
        if (null != obj) {
            try {
                obj.close();
            } catch (IOException e) {
                // Close quietly
            }
        }
    }
}
