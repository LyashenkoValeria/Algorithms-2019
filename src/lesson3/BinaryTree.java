package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {


    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;



    private int size = 0;

    @Override
    public boolean add(T t)  {
            Node<T> closest = find(t);
            int comparison = closest == null ? -1 : t.compareTo(closest.value);
            if (comparison == 0) {
              return false;
            }
            Node<T> newNode = new Node<>(t);
            if (closest == null) {
                root = newNode;
            } else if (comparison < 0) {
                assert closest.left == null;
                closest.left = newNode;
            } else {
                assert closest.right == null;
                closest.right = newNode;
            }
            size++;
            return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //T(N)=O(N)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        Node<T> parent = root;
        Node<T> delete = root;
        while (delete != null && delete.value != t) {
            parent = delete;
            if (t.compareTo(delete.value) < 0) {
                delete = delete.left;
            } else {
                delete = delete.right;
            }
        }
        if (delete == null) return false;

        //У удаляемого 0 или 1 потомок
        else if(delete.left == null || delete.right == null) {
            if (delete.left == null) changeNode(delete, parent, delete.right);
            else  changeNode(delete, parent, delete.left);
        }

        //У удаляемого 2 потомка
        else {
            Node<T> next = delete.right;
            Node<T> nextParent = delete;

            //Находим следующий за удалемым элемент
            while (next.left != null) {
                nextParent = next;
                next = next.left;
            }

            //Перевешиваем на него потомков delete
            if (next != delete.right) {
                nextParent.left = next.right;
                next.right = delete.right;
            }
            next.left = delete.left;
            changeNode(delete, parent, next);
        }

        size--;
        return true;
    }

    public void changeNode(Node<T> del, Node<T> parent, Node<T> newChild){
            if (del == root) {
                root = newChild;
            } else if (parent.left == del) {
                parent.left = newChild;
            } else {
                parent.right = newChild;
            }
    }



    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Stack<Node<T>> stack = new Stack<>();
        private Node<T> current = null;

        //T(N)=O(N)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        private BinaryTreeIterator() {
        Node<T> node = root;
        while (node != null){
            stack.push(node);
            node = node.left;
        }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        //T(N)=O(1)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            current = nextNode();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        //T(N)=O(N)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        public Node<T> nextNode(){
            Node<T> node = stack.pop();
            current = node;
            if (node.right != null){
                node = node.right;
                while (node != null){
                    stack.push(node);
                    node = node.left;
                }
            }
            return current;
        }


        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    //T(N)=O(1)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubTree<>(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    //T(N)=O(1)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubTree<>(this, null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    //T(N)=O(1)-трудоёмкость
    //R(N)=O(1)-ресурсоёмкость
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubTree<>(this, fromElement, null);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }


    public class SubTree<ST extends Comparable<ST>> extends BinaryTree<ST>{
        BinaryTree<ST> tree;
        ST fromElem, toElem;

        public SubTree(BinaryTree<ST> tree, ST fromElem, ST toElem){
            this.tree = tree;
            this.fromElem = fromElem;
            this.toElem = toElem;
        }

        //T(N)=O(1)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        private boolean inSubTree(ST elem){
            if(fromElem != null && toElem != null){
                return elem.compareTo(fromElem) >= 0 && elem.compareTo(toElem) < 0;
            } else if (fromElem == null){
                return elem.compareTo(toElem) < 0;
            } else return elem.compareTo(fromElem) >= 0;
        }

        //T(N)=O(N)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        @Override
        public boolean add(ST t) {
            if (inSubTree(t)) {
                return tree.add(t);
            }
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean contains(Object o) {
            return (inSubTree((ST) o)) && tree.contains(o);
        }

        //T(N)=O(N)-трудоёмкость
        //R(N)=O(1)-ресурсоёмкость
        private int subTreeSize(Node<ST> node){
            int size = 0;
            if(node != null){
                if(inSubTree(node.value)){
                    size++;
                }
                size += subTreeSize(node.left);
                size += subTreeSize(node.right);
            }
            return size;
        }


        @Override
        public int size(){
            return subTreeSize(tree.root);
        }

    }
}
