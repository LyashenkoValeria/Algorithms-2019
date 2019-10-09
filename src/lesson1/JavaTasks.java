package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //T(N)=O(N*log N)-трудоёмкость
    //R(N)=O(N)-ресурсоёмкость
    static public void sortTimes(String inputName, String outputName) throws IOException {
        class Time {
            private String number;
            private String dayPart;

            Time(String number, String dayPart){
                this.dayPart = dayPart;
                this.number = number;
            }
        }
        try(FileInputStream input = new FileInputStream(inputName);
            Scanner reader = new Scanner(new InputStreamReader(input));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            List<Time> lines = new ArrayList<>();
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
                if (line.matches("[01][0-9]:[0-5][0-9]:[0-5][0-9]\\s[AP]M")) {
                    if (line.substring(0,2).matches("[1][2]")){
                        line = "00" + line.substring(2);
                    }
                    Time time = new Time(line.substring(0,8), line.substring(9));
                    lines.add(time);
                }
                else {
                    throw new IOException();
                }
            }
            lines.sort((o1, o2) -> {
                int timeCompare = o1.dayPart.compareTo(o2.dayPart);
                if (timeCompare == 0) {
                    return o1.number.compareTo(o2.number);
                } else return timeCompare;
            });
            for(int i=0; i < lines.size(); i++){
                if (lines.get(i).number.substring(0,2).matches("00")){
                    lines.set(i, new Time("12"+lines.get(i).number.substring(2),lines.get(i).dayPart));
                }
            }
            for (Time line : lines) {
                writer.write(line.number + " " + line.dayPart + "\n");
            }
        }
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName)  {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //T(N)=O(N)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        Double leftBorder = -273.0;
        Double rightBorder = 500.0;
        leftBorder = leftBorder*10;
        rightBorder = rightBorder*10;
        int tempNumber = rightBorder.intValue() - leftBorder.intValue() + 1;
        try (FileInputStream input = new FileInputStream(inputName);
             Scanner reader = new Scanner(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            int[] temp = new int[tempNumber];
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
                if (!line.matches("[-][0-9]*[.][0-9]|[0-9]*[.][0-9]")) {
                    throw new IOException();
                }
                if (leftBorder > Integer.parseInt(line.replaceAll("[.]", "")) &&
                        rightBorder < Integer.parseInt(line.replaceAll("[.]", ""))) {
                    throw new IOException();
                }
                line = line.replaceAll("[.]", "");
                temp[Integer.parseInt(line) - leftBorder.intValue()]++;
            }
            for (int i = 0; i < tempNumber; i++) {
                String s = Integer.toString(i + leftBorder.intValue());
                for (int j = 0; j < temp[i]; j++) {
                    if (s.length() == 2 && i + leftBorder < 0) {
                        s = s.charAt(0) + "0" + s.charAt(1);
                    }
                    if (s.length() == 1 && i + leftBorder >= 0) {
                        s = "0" + s.charAt(0);
                    }
                    writer.write(s.substring(0, s.length() - 1) + "." + s.charAt(s.length() - 1) + "\n");
                }
            }
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    //T(N)=O(N)-трудоёмкость
    //R(N)=O(N)-ресурсоёмкость
    static public void sortSequence(String inputName, String outputName) throws IOException {
        List<Integer> seq = new ArrayList<>();
        Map<Integer, Integer> repeat = new TreeMap<>();
        try (FileInputStream input = new FileInputStream(inputName);
             Scanner reader = new Scanner(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
                if (!line.matches("[0-9]*")) {
                    throw new IOException();
                }
                seq.add(Integer.parseInt(line));
                repeat.putIfAbsent(Integer.parseInt(line), 0);
                repeat.put(Integer.parseInt(line), repeat.get(Integer.parseInt(line)) + 1);
            }
            Integer minNumber = seq.get(0);
            Integer countOfEntry = repeat.get(minNumber);
            for (Integer key : repeat.keySet()) {
                if (countOfEntry < repeat.get(key)) {
                    minNumber = key;
                    countOfEntry = repeat.get(key);
                }
            }
            seq.removeAll(Collections.singleton(minNumber));
            for (Integer elem : seq) {
                writer.write(elem + "\n");
            }
            for (int i = 0; i < countOfEntry; i++) {
                writer.write(minNumber + "\n");
            }
        }
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    //T(N)=O(N)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость, т.к. не выделяется доп. память
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
       int i=0;
       int j=first.length;
       int k=0;
       while (i<first.length && j<second.length){
           if (first[i].compareTo(second[j])<0){
               second[k]=first[i];
               i++;
           }
           else{
               second[k]=second[j];
               j++;
           }
           k++;
       }
       while (i<first.length){
           second[k]=first[i];
           i++;
           k++;
       }
    }
}
