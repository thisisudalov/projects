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

        boolean exitCondition = false;
        RecordFromFile currentLeft = it1.next();
        RecordFromFile currentRight = it2.next();

        for (Iterator it = listOne.iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        while (it1.hasNext() || it2.hasNext()) {
            System.out.println("CURRENT: " + currentLeft.getValue() + currentRight.getValue());
            if (currentLeft.getId() < currentRight.getId()) {
                System.out.println("more");
                if (it1.hasNext()) currentLeft = it1.next();
                else return resultList;
            }
            else if (currentLeft.getId() > currentRight.getId()) {
                System.out.println("less");
                System.out.println(it2.hasNext());
                if (it2.hasNext()) currentRight = it2.next();
                else return resultList;
            }
            else {
                System.out.println("equal" + currentLeft.getValue() + " " + currentRight.getValue());
                RecordFromFile nextLeft;
                RecordFromFile nextRight;
                LinkedList<RecordFromFile> leftTempList = new LinkedList<>();
                LinkedList<RecordFromFile> rightTempList = new LinkedList<>();
                if (it1.hasNext()) {
                    if ((nextLeft = it1.next()).getId().equals(currentLeft.getId())) {
                        leftTempList.add(currentLeft);
                        leftTempList.add(nextLeft);
                        while (it1.hasNext()) {
                            if ((currentLeft = nextLeft).getId().equals((nextLeft = it1.next()).getId())) {
                                leftTempList.add(nextLeft);
                                System.out.println("added left " + nextLeft.getValue());
                            } else {
                                currentLeft = nextLeft;
                                break;
                            }
                        }
                    } else {
                        System.out.println("ELSE");
                        it1.previous();
                    }
                }

                if (it2.hasNext()) {
                    if ((nextRight = it2.next()).getId().equals(currentRight.getId())) {
                        rightTempList.add(currentRight);
                        rightTempList.add(nextRight);
                        System.out.println("preadded" + currentRight.getValue() + " " + nextRight.getValue());
                        while (it2.hasNext()) {
                            if ((currentRight = nextRight).getId().equals((nextRight = it2.next()).getId())) {
                                rightTempList.add(nextRight);
                                System.out.println("added right" + nextRight.getValue());
                            } else {
                                currentRight = nextRight;
                                System.out.println("else");
                                break;
                            }
                        }
                    } else {

                        System.out.println("ELSE");
                        it2.previous();
                    }
                }
                if (!leftTempList.isEmpty() && !rightTempList.isEmpty()) {
                    for (RecordFromFile recordOuter : leftTempList) {
                        for (RecordFromFile recordInner : rightTempList) {
                            resultList.add(new RecordToFile(recordOuter.getId(), recordOuter.getValue(), recordInner.getValue()));
                        }
                    }
                }

                else if (rightTempList.isEmpty() && leftTempList.isEmpty()) {
                    System.out.println("added one");
                    resultList.add(new RecordToFile(currentLeft.getId(), currentLeft.getValue(), currentRight.getValue()));
                    if (it1.hasNext()){
                        System.out.println("left = " + currentLeft.getValue());
                        currentLeft = it1.next();
                    }
                    else return resultList;
                    if (it2.hasNext()) {
                        System.out.println(it2.nextIndex());
                        System.out.println("right = " + currentRight.getValue());
                        currentRight = it2.next();
                    }
                    else return resultList;
                }

                else if (leftTempList.isEmpty()) {
                    for (RecordFromFile record : rightTempList) {
                        resultList.add(new RecordToFile(currentLeft.getId(), currentLeft.getValue(), record.getValue()));
                    }
                }

                else {
                    for (RecordFromFile record : leftTempList) {
                        resultList.add(new RecordToFile(record.getId(), record.getValue(), currentRight.getValue()));
                    }
                }
            }
            if (!(it1.hasNext() || it2.hasNext())) {
                if (currentLeft.getId().equals(currentRight.getId())) {
                    resultList.add(new RecordToFile(currentLeft.getId(), currentLeft.getValue(), currentRight.getValue()));
                }
            }
        }
        return resultList;
    }

    public List<RecordToFile> buildHashMapImpl(List<RecordFromFile> recordsFromArrayListOne, List<RecordFromFile> recordsFromArrayListTwo) {
        HashMap<Integer, List<RecordFromFile>> mapOne = createHashMapFromList(recordsFromArrayListOne);
        HashMap<Integer, List<RecordFromFile>> mapTwo = createHashMapFromList(recordsFromArrayListTwo);
        List<RecordToFile> resultList = new ArrayList<>();

        for (List<RecordFromFile> recordListOuter : mapOne.values()) {
            if (mapTwo.get(recordListOuter.get(0).getId()) == null) {
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
