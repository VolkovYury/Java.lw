package com.lw.lw1_2;


import java.util.LinkedList;
import java.util.Queue;

class Model {
    static int diameter = 40;
    public static int stdValue = -777;

    private int coordinatesX;
    private int coordinatesY;
    private int[] example = new int[1];
    private String arrayTree = "";

    private int index;
    private int value;


    // сеттеры
    public void setIndex(int i) {
        if (i >= 0) {
            index = i;
        }
    }
    public void setValue(int i) {
        value = i;
    }


    // геттеры
    public int getIndex() {
        return index;
    }
    public int getValue() {
        return value;
    }
    public int [] getExample() {
        return example;
    }
    public int getCoordinatesX() {
        return coordinatesX;
    }
    public int getCoordinatesY() {
        return coordinatesY;
    }
    public String getArrayTree() {
        this.arrToTxt(example);
        return arrayTree;
    }


    // Данный метод добавляет узел по индексу к существующему дереву
    // в методе осуществляется обработка некоторых некорректных данных - выход за границы массива дерева, попытка создать
    // узел без родительского узла, создание уже созданного узла.
    void addNode(int [] arr, int index, int value) {

        boolean check = true;   // данный флаг указывает на то, есть у создаваемого узла родительский узел или нет
        int indexParent;

        // блок, вычисляющий индекс родительского узла по индексу исходного узла
        if (index != 0) {

            // если индекс чётный (правая ветвь), то по формуле 2*i + 2:
            if (index % 2 == 0) {
                indexParent = (index - 2) / 2;
            }
            // если индекс нечётный (левая ветвь), то по формуле 2*i + 1:
            else {

                indexParent = (index - 1) / 2;
            }

            try {
                if (arr[indexParent] == stdValue) {
                    System.out.println("Создать узел с индексом " +index+ " невозможно, т.к. отсутствует родительский узел");
                    check = false;
                }
            }
            catch (ArrayIndexOutOfBoundsException Ex1) {
                System.out.println("Создать узел с индексом " +index+ " невозможно, т.к. отсутствует родительский узел (Exception)");
                check = false;
            }
        }
        // если значение индекса равно нулю:
        else {
            System.out.println("Указан индекс родительского узла");
        }

        // Если у исходного узла есть родительский узел
        if (check) {

            // Если индекс находится за пределами массива дерева
            if (index >= (arr.length*2+1)) {
                System.out.println("По индексу " + index + " нельзя создать узел (уровень пока не доступен)");
            }
            // Если индекс соответствует массиву дерева
            else {

                // Если узел выходит за пределы массива дерева (увеличение уровня)
                if(index >= arr.length) {

                    // инициализируется новый массив необходимого размера и в "добавленные" ячейки записывается "стандартное значение"
                    int [] newArray = new int[arr.length*2+1];
                    for (int i = arr.length; i < newArray.length; i++){
                        newArray[i] = stdValue;
                    }

                    // копирование нашего массива в новый расширенный
                    System.arraycopy(arr, 0, newArray, 0, arr.length);
                    newArray[index] = value;
                    System.out.println("По индексу "+index+" создан узел со значением "+ value);

                    example = newArray;
                }
                // Если узел создаётся в пределах имеющегося массива дерева
                else {

                    // Если узел отсутствует:
                    if (arr[index] == stdValue) {

                        arr[index] = value;
                        System.out.println("По индексу "+index+" создан узел со значением "+ value);
                    }
                    // Если узел уже создан и он не родительский:
                    else if (index !=0) {
                        System.out.println("По индексу "+index+" нельзя создать новый узел (узел уже создан)");
                    }
                }
            }
        }
    }

    // Данный метод удаляет узел по индексу существующего дерева
    // в методе осуществляется обработка некоторых некорректных данных - выход за границы массива дерева, попытка удалить
    // отсутствующий узел.
    void delNode(int [] arr, int index) {

        Queue<Integer> delNodes = new LinkedList<>();

        try {
            // Если узел отсутствует:
            if (arr[index]== stdValue) {
                System.out.println("Узел с индексом " + index + " отсутствует");
            }
            // Если узел удалить можно, то:
            else {

                // вносится индекс узла в очередь
                delNodes.add(index);

                // блок кода, ответственный за формирование списка и удаление узлов
                while (!delNodes.isEmpty()) {
                    // первый элемент принимается за родительский
                    int parent = delNodes.remove();
                    // вычисляются индексы его дочерних элементов
                    int childNode1 = parent * 2 + 1;
                    int childNode2 = parent * 2 + 2;

                    // 2 похожих блока кода
                    // Если узел находится в пределах массива дерева, а потом если дочерний узел имеется, то он добавляется
                    // в очередь и цикл while повторяется
                    if (childNode1 < arr.length) {
                        if (arr[childNode1] != stdValue) {
                            delNodes.add(childNode1);
                        }
                    }

                    if (childNode2 < arr.length) {
                        if (arr[childNode2] != stdValue) {
                            delNodes.add(childNode2);
                        }
                    }

                    // в самом конце значение родительского узла удаляется
                    arr[parent] = stdValue;

                    boolean resize = true;
                    int numberOfElementsLL = (arr.length + 1)/2;
                    int indexFirstElementLL = arr.length - numberOfElementsLL;

                    for (int i = indexFirstElementLL; i < arr.length; i++) {
                        if (arr[i] != stdValue) {
                            resize = false;
                            break;
                        }
                    }

                    if (resize) {
                        int [] newArray = new int [indexFirstElementLL];
                        System.arraycopy(arr, 0, newArray, 0, newArray.length);
                        example = newArray;
                    }

                }
                System.out.println("Узел с индексом " + index + " удалён(включая дочерние узлы)");
            }
        }
        catch (ArrayIndexOutOfBoundsException Ex1) {
            System.out.println("Узел с индексом " + index + " недоступен");
        }
    }

