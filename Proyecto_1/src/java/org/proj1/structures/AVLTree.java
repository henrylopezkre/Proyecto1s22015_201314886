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
        System.out.println(count);
        if(count > 1){
            this.rootNode = rotationRL(this.rootNode, this.rootNode.rightNode);
        }
        this.count++;
    }
    //Rotación derecha-derecha
    public TreeNode rotationRR(TreeNode n, TreeNode n1){
        n.rightNode = n1.leftNode;
        n1.leftNode = n;
        if(n1.balanceFactor == 1){
            n.balanceFactor = 0;
            n1.balanceFactor = 0;
        }else{
            n.balanceFactor = 1;
            n1.balanceFactor = -1;
        }
        //
        n.fatherNode = n1.fatherNode = n1;
        return n1;
    }
    //Rotación izquierda-izquierda
    public TreeNode rotationLL(TreeNode n, TreeNode n1){
        n.leftNode = n1.rightNode;
        n1.rightNode = n;
        if(n1.balanceFactor == -1){
            n.balanceFactor = 0;
            n1.balanceFactor = 0;
        }else{
            n.balanceFactor = -1;
            n1.balanceFactor = 1;
        }
        //
        n.fatherNode = n1.fatherNode = n1;
        return n1;
    }
    //Rotación izquierda-derecha
    public TreeNode rotationLR(TreeNode n, TreeNode n1){
        TreeNode n2;
        n2 = n1.rightNode;
        //Rotación izquierda-izquierda
        n.leftNode = n2.rightNode;
        n2.rightNode = n;
        //Rotación derecha-derecha
        n1.rightNode = n2.leftNode;
        n2.leftNode = n1;
        if(n2.balanceFactor == 1){
            n1.balanceFactor = -1;
        }else{
            n1.balanceFactor = 0;
        }
        if(n2.balanceFactor == -1){
            n.balanceFactor = 1;
        }else{
            n.balanceFactor = 0;
        }
        n2.balanceFactor = 0;
        n.fatherNode = n1.fatherNode = n2.fatherNode = n2;
        return n2;
    }
    public TreeNode rotationRL(TreeNode n, TreeNode n1){
        TreeNode n2;
        n2 = n1.leftNode;
        //Rotación derecha-derecha
        n.rightNode = n2.leftNode;
        n2.leftNode = n;
        //Rotación izquierda-izquierda
        n1.leftNode = n2.rightNode;
        n2.rightNode = n1;
        if(n2.balanceFactor == 1){
            n.balanceFactor = -1;
        }else{
            n.balanceFactor = 0;
        }
        if(n2.balanceFactor == -1){
            n1.balanceFactor = 1;
        }else{
            n1.balanceFactor = 0;
        }
        n2.balanceFactor = 0;
        n.fatherNode = n1.fatherNode = n2.fatherNode = n2;
        return n2;
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
            node.balanceFactor = heightRight - heightLeft;
            balance(node.leftNode);
            balance(node.rightNode);
        }
    }
    private void print(TreeNode node){
        if(node != null){
            print(node.leftNode);
            printWriter.print("\t tn_avlt" + node.object + "[label = \"{ <e> | Valor. " + node.object + " \n f.e. " + node.balanceFactor + "| <p> }\", style=\"filled\", color=\"black\", fillcolor=\"skyblue\"]; \n");
            System.out.println("Padre: " + node.fatherNode.object + " Nodo: " + node.object);
            if(node.fatherNode.object != node.object){
                printWriter.print("\t tn_avlt" + node.fatherNode.object + ":p -> tn_avlt"+ node.object + ":e; \n");
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
    public int object;
    public TreeNode fatherNode;
    public TreeNode rightNode;
    public TreeNode leftNode;
    public int balanceFactor;
    public TreeNode(){
        this.fatherNode = null;
        this.leftNode = null;
        this.rightNode = null;
        this.object = 0;
        this.balanceFactor = 0;
    }
}
