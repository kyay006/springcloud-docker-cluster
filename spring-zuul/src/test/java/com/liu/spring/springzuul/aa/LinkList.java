package com.liu.spring.springzuul.aa;

public class LinkList {
    private Node head;
    private Node current;

    class Node{
        int data;
        Node next;

        public Node(int data){
            this.data = data;
        }
    }

    public void add(int data){
        if(head == null){
            head = new Node(data);
            current = head;
        }else{
            current.next = new Node(data);
            current = current.next;
        }
    }


    public void find(int data){
        if(head == null){
            return;
        }
        Node temp = head;
        while (temp != null){
            if(temp.data == data){
                System.out.println(temp);
                return;
            }
            temp = temp.next;
        }
    }


    public static void main(String[] args) {

        LinkList linkList = new LinkList();
        linkList.add(4);
        linkList.add(23);
        linkList.add(55);
        linkList.add(324);
        linkList.add(56);

        linkList.find(55);
    }



}
