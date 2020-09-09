package contacts.object.utils;

import contacts.repository.PhoneBookRepository;

import java.io.*;

public class SerializationUtils {

    public static void serialize(Object object, String fileName) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.close();
    }

    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {

        File file = new File(fileName);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            ois.close();
            return object;
        } else {
            return new PhoneBookRepository();
        }
    }
}
