import filemanage.Reader;
import models.RecordFromFile;
import service.CollectionBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<RecordFromFile> recordsFromArrayListOne = new ArrayList<>();
        List<RecordFromFile> recordsFromArrayListTwo = new ArrayList<>();

        CollectionBuilder cb = new CollectionBuilder();

        try {
            if (Reader.ifFileExists(args[0])) {
                Reader.read(args[0], recordsFromArrayListOne);
            } else return;

            if (Reader.ifFileExists(args[1])) {
                Reader.read(args[1], recordsFromArrayListTwo);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("В параметры запуска программы передано меньше двух значений");
            return;
        }

        //System.out.println(cb.buildArrayListImpl(recordsFromArrayListOne, recordsFromArrayListTwo));
        System.out.println(cb.buildLinkedListImpl(recordsFromArrayListOne, recordsFromArrayListTwo));
        //System.out.println(cb.buildHashMapImpl(recordsFromArrayListOne, recordsFromArrayListTwo));
    }
}
