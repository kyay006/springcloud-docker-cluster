package com.liu.spring.springzuul;

import java.util.ArrayList;

public class LinkList {

    class Node{
        int data;
        Node next;

        public Node(int data){
            this.data = data;
        }
    }

    public Node head;
    public Node current;

    public void add(int data){
        if(head == null){
            head = new Node(data);
            current = head;
        }else{
            current.next = new Node(data);
            //当前节点移向最新位置
            current = current.next;
        }
    }


    //遍历
    public void print(Node node){
        if(node == null){
            return;
        }
        while (node != null){
            System.out.println(node.data);
            node = node.next;
        }
    }

    //查找链表的中间节点，不允许先遍历长度
    public void centerNode(LinkList linkList)
    {
        if(linkList == null){
            return;
        }

        Node first = linkList.head;
        Node one   = linkList.head;
        while(first != null && first.next != null){
            first = first.next.next;
            one = one.next;
        }
        System.out.println("中间节点：" + one.data);
    }


    public static void main(String[] args) {
        LinkList linkList = new LinkList();
        linkList.add(4);
        linkList.add(23);
        linkList.add(55);
        linkList.add(324);
        linkList.add(56);

//        linkList.print(linkList.head);
        linkList.centerNode(linkList);

        ArrayList a;
    }



}