    // Данный метод возвращает значение узла по индексу существующего дерева
    // в методе осуществляется обработка некоторых некорректных данных - выход за границы массива дерева, попытка получить
    // значение отсутствующего узла.
    void returnValueNode(int [] arr, int index) {

        try {
            int value = arr[index];

            if (value == stdValue) {
                System.out.println("Данный узел отсутствует");
            }
            else {
                System.out.println("Значение узла с индексом " + index+ " равно " + value);

                this.findXYNode(example, index);
            }
        }
        catch (ArrayIndexOutOfBoundsException ex1) {
            System.out.println("Данный индекс не доступен (за пределами массива дерева)");
        }
    }

    // Данный метод изменяет значение узла по индексу существующего дерева
    // в методе осуществляется обработка некоторых некорректных данных - выход за границы массива дерева, попытка изменить
    // значение отсутствующего узла, попытка изменить значение узла на его настоящее значение.
    void changeValueNode(int [] arr, int index, int value) {

        try {
            if (arr[index] == value) {
                System.out.println("Изменения не требуются");
            }
            else if (arr[index] == stdValue) {
                System.out.println("Данный узел отсутствует");
            }
            else {
                arr[index] = value;
                System.out.println("Изменения по индексу " + index + " внесены");
            }
        }
        catch (ArrayIndexOutOfBoundsException ex1) {
            System.out.println("Данный индекс не доступен (за пределами массива дерева)");
        }
    }


    // Метод, формирующий одномерный массив из данных текстового документа
    void txtToArr(String str) {
        String[] subStr;
        subStr = str.split("[:;,. ]+");

        int [] arrayTree = new int[subStr.length];

        for (int i = 0; i < subStr.length; i++) {
            arrayTree[i] = Integer.parseInt(subStr[i]);
        }

        int value1;
        if (arrayTree.length % 2 == 0) {
            value1 = (arrayTree.length + 2) / 2;
        }
        else {
            value1 = (arrayTree.length + 1) / 2;
        }

        int value2 = (int) Math.ceil(Math.log(value1)/ Math.log(2));

        int neededSize = (int) (Math.pow(2,value2)*2-1);

        int [] rightArray = new int[neededSize];

        System.arraycopy(arrayTree, 0, rightArray, 0, arrayTree.length);

        for (int i = arrayTree.length; i <rightArray.length; i++) {
            rightArray[i] = stdValue;
        }

        example = rightArray;

    }

    // Метод, формирующий "текстовый документ" (строку) из одномерного массива
    void arrToTxt (int [] arr) {
        for (int item : arr) {
            arrayTree += item;
            arrayTree += " ";
        }
    }

    // Метод, находящий координаты интересующего узла
    void findXYNode(int [] arr, int index) {
        int ElementsLL = (arr.length + 1) / 2;
        int NumbersL = 1 + (int) Math.round(Math.log(ElementsLL)/Math.log(2));

        int iter = 1;
        int pIndex = 1;
        int indexCopy = index;

        if (index == 0) {
            coordinatesY = (int) (1.5 * diameter);
            coordinatesX = (ElementsLL *2 +1) * diameter/2;
            return;
        }

        while (pIndex >0) {
            if (indexCopy %2 == 0) {
                pIndex = (indexCopy - 2) / 2;
            }
            else {
                pIndex = (indexCopy - 1) / 2;
            }

            indexCopy = pIndex;
            iter +=1;
        }

        // iter в данный момент указывает на то, в каком ряду находится элемент с индексом.
        int row = iter;

        coordinatesY =  (row) *2* diameter - diameter/2;

        int firstElIndex = (int) (Math.pow(2, (iter-1)) - 1);

        iter = 1;
        for (int i = firstElIndex; i < index; i++) {
            iter +=1;
        }

        // iter в данный момент указывает на номер элемента с индексом в ряду.
        int column = iter;

        coordinatesX = (int) (diameter * (0.5 + Math.pow(2, (NumbersL - row)) * (2 * column - 1)));

    }
}


