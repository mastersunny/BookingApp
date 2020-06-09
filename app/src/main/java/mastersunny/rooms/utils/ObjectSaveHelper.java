package mastersunny.rooms.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import mastersunny.rooms.BookingApplication;


public class ObjectSaveHelper {

    private FileOutputStream fos;
    private ObjectOutputStream os;
    private FileInputStream fis;
    private ObjectInputStream is;
    private static volatile ObjectSaveHelper instance;
    private Context mContext;
    private String fName;
    private String TAG = "ObjectSaveHelper";

    private ObjectSaveHelper() {
        // TODO Auto-generated constructor stub
        mContext = BookingApplication.getContext();
    }

    public static ObjectSaveHelper getInstance() {
        if (instance == null)
            instance = new ObjectSaveHelper();

        return instance;
    }

    public synchronized void saveObject(String fileName, Object toSave) {
        try {

            fName = fileName;
            // removeObject(fileName);
            fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);

            os.writeObject(toSave);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public synchronized void updateObject(String fileName, Object toUpdate) {
        try {
            fis = mContext.openFileInput(fileName);
            is = new ObjectInputStream(fis);
            Object obj = is.readObject();
            if (obj != null) {
                mContext.deleteFile(fileName);
            }

            is.close();
            fis.close();
            saveObject(fileName, toUpdate);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized Object loadObject(String fileName) {
        Object obj = null;
        try {
            fis = mContext.openFileInput(fileName);
            is = new ObjectInputStream(fis);
            obj = is.readObject();

            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            obj = null;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return obj;

    }


    public void removeObject(String fileName) {
        try {
            fis = mContext.openFileInput(fileName);
            is = new ObjectInputStream(fis);
            Object obj = is.readObject();
            if (obj != null) {
                mContext.deleteFile(fileName);
            }

            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}