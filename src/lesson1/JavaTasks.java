package lesson1;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

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
    static public void sortTimes(String inputName, String outputName) throws Exception {
        class Time implements Comparable<Time> {

            private int hour;
            private int min;
            private int sec;
            private String dayPart;

            Time(int hour, int min, int sec, String dayPart) {
                this.hour = hour;
                this.min = min;
                this.sec = sec;
                this.dayPart = dayPart;
            }

            public int getHour() {
                return hour;
            }

            public int getMin() {
                return min;
            }

            public int getSec() {
                return sec;
            }

            public String getDayPart() {
                return dayPart;
            }

            @Override
            public int compareTo(@NotNull Time o) {
                return Comparator.comparing(Time::getDayPart)
                        .thenComparing(Time::getHour)
                        .thenComparing(Time::getMin)
                        .thenComparing(Time::getSec)
                        .compare(this, o);
            }

            @Override
            public String toString() {
                StringBuilder res = new StringBuilder();
                if (hour == 0) {
                    res.append("12:");
                } else {
                    if (hour > 0 && hour < 10) {
                        res.append("0");
                    }
                    res.append(hour).append(":");
                }
                if (min < 10) {
                    res.append("0");
                }
                res.append(min).append(":");
                if (sec < 10) {
                    res.append("0");
                }
                res.append(sec).append(" ");
                res.append(dayPart);
                return res.toString();
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
                    String[] parts = line.substring(0,8).split(":");
                    int[] timeNumber = new int[3];
                    for(int i = 0; i<parts.length; i++){
                        timeNumber[i]=Integer.parseInt(parts[i]);
                    }
                    Time time = new Time(timeNumber[0],timeNumber[1],timeNumber[2], line.substring(9));
                    lines.add(time);
                }
                else {
                    throw new IOException();
                }
            }
            Collections.sort(lines);
            for (Time line : lines) {
                writer.write(line.toString() + "\n");
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
    static public void sortTemperatures(String inputName, String outputName) throws Exception {
        Double leftBorder = -273.0*10;
        Double rightBorder = 500.0*10;
        int tempNumber = rightBorder.intValue() - leftBorder.intValue() + 1;
        try (FileInputStream input = new FileInputStream(inputName);
             Scanner reader = new Scanner(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            int[] temp = new int[tempNumber];
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
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
    static public void sortSequence(String inputName, String outputName) throws Exception {
        List<Integer> seq = new ArrayList<>();
        Map<Integer, Integer> repeat = new TreeMap<>();
        try (FileInputStream input = new FileInputStream(inputName);
             Scanner reader = new Scanner(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
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
