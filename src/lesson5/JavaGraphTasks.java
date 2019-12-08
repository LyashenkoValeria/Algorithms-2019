package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    //T(N)=O(V^2)-трудоёмкость
    //R(N)=O(V+E)-ресурсоёмкость
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new ArrayList<>();
        List<Graph.Vertex> loop = new ArrayList<>();
        Set<Graph.Edge> edges = graph.getEdges();
        Set<Graph.Vertex> vertices = graph.getVertices();
        if(!isEulerLoop(graph)||edges.isEmpty()||vertices.isEmpty()) return result;

        Graph.Vertex first = vertices.iterator().next();
        findLoop(first, graph, edges, vertices, loop);

            for (int i = 0; i < loop.size() - 1; i++) {
                result.add(graph.getConnection(loop.get(i), loop.get(i + 1)));
            }
        return result;
    }

    public static void findLoop(Graph.Vertex vertex, Graph graph,  Set<Graph.Edge> edges,
                                Set<Graph.Vertex> vertices, List<Graph.Vertex> loop){
        for(Graph.Vertex v : vertices){
            Graph.Edge currentEdge = graph.getConnection(vertex, v);
            if(edges.contains(currentEdge)){
                edges.remove(currentEdge);
                findLoop(v, graph, edges, vertices, loop);
            }
        }
        loop.add(vertex);
    }

    public static boolean isEulerLoop(Graph graph){
        for(Graph.Vertex vertex : graph.getVertices()){
            if (graph.getNeighbors(vertex).size()%2 != 0) return false;
        }
        return true;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    //T(N)=O(V^2)-трудоёмкость
    //R(N)=O(V)-ресурсоёмкость
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
       if(graph.getVertices().isEmpty()) return new HashSet<>();
       Set<Graph.Vertex> begins = new HashSet<>();
       Set<Graph.Vertex> ends = new HashSet<>();
       for(Graph.Edge edge : graph.getEdges()){
           if(!checkLoop(graph, edge, begins, ends)) throw new IllegalArgumentException();
       }
       Set<Graph.Vertex> answer = new HashSet<>();
       for(Graph.Vertex vertex : graph.getVertices()){
           Set<Graph.Vertex> result = new HashSet<>();
           Set<Graph.Vertex> anotherResult = new HashSet<>();

           for (Graph.Vertex anotherVertex : graph.getVertices()) {
               if (!graph.getNeighbors(vertex).contains(anotherVertex) &&
                       !anotherResult.contains(anotherVertex)) {
                   anotherResult.addAll(graph.getNeighbors(anotherVertex));
                   result.add(anotherVertex);
               }
           }
           if (result.size() + anotherResult.size() == graph.getVertices().size()){
               if(result.size() >= anotherResult.size()) {
                   answer = result;
               }
               else answer = anotherResult;
               break;
           }
       }
       return answer;
    }

    public static boolean checkLoop(Graph graph, Graph.Edge edge, Set<Graph.Vertex> begins, Set<Graph.Vertex> ends){
        Graph.Vertex begin = edge.getBegin();
        Graph.Vertex end = edge.getEnd();
        if(begins.contains(begin) && ends.contains(end) || begins.contains((end))
                && ends.contains(begin)) return false;
        if(!begins.contains(begin) && !ends.contains(end)){
            begins.add(begin);
            ends.add(end);
        } else if (ends.contains(begin)) {
            begins.add(end);
            ends.add(end);
        }
        else if(begins.contains(end)) {
            ends.add(begin);
            begins.add(begin);
        }
        return true;
    }


    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    //T(N)=O(V!)-трудоёмкость
    //R(N)=O(V!)-ресурсоёмкость
    public static Path longestSimplePath(Graph graph) {
        if(graph.getEdges().isEmpty() || graph.getVertices().isEmpty()) return new Path();
        Set<Graph.Vertex> vertices = graph.getVertices();
        Path longestSimplePath = new Path(vertices.iterator().next());
        Deque<Path> deque = new ArrayDeque<>();

        for(Graph.Vertex vertex : vertices){
            deque.add(new Path(vertex));
        }

        while(!deque.isEmpty()){
            Path currentPath = deque.pop();
            List<Graph.Vertex> vertexList = currentPath.getVertices();
            if(currentPath.getLength() > longestSimplePath.getLength()){
                longestSimplePath = currentPath;
                if(vertexList.size() == vertices.size()) break;
            }
            Set<Graph.Vertex> neighbors = graph.getNeighbors(vertexList.get(vertexList.size()-1));
            for(Graph.Vertex neighbor : neighbors){
                if(!currentPath.contains(neighbor)){
                    deque.push(new Path(currentPath, graph, neighbor));
                }
            }
        }
        return longestSimplePath;
    }
}
