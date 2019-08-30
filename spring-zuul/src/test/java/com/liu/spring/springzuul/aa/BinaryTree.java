package com.liu.spring.springzuul.aa;

public class BinaryTree {

    private TreeNode root;


    public void insert(int data)
    {
        TreeNode treeNode = new TreeNode();
        treeNode.setValue(data);
        if(root == null){
            root = treeNode;
        }else
        {
            TreeNode current = root;
            TreeNode parent;

            while (true)
            {
                parent = current;
                if(treeNode.getValue() > current.getValue()){
                    //放右边
                    current = current.getRight();
                    if(current == null){
                        parent.setRight(treeNode);
                        break;
                    }
                }else{
                    //放左边
                    current = current.getLeft();
                    if(current == null){
                        parent.setLeft(treeNode);
                        break;
                    }
                }
            }
        }
    }

    //查找
    public void find(int data)
    {
        if(root == null){
            return;
        }
        TreeNode temp = root;
        while (temp != null){
            if(data == temp.getValue()){
                System.out.println(temp.getValue());
                return;
            }
            if(data > temp.getValue()){
                temp = temp.getRight();
            }else if(data <= temp.getValue()){
                temp = temp.getLeft();
            }
        }
        if(temp.getIsDelete()){
            return;
        }
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public static void main(String[] args) {

        BinaryTree aa = new BinaryTree();
        aa.insert(2);
        aa.insert(3);
        aa.insert(44);
        aa.insert(5);
        aa.insert(66);
        aa.insert(324);
        aa.insert(3);
        aa.insert(345);

        System.out.println(aa.getRoot().getValue());


        aa.find(33);
    }

}
