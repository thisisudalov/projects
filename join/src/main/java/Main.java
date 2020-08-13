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
        List<List<RecordFromFile>> bothLists = new ArrayList<>();
        bothLists.add(recordsFromArrayListOne);
        bothLists.add(recordsFromArrayListTwo);

        System.out.println(cb.buildArrayListImpl(bothLists));
        System.out.println(cb.buildLinkedListImpl(bothLists));
        System.out.println(cb.buildHashMapImpl(bothLists));
    }
}
