//%%writefile Main.java
import java.time.Instant;
import java.time.Duration;

interface Lista<T> {
    void pushFront(T element);
    void pushBack(T element);
    T popFront();
    T popBack();
    int find(T element);
    boolean erase(T element);
    boolean addBefore(T element, T targetElement);
    boolean addAfter(T element, T targetElement);
}

interface MyStack<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
    boolean delete(T element);
}

interface MyQueue<T> {
    void enqueue(T element);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
    boolean delete(T element);
}

///////////////////////////////////////////////// Single linked list no tail

class ListaSimplementeEnlazada<T> implements Lista<T> {
    private static class Node<T> {
        T data;
        Node<T> next = null;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    @Override
    public void pushFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    @Override
    public void pushBack(T element){
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size ++;
    }

    @Override
    public T popFront() {
        if (head == null) {
            return null;
        } else {
            Node<T> popped = head;
            if (head.next == null) {
                head = null;
            } else {
                head = head.next;
            }
            size--;
            popped.next = null;
            return popped.data;
        }
    }

    @Override
    public T popBack() {
        T toReturn;
        if (head == null) {
            return null;
        } else if (head.next == null) {
            toReturn = head.data;
            head = null;
        } else {
            Node<T> prevToPopped = head;
            while (prevToPopped.next.next != null) {
                prevToPopped = prevToPopped.next;
            }
            toReturn = prevToPopped.next.data;
            prevToPopped.next = null;
        }
        size--;
        return toReturn;
    }

    @Override
    public int find(T element) {
        Node<T> current = head;
        int count = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                return count; // Retorna la posicion del elemento en la lista
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public boolean erase(T element) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(element)) {
            head = head.next;
            size --;
            return true;
        }

        Node<T> prev = head;

        while (prev.next != null) {
            if (prev.next.data.equals(element)) {
                prev.next = prev.next.next;
                size--;
                return true;
            }
            prev = prev.next;
        }
        return false;
    }

