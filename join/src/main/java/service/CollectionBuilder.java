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

    public List<RecordToFile> buildArrayListImpl(List<List<RecordFromFile>> bothLists) {
        List<RecordFromFile> listOne = bothLists.get(0);
        List<RecordFromFile> listTwo = bothLists.get(1);
        List<RecordToFile> resultList = new ArrayList<>();

        for (RecordFromFile recordOuter : listOne) {
            for (RecordFromFile recordInner : listTwo) {
                if (recordInner.getId().equals(recordOuter.getId())) {
                    resultList.add(new RecordToFile(recordInner.getId(), recordInner.getValue(), recordOuter.getValue()));
                }
            }
        }
        return resultList;
    }

    public List<RecordToFile> buildLinkedListImpl(List<List<RecordFromFile>> bothLists) {
        List<RecordFromFile> listOne = bothLists.get(0);
        List<RecordFromFile> listTwo = bothLists.get(1);
        listOne.sort(Comparator.comparing(RecordFromFile::getId));
        listTwo.sort(Comparator.comparing(RecordFromFile::getId));
        List<RecordToFile> resultList = new LinkedList<>();

        int index1 = 0;
        int index2 = 0;
        boolean leftOrRight = true; //TRUE когда элемент правого списка двигается по левому, FALSE - наоборот

        while ((listOne.size() > index1) && (listTwo.size() > index2)) {
                RecordFromFile listOneElem = listOne.get(index1);
                RecordFromFile listTwoElem = listTwo.get(index2);
                //System.out.println(index1 + " " + index2);
                if (listOneElem.getId() > listTwoElem.getId()) {
                    index2++;
                    leftOrRight = true;
                    continue;
                }

                if (listOneElem.getId() < listTwoElem.getId()) {
                    index1++;
                    leftOrRight = false;
                    continue;
                }

                if (listOneElem.getId().equals(listTwoElem.getId())) {
                    if ((index1 + 1 < listOne.size()) &&
                            (index2 + 1 < listTwo.size()) &&
                            (listOne.get(index1 + 1).getId().equals(listOneElem.getId())) &&
                            (listOne.get(index1 + 1).getId().equals(listTwo.get(index2 + 1).getId()))) {

                        List<RecordFromFile> temporaryListOne = new LinkedList<>();
                        temporaryListOne.add(listOneElem);
                        while ((index1 + 1 < listOne.size()) && listOne.get(index1 + 1).getId().equals(listOne.get(index1).getId())) {
                            temporaryListOne.add(listOne.get(index1 + 1));
                            index1++;
                        }
                        index1++;

                        List<RecordFromFile> temporaryListTwo = new LinkedList<>();
                        temporaryListTwo.add(listTwoElem);
                        while ((index2 + 1 < listTwo.size()) && listTwo.get(index2 + 1).getId().equals(listTwo.get(index2).getId())) {
                            temporaryListTwo.add(listTwo.get(index2 + 1));
                            index2++;
                        }
                        index2++;

                        for (RecordFromFile outerRecord : temporaryListOne) {
                            for (RecordFromFile innerRecord : temporaryListTwo) {
                                resultList.add(new RecordToFile(outerRecord.getId(), outerRecord.getValue(), innerRecord.getValue()));
                            }
                        }
                        continue;
                    }
                    resultList.add(new RecordToFile(listOneElem.getId(), listOneElem.getValue(), listTwoElem.getValue()));
                    if (leftOrRight) {
                        index2++;
                    } else {
                        index1++;
                    }
                }
        }
        return resultList;
    }

    public List<RecordToFile> buildHashMapImpl(List<List<RecordFromFile>> bothLists) {
        HashMap<Integer, List<RecordFromFile>> mapOne = createHashMapFromList(bothLists.get(0));
        HashMap<Integer, List<RecordFromFile>> mapTwo = createHashMapFromList(bothLists.get(1));
        List<RecordToFile> resultList = new ArrayList<>();

        for (List<RecordFromFile> recordListOuter : mapOne.values()) {
            for (RecordFromFile recordFromMapOne : recordListOuter) {
                if (mapTwo.get(recordFromMapOne.getId()) == null) {
                    continue;
                }
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
