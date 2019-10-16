package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    //T(N)=O(N)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    static public int josephTask(int menNumber, int choiceInterval) {
             int result=0;
             for(int i=1; i<=menNumber; i++){
                 result=(result+choiceInterval)%i;
             }
             return result+1;
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //T(N)=O(N*M)-трудоёмкость, N=first.length, M=second.length
    //R(N)=O(N*M)-ресурсоёмкость
    static public String longestCommonSubstring(String first, String second) {
        if (first.isEmpty() || second.isEmpty()) return "";
        int[][] matrix = new int[first.length()][second.length()];
        int maxSubstring = 0;
        int maxIndex = -1;
        for (int i=0; i<first.length(); i++){
            for(int j=0; j<second.length(); j++){
                matrix[i][j]=0;
            }
        }
        for (int i=0; i<first.length(); i++){
            for(int j=0; j<second.length(); j++){
                if (first.charAt(i)==second.charAt(j)){
                    if (i!=0 && j!=0) {matrix[i][j]=matrix[i-1][j-1]+1;}
                    else {matrix[i][j]=1;}
                    if(maxSubstring<matrix[i][j]){
                        maxSubstring=matrix[i][j];
                        maxIndex = i;
                    }
                }
            }
        }
        if (maxSubstring==0) return "";
        else{return first.substring(maxIndex-maxSubstring+1, maxIndex+1);}
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //T(N)=O(N*log log N)-трудоёмкость, т.к. используем квадратный корень
    //R(N)=O(1)-ресурсоёмкость
    static public int calcPrimesNumber(int limit) {
        int result = 0;
        for (int i=1; i<=limit; i++){
            if (isPrime(i)) result++;
        }
        return result;
    }

    static public boolean isPrime(int n){
        if (n<2) return false;
        if (n==2) return true;
        if (n%2==0)return false;
        for (int m=3; m<=new Double(Math.sqrt(n)).intValue(); m=m+2){
            if(n%m==0) return false;
        }
        return true;
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    //T(N)=O(N*M*H)-трудоёмкость, N-число строк, M-длина строки, H-количество слов
    //R(N)=O(N*M+H)-ресурсоёмкость
    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        try(FileInputStream input = new FileInputStream(inputName);
        Scanner reader = new Scanner(new InputStreamReader(input))) {
            List<String[]> matrix = new ArrayList<>();
            Set<String> result = new HashSet<>();
            Set<Pair<Integer, Integer>> passedWay = new HashSet<>();
            while ((reader.hasNextLine())) {
                String line = reader.nextLine().trim();
                String[] part = line.split(" ");
                matrix.add(part);
            }
            for (String word : words) {
                for (int i = 0; i < matrix.size(); i++) {
                    for (int j = 0; j < matrix.get(i).length; j++) {
                        if (matrix.get(i)[j].equals(String.valueOf(word.charAt(0)))) {
                            passedWay.clear();
                            passedWay.add(new Pair<>(i,j));
                            if (findWord(matrix, i, j, word, passedWay)) {
                                result.add(word);
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    static public boolean findWord(List<String[]> matrix, int i, int j, String word, Set<Pair<Integer,Integer>> way){
        if(word.length()==1) return true;
        String c = String.valueOf(word.charAt(1));
        if(i>0 && !way.contains(new Pair<>(i-1, j)) && matrix.get(i-1)[j].equals(c)){
            if(findWord(matrix, i-1, j, word.substring(1), way)) return true;
        }
        if(i<matrix.size()-1 && !way.contains(new Pair<>(i+1, j)) && matrix.get(i+1)[j].equals(c)){
            if(findWord(matrix, i+1, j, word.substring(1), way)) return true;
        }
        if(j>0 && !way.contains(new Pair<>(i, j-1)) && matrix.get(i)[j-1].equals(c)){
            if(findWord(matrix, i, j-1, word.substring(1), way)) return true;
        }
        if(j<matrix.get(i).length-1 && !way.contains(new Pair<>(i, j+1)) && matrix.get(i)[j+1].equals(c)){
            if(findWord(matrix, i, j+1, word.substring(1), way)) return true;
        }
        return false;
    }
}
