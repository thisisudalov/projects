package service;

import models.RecordFromFile;
import models.RecordToFile;

import java.util.*;

public class CollectionBuilder {

    public void fillArrayList(String line, List<RecordFromFile> records){
        if (line.length()<1) {
            System.out.println("Пустая линия");
            return;
        }

        String temp[] = line.split(",");
        if (validateFileLine(temp)) {
            records.add(new RecordFromFile(Integer.parseInt(temp[0]), temp[1]));
        } else {
            System.out.println("Строку не удалось обработать");
            return;
        }
    }

    public List<RecordToFile> buildArrayListImpl(List<RecordFromFile> recordsFromArrayListOne, List<RecordFromFile> recordsFromArrayListTwo) {
        List<RecordToFile> resultList = new ArrayList<>();

        for (RecordFromFile recordOuter : recordsFromArrayListOne) {
            for (RecordFromFile recordInner : recordsFromArrayListTwo) {
                if (recordInner.getId().equals(recordOuter.getId())) {
                    resultList.add(new RecordToFile(recordInner.getId(), recordInner.getValue(), recordOuter.getValue()));
                }
            }
        }
        return resultList;
    }

    public List<RecordToFile> buildLinkedListImpl(List<RecordFromFile> recordsFromArrayListOne, List<RecordFromFile> recordsFromArrayListTwo) {
        LinkedList<RecordFromFile> listOne = new LinkedList<>(recordsFromArrayListOne);
        LinkedList<RecordFromFile> listTwo = new LinkedList<>(recordsFromArrayListTwo);
        listOne.sort(Comparator.comparing(RecordFromFile::getId));
        listTwo.sort(Comparator.comparing(RecordFromFile::getId));
        List<RecordToFile> resultList = new LinkedList<>();

        ListIterator<RecordFromFile> it1 = listOne.listIterator();
        ListIterator<RecordFromFile> it2 = listTwo.listIterator();

        RecordFromFile currentLeft = it1.next();
        RecordFromFile currentRight = it2.next();

        while (it1.hasNext() || it2.hasNext()) {
            if (currentLeft.getId() < currentRight.getId()) {
                System.out.println("more");
                if (it1.hasNext()) currentLeft = it1.next();
                else return resultList;
            } else if (currentLeft.getId() > currentRight.getId()) {
                System.out.println("less");
                if (it2.hasNext()) currentRight = it2.next();
                else return resultList;
            } else {
                System.out.println("EQUALS" + currentLeft.getValue() + " " + currentRight.getValue());
                List<RecordFromFile> leftTempList = createTempList(it1, currentLeft);
                List<RecordFromFile> rightTempList = createTempList(it2, currentRight);
                for (RecordFromFile outerRecord : leftTempList) {
                    for (RecordFromFile innerRecord : rightTempList) {
                        resultList.add(new RecordToFile(outerRecord.getId(), outerRecord.getValue(), innerRecord.getValue()));
                    }
                }
                try {
                    currentLeft = it1.next();
                    currentRight = it2.next();
                } catch (NoSuchElementException e) {
                    System.out.println("caught exeption");
                    return resultList;
                }
            }
            if (!it1.hasNext() && !it2.hasNext()) {
                if (currentLeft.getId().equals(currentRight.getId())) resultList.add(new RecordToFile(currentLeft.getId(), currentLeft.getValue(), currentRight.getValue()));
            }
        }
        return resultList;
    }

    private List<RecordFromFile> createTempList(ListIterator<RecordFromFile> iterator, RecordFromFile currentElem) {
        System.out.println("Entered templist");
        List<RecordFromFile> list = new ArrayList<>();
        RecordFromFile nextElem;
        list.add(currentElem);
        System.out.println("added once" + currentElem.getValue());
        while (iterator.hasNext()) {
            if ((nextElem = iterator.next()).getId().equals(currentElem.getId())) {
                list.add(nextElem);
                System.out.println("added elem" + nextElem.getValue());
            } else {
                iterator.previous();
                return list;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<RecordToFile> buildHashMapImpl(List<RecordFromFile> recordsFromArrayListOne, List<RecordFromFile> recordsFromArrayListTwo) {
        HashMap<Integer, List<RecordFromFile>> mapOne = createHashMapFromList(recordsFromArrayListOne);
        HashMap<Integer, List<RecordFromFile>> mapTwo = createHashMapFromList(recordsFromArrayListTwo);
        List<RecordToFile> resultList = new ArrayList<>();

        for (List<RecordFromFile> recordListOuter : mapOne.values()) {
            if (!mapTwo.containsKey(recordListOuter.get(0).getId())) {
                continue;
            }
            for (RecordFromFile recordFromMapOne : recordListOuter) {
                for (RecordFromFile recordFromMapTwo : mapTwo.get(recordFromMapOne.getId())){
                    resultList.add(new RecordToFile(recordFromMapOne.getId(), recordFromMapOne.getValue(), recordFromMapTwo.getValue()));
                }
            }
        }

        return resultList;
    }

    private HashMap<Integer, List<RecordFromFile>> createHashMapFromList(List<RecordFromFile> list) {
        HashMap<Integer, List<RecordFromFile>> resultHashMap = new HashMap<>();
        for (RecordFromFile record : list) {
            if (resultHashMap.containsKey(record.getId())) {
                resultHashMap.get(record.getId()).add(record);
            } else {
                List<RecordFromFile> tempList = new ArrayList<>();
                tempList.add(record);
                resultHashMap.put(record.getId(), tempList);
            }
        }
        return resultHashMap;
    }

    private boolean validateFileLine(String[] temp) {
        if (temp.length!=2) {
            System.out.println("Неверное количество параметров в строке");
            return false;
        }

        for (String aTemp : temp) {
            if (aTemp.replaceAll(" ", "").length() < 1) {
                System.out.println("Минимум один из параметров в строке не заполнен");
                return false;
            }

            if (!tryParseStringToInt(temp[0])) {
                return false;
            }
        }
        return true;
    }

    private boolean tryParseStringToInt(String parameter){
        try {
            int id = Integer.parseInt(parameter);
            if (id < 0) {
                System.out.println("ID не может быть отрицательным");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID заполнен неверно");
            return false;
        }
        return true;
    }

}
