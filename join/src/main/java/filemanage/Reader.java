package filemanage;

import models.RecordFromFile;
import service.CollectionBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Reader {

    public static synchronized void read(String fileName, List<RecordFromFile> records){
        int lineNum = 0;
        CollectionBuilder cb = new CollectionBuilder();
        try (BufferedReader bf = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bf.readLine())!=null) {
                System.out.println(++lineNum);
                cb.fillArrayList(line, records);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + fileName);
        }
    }
    public static boolean ifFileExists(String fileName){
        File file = new File(fileName);
        if(!file.exists()) {
            System.out.println("Такого файла не существует");
            return false;
        }
        return true;
    }
}
