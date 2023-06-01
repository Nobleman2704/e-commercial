package com.example.ecommercial;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        LinkedList linkedList = new LinkedList();
//        linkedList.get(4);
//
//        Vector vector = new Vector();
//        vector.get(5);
//
//        Stack stack = new Stack();
//        stack.get(5);
//
//        Set set = new HashSet();
//        set.add(4);
//
//        HashSet<Integer> hashSet = new HashSet<>();
//        Collection collection = new ArrayList();
//
//        SortedSet sortedSet = new TreeSet();
//        sortedSet.add(5);
//
//        NavigableSet navigableSet = new TreeSet();
//
//        TreeSet treeSet = new TreeSet();
//
//        PriorityQueue queue = new PriorityQueue();
//        queue.add(5);
//
//        HashMap map = new HashMap();
//
//        IdentityHashMap<String, Integer> identityHashMap = new IdentityHashMap<>();
//
//        String word = new String("12");
//        String word2 = word;
//        String word3 = word2;
//
//        identityHashMap.put(word, 1);
//        identityHashMap.put(word2, 1);
//        identityHashMap.put(word3, 1);
//
//        Set<Map.Entry<String, Integer>> entries = identityHashMap.entrySet();
//        for (Map.Entry<String, Integer> entry : entries) {
//            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
//        }
//
//        HashMap<String, Integer> hashMap = new HashMap<>();
//        hashMap.put(word, 1);
//        hashMap.put(word2, 1);
//        hashMap.put(word3, 1);
//
//        System.out.println("\n hashmap");
//
//        Set<Map.Entry<String, Integer>> entries1 = hashMap.entrySet();
//        for (Map.Entry<String, Integer> entry : entries1) {
//            System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
//        }

        WeakHashMap weakHashMap = new WeakHashMap();
        weakHashMap.put(5, 8);
        weakHashMap.put(7, 8);

        System.out.println("weakHashMap.size() = " + weakHashMap.size());

        Object o = weakHashMap.get(7);
        o=null;

        System.gc();

        System.out.println("weakHashMap.size() = " + weakHashMap.size());

        ArrayList arrayList = new ArrayList();


    }
}
