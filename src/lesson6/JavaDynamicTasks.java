package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    //T(N)=O(N*M)-трудоёмкость
    //R(N)=O(N*M)-ресурсоёмкость
    public static String longestCommonSubSequence(String first, String second) {
        StringBuilder result = new StringBuilder();
        int firstLength = first.length();
        int secondLength = second.length();
        int[][] matrix = new int[firstLength + 1][secondLength + 1];
        for(int i = 1; i < firstLength + 1; i++){
            for(int j = 1; j < secondLength + 1; j++){
                if(first.charAt(i - 1) == second.charAt(j - 1)) matrix[i][j] = 1 + matrix[i - 1][j - 1];
                else matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }
        int i = firstLength;
        int j = secondLength;
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                result = result.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (matrix[i][j] == matrix[i - 1][j]) {
                i--;
            } else j--;
        }
        return result.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */

    //T(N)=O(N^2)-трудоёмкость
    //R(N)=O(N)-ресурсоёмкость
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        if(list.size()<=1) return list;
        int[] array = new int[list.size()];
        int[] length = new int[list.size()];
        Arrays.fill(array, -1);
        Arrays.fill(length, -1);

        int pos = 0;
        int maxLength = -1;
        for(int i = 0; i<list.size(); i++){
            for(int j = 0; j < i; j++){
                if(list.get(i) > list.get(j) && length[i] <= length[j]){
                    array[i] = j;
                    length[i] = length[j] + 1;
                    if(length[i] > maxLength){
                        pos = i;
                        maxLength = length[i];
                    }
                }
            }
        }

        while (pos != -1){
            result.add(list.get(pos));
            pos = array[pos];
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
