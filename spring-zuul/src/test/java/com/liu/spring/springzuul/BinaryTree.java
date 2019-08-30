package com.liu.spring.springzuul;

public class BinaryTree {

    //根节点
    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }



    //插入操作
    public void insert(int value)
    {
        TreeNode treeNode = new TreeNode();
        treeNode.setValue(value);

        if(root == null){
            root = treeNode;
        }else
        {
            TreeNode currentNode = root;
            TreeNode parentNode  = null;

            while (true)
            {
                parentNode = currentNode;
                //往右放
                if(treeNode.getValue() > currentNode.getValue())
                {
                    currentNode = currentNode.getRightNode();
                    if(currentNode == null){
                        parentNode.setRightNode(treeNode);
                        return;
                    }
                }else
                {
                    //往左放
                    currentNode = currentNode.getLeftNode();
                    if(currentNode == null)
                    {
                        parentNode.setLeftNode(treeNode);
                        return;
                    }
                }
            }
        }
    }

    //查找
    public TreeNode find(int key)
    {
        TreeNode treeNode = root;
        if(treeNode == null){
            return null;
        }else
        {
            while (treeNode.getValue() != key)
            {
                if(treeNode.getValue() > key)
                {
                    treeNode = treeNode.getLeftNode();
                }else {
                    treeNode = treeNode.getRightNode();
                }

                if(treeNode == null){
                    return null;
                }
            }

            if(treeNode.isDelete()){
                return null;
            }else{
                return treeNode;
            }
        }
    }


    public static void main(String[] args) {
        //测试
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


        System.out.println(aa.find(3));
    }


}