    @Override
    public boolean addBefore(T element, T targetElement){
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (head.data.equals(targetElement)) {
            newNode.next = head;
            head = newNode;
            size++;
            return true;
        }
        Node<T> prev = head;
        Node<T> current = head.next;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current;
                prev.next = newNode;
                size++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addAfter(T element, T targetElement) {
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        Node<T> current = head;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current.next;
                current.next = newNode;
                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

///////////////////////////////////////////////// Simple linked list With Tail

class ListaSimplementeEnlazadaConCola<T> implements Lista<T> {
    private static class Node<T> {
        T data;
        Node<T> next = null;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void pushFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    @Override
    public void pushBack(T element){
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size ++;
    }

    @Override
    public T popFront() {
        if (head == null) {
            return null;
        } else {
            Node<T> popped = head;
            if (head.next == null) {
                head = tail = null;
            } else {
                head = head.next;
            }
            size--;
            popped.next = null;
            return popped.data;
        }
    }

    @Override
    public T popBack() {
        T toReturn;
        if (head == null) {
            return null;
        } else if (head.next == null) {
            toReturn = head.data;
            head = tail = null;
        } else {
            Node<T> prevToPopped = head;
            while (prevToPopped.next.next != null) {
                prevToPopped = prevToPopped.next;
            }
            toReturn = prevToPopped.next.data;
            tail = prevToPopped;
            prevToPopped.next = null;
        }
        size--;
        return toReturn;
    }

    @Override
    public int find(T element) {
        Node<T> current = head;
        int count = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                return count; // Retorna la posicion del elemento en la lista
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public boolean erase(T element) {
        if (head == null) {
            return false;
        }

        Node<T> prev = head;

        if (head.data.equals(element)) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                prev.next = null;
            }
            size --;
            return true;
        }

        while (prev.next != null) {
            if (prev.next.data.equals(element)) {
                if (prev.next.next == null) {
                    tail = prev;
                    tail.next = null;
                } else {
                    prev.next = prev.next.next;
                }
                size--;
                return true;
            }
            prev = prev.next;
        }
        return false;
    }

    @Override
    public boolean addBefore(T element, T targetElement){
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (head.data.equals(targetElement)) {
            newNode.next = head;
            tail = head;
            head = newNode;
            size++;
            return true;
        }
        Node<T> prev = head;
        Node<T> current = head.next;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current;
                prev.next = newNode;
                size++;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addAfter(T element, T targetElement) {
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (tail.data.equals(targetElement)) {
            tail.next = newNode;
            tail = newNode;
            return true;
        }
        Node<T> current = head;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current.next;
                if (current == tail) {
                    tail = newNode;
                }
                current.next = newNode;
                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

///////////////////////////////////////////////// Double linked list no tail

class ListaDoblementeEnlazada<T> implements Lista<T> {
    private static class Node<T> {
        T data;
        Node<T> next = null;
        Node<T> prev = null;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    @Override
    public void pushFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void pushBack(T element){
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.prev = current;
        }
        size ++;
    }

    @Override
    public T popFront() {
        if (head == null) {
            return null;
        } else {
            Node<T> popped = head;
            if (head.next == null) {
                head = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }
            size--;
            popped.next = null;
            return popped.data;
        }
    }

    @Override
    public T popBack() {
        T toReturn;
        if (head == null) {
            return null;
        } else if (head.next == null) {
            toReturn = head.data;
            head = null;
        } else {
            Node<T> prevToPopped = head;
            while (prevToPopped.next.next != null) {
                prevToPopped = prevToPopped.next;
            }
            toReturn = prevToPopped.next.data;
            prevToPopped.next.prev = null;
            prevToPopped.next = null;
        }
        size--;
        return toReturn;
    }

    @Override
    public int find(T element) {
        Node<T> current = head;
        int count = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                return count; // Retorna la posicion del elemento en la lista
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public boolean erase(T element) {
        if (head == null) {
            return false;
        }
        if (head.data.equals(element)) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            size--;
            return true;
        }
        Node<T> current = head.next;
        while (current != null) {
            if (current.data.equals(element)) {
                current.prev.next = current.next;
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
                current.next = null;
                current.prev = null;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addBefore(T element, T targetElement){
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (head.data.equals(targetElement)) {
            pushFront(element);
            return true;
        }
        Node<T> current = head.next;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current;
                newNode.prev = current.prev;
                current.prev.next = newNode;
                current.prev = newNode;
                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addAfter(T element, T targetElement) {
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        Node<T> current = head;
        while(current != null){
            if (current.data.equals(targetElement)) {
                if (current.next == null) {
                    newNode.prev = current;
                    current.next = newNode;
                } else {
                    newNode.next = current.next;
                    current.next.prev = newNode;
                    newNode.prev = current;
                    current.next = newNode;
                }
                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

///////////////////////////////////////////////// Simple linked list With Tail

class ListaDoblementeEnlazadaConCola<T> implements Lista<T> {
    private static class Node<T> {
        T data;
        Node<T> next = null;
        Node<T> prev = null;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void pushFront(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void pushBack(T element){
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size ++;
    }

    @Override
    public T popFront() {
        if (head == null) {
            return null;
        } else {
            Node<T> popped = head;
            if (head.next == null) {
                head = tail = null;
            } else {
                head.next.prev = null;
                head = head.next;
            }
            size--;
            popped.next = null;
            return popped.data;
        }
    }

    @Override
    public T popBack() {
        if (tail == null) {
            return null;
        }
        T toReturn = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            toReturn = tail.data;
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return toReturn;
    }

    @Override
    public int find(T element) {
        Node<T> current = head;
        int count = 0;

        while (current != null) {
            if (current.data.equals(element)) {
                return count; // Retorna la posicion del elemento en la lista
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    @Override
    public boolean erase(T element) {
        if (head == null) {
            return false;
        }

        if (head.data.equals(element)) {
            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev.next = null;
                head.prev = null;
            }
            size --;
            return true;
        }

        Node<T> current = head.next;

        while (current != null) {
            if (current.data.equals(element)) {
                if (current == tail) {
                    tail = tail.prev;
                    tail.next.prev = null;
                    tail.next = null;
                } else {
                    current.next.prev = current.prev;
                    current.prev.next = current.next;
                    current.next = null;
                    current.prev = null;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addBefore(T element, T targetElement){
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (head.data.equals(targetElement)) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            size++;
            return true;
        }
        Node<T> current = head.next;
        while(current != null){
            if (current.data.equals(targetElement)) {
                newNode.next = current;
                newNode.prev = current.prev;
                current.prev.next = newNode;
                current.prev = newNode;

                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean addAfter(T element, T targetElement) {
        Node<T> newNode = new Node<>(element);
        if (head == null){
            return false;
        }
        if (tail.data.equals(targetElement)) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            size++;
            return true;
        }
        Node<T> current = head;
        while(current != tail){
            if (current.data.equals(targetElement)) {
                newNode.next = current.next;
                newNode.prev = current;
                current.next.prev = newNode;
                current.next = newNode;
                size++;
                return true;
            }
            current = current.next;
        }
        return false;
    }
}

///////////////////////////////////////////////// Dinamic Array Queue

class DynamicArrayQueue<T> implements MyQueue<T> {
    private T[] elements;
    private int size;
    private int capacity;
    private int front;
    private int rear;

    public DynamicArrayQueue(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public void enqueue(T element) {
        if (size == capacity) {
            int newCapacity = capacity*2;
            T[] newElements = (T[]) new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[(i + front) % capacity];
            }
            this.capacity = newCapacity;
            this.elements = newElements;
            this.front = 0;
            this.rear = size;
        }
        elements[rear] = element;
        rear = (rear + 1 ) % capacity;
        size++;
    }

    @Override
    public T dequeue() {
        if (size == 0) {
            return null;
        }
        T toReturn = elements[front];
        elements[front] = null;
        front = (front + 1) % capacity;
        size--;
        return toReturn;
    }

    @Override
    public T front() {
        if (size == 0) {
            return null;
        }
        return elements[front];
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean delete(T element) {
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            int currentIndex = (i + front) % capacity;
            if (elements[currentIndex].equals(element)) {
                int lastIndex = (rear + capacity -1) % capacity;
                elements[currentIndex] = elements[lastIndex];
                elements[lastIndex] = null;
                rear = lastIndex;
                size--;
                return true;
            }
        }
        return false;
    }
}

///////////////////////////////////////////////// Dinamic Array Stack

class DynamicArrayStack<T> implements MyStack<T> {
    private T[] elements;
    private int size;
    private int capacity;

    public DynamicArrayStack(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
    }

    @Override
    public void push(T element) {
        if (size == capacity) {
            int newCapacity = capacity*2;
            T[] newElements = (T[]) new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            this.capacity = newCapacity;
            this.elements = newElements;
        }
        elements[size] = element;
        size++;
    }

    @Override
    public T pop() {
        if (size == 0) {
            return null;
        }
        size--;
        T lastElement = elements[size];
        elements[size] = null;
        return lastElement;
    }

    @Override
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return elements[size-1];
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean delete(T element) {
        for (int i = size-1; i >= 0; i--) {
            if (elements[i].equals(element)) {
                for (int j = i; j < size-1; j++) {
                    elements[j] = elements[j+1];
                }
                size--;
                elements[size] = null;
                return true;
            }
        }
        return false;
    }
}

public class Main {

    public static void exec(int size, String method, Runnable operation) {
        Instant start = Instant.now();

        operation.run();

        Instant finish = Instant.now();

        long nanos = Duration.between(start, finish).toNanos();
        long micros = nanos / 1000;

        if (micros < 10) {
          System.out.printf("Se ejecutó %-22s para %7d elementos en: %6d nanos\n", method, size, nanos);
        } else {
          System.out.printf("Se ejecutó %-22s para %7d elementos en: %6d µs\n", method, size, micros);
        }
    }

    public static void main(String[] args) {
        final int start = 10;
        final int end = 100000000;

        for (int size = start; size <= end; size *= 10) {
            System.out.println("\n================ PRUEBAS CON SIZE = " + size + " ================");
            final int currentSize = size;
            final int targetPeorCaso = 0;

            ListaSimplementeEnlazada<Integer> listaLlenaSinCola = new ListaSimplementeEnlazada<>();
            for (int i = 0; i < currentSize; i++) listaLlenaSinCola.pushFront(i);

            // ---------------- SINGEL LINKED LIST SIN TAIL ----------------
            exec(size, "find - No tail", () -> listaLlenaSinCola.find(targetPeorCaso));
            exec(size, "addBefore - No tail", () -> listaLlenaSinCola.addBefore(-1, targetPeorCaso));
            exec(size, "addAfter - No tail", () -> listaLlenaSinCola.addAfter(-1, targetPeorCaso));
            exec(size, "pushFront - No tail", () -> listaLlenaSinCola.pushFront(-1));
            exec(size, "popFront - No tail", () -> listaLlenaSinCola.popFront());
            exec(size, "pushBack - No tail", () -> listaLlenaSinCola.pushBack(-1));
            exec(size, "popBack - No tail", () -> listaLlenaSinCola.popBack());
            exec(size, "erase - No tail", () -> listaLlenaSinCola.erase(targetPeorCaso));

            System.out.println("------------------------- PRUEBAS CON TAIL -------------------------");

            ListaSimplementeEnlazadaConCola<Integer> listaLlenaConCola = new ListaSimplementeEnlazadaConCola<>();
            for (int i = 0; i < currentSize; i++) listaLlenaConCola.pushFront(i);

            // ---------------- SINGLE LINKED LST CON TAIL ----------------
            exec(size, "find - con tail", () -> listaLlenaConCola.find(targetPeorCaso));
            exec(size, "addBefore - con tail", () -> listaLlenaConCola.addBefore(-1, -1)); // Si no existe la referencia
            exec(size, "addAfter - con tail", () -> listaLlenaConCola.addAfter(-1, -1)); // Si no existe la referencia
            exec(size, "pushFront - con tail", () -> listaLlenaConCola.pushFront(-1));
            exec(size, "popFront - con tail", () -> listaLlenaConCola.popFront());
            exec(size, "pushBack - con tail", () -> listaLlenaConCola.pushBack(-1));
            exec(size, "popBack - con tail", () -> listaLlenaConCola.popBack());
            exec(size, "erase - con tail", () -> listaLlenaConCola.erase(targetPeorCaso));

            System.out.println("------------------- PRUEBAS DOBLEMENTE ENLAZADA -------------------");

            ListaDoblementeEnlazada<Integer> listaLlenaDoble = new ListaDoblementeEnlazada<>();
            for (int i = 0; i < currentSize; i++) listaLlenaDoble.pushFront(i);

            // ---------------- DOUBLE LINKED LIST SIN TAIL ----------------
            exec(size, "find - doble", () -> listaLlenaDoble.find(targetPeorCaso));
            exec(size, "addBefore - doble", () -> listaLlenaDoble.addBefore(-1, -1)); // Si no existe la referencia
            exec(size, "addAfter - doble", () -> listaLlenaDoble.addAfter(-1, -1)); // Si no existe la referencia
            exec(size, "pushFront - doble", () -> listaLlenaDoble.pushFront(-1));
            exec(size, "popFront - doble", () -> listaLlenaDoble.popFront());
            exec(size, "pushBack - doble", () -> listaLlenaDoble.pushBack(-1));
            exec(size, "popBack - doble", () -> listaLlenaDoble.popBack());
            exec(size, "erase - doble", () -> listaLlenaDoble.erase(targetPeorCaso));

            System.out.println("--------------- PRUEBAS DOBLEMENTE ENLAZADA CON TAIL ---------------");

            ListaDoblementeEnlazadaConCola<Integer> listaLlenaDobleConTail = new ListaDoblementeEnlazadaConCola<>();
            for (int i = 0; i < currentSize; i++) listaLlenaDobleConTail.pushFront(i);

            // ---------------- DOUBLE LINKED LIST CON TAIL ----------------
            exec(size, "find - dobleTail", () -> listaLlenaDobleConTail.find(targetPeorCaso));
            exec(size, "addBefore - dobleTail", () -> listaLlenaDobleConTail.addBefore(-1, -1)); // Si no existe la referencia
            exec(size, "addAfter - dobleTail", () -> listaLlenaDobleConTail.addAfter(-1, -1)); // Si no existe la referencia
            exec(size, "pushFront - dobleTail", () -> listaLlenaDobleConTail.pushFront(-1));
            exec(size, "popFront - dobleTail", () -> listaLlenaDobleConTail.popFront());
            exec(size, "pushBack - dobleTail", () -> listaLlenaDobleConTail.pushBack(-1));
            exec(size, "popBack - dobleTail", () -> listaLlenaDobleConTail.popBack());
            exec(size, "erase - dobleTail", () -> listaLlenaDobleConTail.erase(targetPeorCaso));

            System.out.println("--------------- COMPARACIÓN MÉTODOS SEMEJANTES ---------------");

            // ---------------- COMPARACIÓN MÉTODOS SEMEJANTES ----------------
            DynamicArrayStack<Integer> pila = new DynamicArrayStack<>(2);
            ListaSimplementeEnlazada<Integer> lista = new ListaSimplementeEnlazada<>();
            DynamicArrayQueue<Integer> cola = new DynamicArrayQueue<>(2);

            exec(size, "push - pila", () -> {
              for (int i = 0; i < currentSize; i++) pila.push(i);
            });
            exec(size, "push - lista", () -> {
              for (int i = 0; i < currentSize; i++) lista.pushFront(i);
            });
            exec(size, "enqueue - cola", () -> {
              for (int i = 0; i < currentSize; i++) cola.enqueue(i);
            });
            exec(size, "find - lista", () -> {
              for (int i = 0; i < currentSize; i++) pila.delete(-1);
            });
            exec(size, "delete - pila", () -> {
              for (int i = 0; i < currentSize; i++) lista.erase(-1);
            });
            exec(size, "delete - cola", () -> { 
              for (int i = 0; i < currentSize; i++) cola.delete(-1);
            });
            exec(size, "pop - pila", () -> {
              for (int i = 0; i < currentSize; i++) pila.pop();
            });
            exec(size, "pop - lista", () -> {
              for (int i = 0; i < currentSize; i++) lista.popFront();
            });
            exec(size, "dequeue - cola", () -> { 
              for (int i = 0; i < currentSize; i++) cola.dequeue();
            });

        }
    }
}
