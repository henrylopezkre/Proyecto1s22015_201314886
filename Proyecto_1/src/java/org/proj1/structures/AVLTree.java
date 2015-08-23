/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proj1.structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Sorge
 */
public class AVLTree {
    private int count;
    private TreeNode rootNode;
    private FileWriter fileWriter = null;
    private PrintWriter printWriter = null;
    public AVLTree(){
        this.rootNode = null;
        this.count = 0;
    }
    public boolean isEmpty(){
        return this.rootNode == null;
    }
    //Agregar 
    public void add(int object){
        boolean verify = false;
        TreeNode auxNode;
        if(isEmpty()){
            auxNode = new TreeNode();
            auxNode.object = object;
            auxNode.num = count;
            auxNode.fatherNode = auxNode;
            auxNode.leftNode = null;
            auxNode.rightNode = null;
            this.rootNode = auxNode;
            System.out.println("Raíz:" + this.rootNode.object);
            verify = true;
        }else{
            auxNode = this.rootNode;
            while(!verify){
                if(object < auxNode.object){
                    if(auxNode.leftNode == null){
                        auxNode.leftNode = new TreeNode();
                        auxNode.leftNode.object = object;
                        auxNode.leftNode.num = count;
                        System.out.println("Izquierdo: " + auxNode.leftNode.object);
                        auxNode.leftNode.fatherNode = auxNode;
                        auxNode = auxNode.leftNode;
                        auxNode.leftNode = null;
                        auxNode.rightNode = null;
                        
                        verify = true;
                    }else{
                        auxNode = auxNode.leftNode;
                    }
                }else if(object > auxNode.object){
                    if(auxNode.rightNode == null){
                        auxNode.rightNode = new TreeNode();
                        auxNode.rightNode.object = object;
                        auxNode.rightNode.num = count;
                        System.out.println("Derecho: " + auxNode.rightNode.object);
                        auxNode.rightNode.fatherNode = auxNode;
                        auxNode = auxNode.rightNode;
                        auxNode.leftNode = null;
                        auxNode.rightNode = null;
                        verify = true;
                    }else{
                        auxNode = auxNode.rightNode;
                    }
                }else if(object == auxNode.object){
                    verify = true;
                }
            }
        }
        this.balance(this.rootNode);
        this.count++;
    }
    //Obtener altura desde un nodo
    public int height(TreeNode node){
        if(node == null){
            return 0;
        }
        int heightLeft = 0;
        int heightRight = 0;
        heightLeft = height(node.leftNode);
        heightRight = height(node.rightNode);
        if(heightLeft > heightRight){
            return heightLeft + 1;
        }else{
            return heightRight + 1;
        }
    }
    //Colocar factor de equilibrio en nodos
    public void balance(TreeNode node){
        if(node != null){
            int heightLeft = height(node.leftNode);
            int heightRight = height(node.rightNode);
            node.balance = heightRight - heightLeft;
            balance(node.leftNode);
            balance(node.rightNode);
        }
    }
    private void print(TreeNode node){
        if(node != null){
            print(node.leftNode);
            printWriter.print("\t tn_avlt" + node.num + "[label = \"{ <e> | Valor. " + node.object + " \n f.e. " + node.balance + "| <p> }\", style=\"filled\", color=\"black\", fillcolor=\"skyblue\"]; \n");
            if(node.fatherNode.num != node.num){
                printWriter.print("\t tn_avlt" + node.fatherNode.num + ":p -> tn_avlt"+ node.num + ":e; \n");
            }
            count++;
            print(node.rightNode);
        }
    }
    public void printGraphviz() throws IOException{
        TreeNode auxNode = this.rootNode;
        File directorio = new File(".\\Reportes");
        if(!directorio.exists()){
            directorio.mkdirs();
        }
        fileWriter = new FileWriter(".\\Reportes\\avl.dot");
        printWriter = new PrintWriter(fileWriter);
        printWriter.println("digraph G {");
        printWriter.println("\t rankdir = TB; \n");
        printWriter.println("\t node[shape=record]; \n");
        printWriter.println("\t subgraph clusterAVL { \n");
        printWriter.println("\t label = \"Árbol AVL\"; \n");
        //printWriter.print("\t tn_avlt" + auxNode.num + "[label = \"{ <e> | Valor. " + auxNode.object + " \n f.e. " + auxNode.balance + "| <p> }\", style=\"filled\", color=\"black\", fillcolor=\"skyblue\"]; \n");
        print(auxNode);
        printWriter.print("\t } \n");
        printWriter.print("\t } \n");
        printWriter.close();
        String dotPath = "C:\\Program Files (x86)\\Graphviz2.30\\bin\\dot.exe";
        String fileInputPath = ".\\Reportes\\avl.dot";
        String fileOutputPath = ".\\Reportes\\avl.jpg";
        String tParam = "-Tjpg";
        String tOParam = "-o";
        String[] cmd = new String[5];
        cmd[0] = dotPath;
        cmd[1] = tParam;
        cmd[2] = fileInputPath;
        cmd[3] = tOParam;
        cmd[4] = fileOutputPath;
        Runtime rt = Runtime.getRuntime();
        rt.exec(cmd);
    }
}
class TreeNode{
    public int num;
    public int object;
    public TreeNode fatherNode;
    public TreeNode rightNode;
    public TreeNode leftNode;
    public int balance;
    public TreeNode(){
        this.fatherNode = null;
        this.leftNode = null;
        this.rightNode = null;
        this.object = 0;
        this.num = 0;
    }
}
