package com.rbricks.appsharing.concept.Activities.concept.fragments;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gopikrishna on 19/01/17.
 */

public class LruCache {

    class LruNode {
        int value;
        LruNode left;
        LruNode right;

        public LruNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    LruNode root;
    Map<Integer, Integer> map = new HashMap<>();


    public void addElementes(int[] values) {
        if (values.length == 0) {
            return;
        }
        int size = values.length;
        for (int index = 0; index < values.length; index++) {
            int i = values[index];
            if (map.containsKey(i)) {
                Integer position = map.get(i);
                // remove element at position n update the linkedlist
            }
            if (map.size() <= size) {
                map.put(i, index);
                if (root == null) {
                    root = new LruNode(i);
                    return;
                } else {
                    LruNode nodeValue = root;
                    while (nodeValue.right != null) {
                        nodeValue = nodeValue.right;
                    }
                    nodeValue.right = new LruNode(i);
                }
            } else {
                // every time evict
//                int value = root.value;
                // if not map.contains then only do this .
                LruNode temp = root.right;
                temp.left = null;
                root.right = null;
                root = temp;
                //evict this item
                LruNode nodeValue = root;
                while (nodeValue.right != null) {
                    nodeValue = nodeValue.right;
                }
                nodeValue.right = new LruNode(i);

            }
        }
    }


}
