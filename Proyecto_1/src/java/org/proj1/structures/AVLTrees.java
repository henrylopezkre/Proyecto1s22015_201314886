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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sorge
 */
public class AVLTrees {
    private int count;
    private TreeNode rootNode;
    private FileWriter fileWriter = null;
    private PrintWriter printWriter = null;
    public AVLTrees(){
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
                        verify = true;
                    }else{
                        auxNode = auxNode.rightNode;
                    }
                }else if(object == auxNode.object){
                    verify = true;
                }
            }
        }
        //Colocar factor de equilibrio en cada nodo
        this.balance(this.rootNode);   
        TreeNode n = this.equilibrate(this.rootNode);
        System.out.println("Soy la raíz: " + n.object);
        this.rootNode = n;
    }
    /*public void add(int object) throws Exception{
        Logical l = new Logical(false);
        int o = object;
        this.rootNode = insertNode(this.rootNode, o, l, 0); 
    }
    private TreeNode insertNode(TreeNode root , int object, Logical l, int option) throws Exception{
        boolean verify = false;
        TreeNode auxNode = root;
        System.out.println("Estoy ingresando uno nuevo: " + object);
        switch(option){
            case 0:
                if(auxNode == null){
                    auxNode = new TreeNode();
                    auxNode.object = object;
                    System.out.println("Raíz:" + auxNode.object);
                    auxNode.fatherNode = auxNode;
                    root = auxNode;
                    l.setResp(true);
                    verify = true;
                }
                break;
            case 1:
                if(auxNode.leftNode == null){
                    System.out.println("Entre aca");
                    auxNode.leftNode = new TreeNode();
                    auxNode.leftNode.object = object;
                    System.out.println("Izquierdo: " + auxNode.leftNode.object);
                    auxNode.leftNode.fatherNode = auxNode;
                    auxNode = auxNode.leftNode;
                    root = auxNode;
                    verify = true;
                    l.setResp(true);
                }else{
                    auxNode = auxNode.leftNode;
                    root = auxNode;
                }
                break;
            case 2:
                if(auxNode.rightNode == null){
                    auxNode.rightNode = new TreeNode();
                    auxNode.rightNode.object = object;
                    System.out.println("Derecho: " + auxNode.rightNode.object);
                    auxNode.rightNode.fatherNode = auxNode;
                    auxNode = auxNode.rightNode;
                    root = auxNode;
                    verify = true;
                    l.setResp(true);
                }else{
                    auxNode = auxNode.rightNode;
                    root = auxNode;
                }
                break;
        }
        if(!verify){
        if(object < root.object){
            TreeNode auxLeft;
            auxLeft = insertNode(root, object, l, 1);
            this.balance(this.rootNode);
            //System.out.println("Actual izquierdo: " + auxLeft.object + " fe: " + auxLeft.balanceFactor);
        }else if(object > root.object){
            TreeNode auxRight;
            auxRight = insertNode(root, object, l, 2);
            //System.out.println("Actual derecho: " + auxRight.object + " fe: " + auxRight.balanceFactor);
            
        }else if(object == root.object){
            throw new Exception("No pueden haber claves repetidas");
        }
        }
        this.balance(this.rootNode);
        this.rootNode = this.equilibrate(this.rootNode);
        return root;
    }*/
    
    public TreeNode equilibrate(TreeNode node){
        TreeNode auxNode = null;
        if(node != null){
            auxNode = node;
            switch(auxNode.balanceFactor){
                case 2:
                    TreeNode n1 = auxNode.rightNode;
                    if(n1.balanceFactor == +1){
                        TreeNode auxRoot = null;
                        if(auxNode.object != auxNode.fatherNode.object){
                             auxRoot = auxNode.fatherNode;
                        }
                        TreeNode n = node;
                        TreeNode aux = null;
                        if(n1.leftNode != null){
                            aux = n1.leftNode;
                            n1.leftNode = null;
                        }
                        auxNode = rotationRR(n, n1);
                        if(auxRoot != null){
                            auxRoot.rightNode = auxNode;
                            auxNode.fatherNode = auxRoot;
                        }
                        if(aux != null){
                            this.add(aux.object);
                        }
                        this.balance(this.rootNode);
                    }else{
                        TreeNode n = auxNode.fatherNode;
                        auxNode.rightNode = equilibrate(auxNode.rightNode);
                        
                        System.out.println("Esto devuelvo2: " + auxNode.object);
                    }
                    
                    break;
            }
        }      
        return auxNode;
    }
    //Rotación derecha-derecha
    public TreeNode rotationRR(TreeNode n, TreeNode n1){
        n.rightNode = n1.leftNode;
        n1.leftNode = n;
        if(n1.balanceFactor == +1){
            n.balanceFactor = 0;
            n1.balanceFactor = 0;
        }else{
            n.balanceFactor = +1;
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
        if(n2.balanceFactor == +1){
            n1.balanceFactor = -1;
        }else{
            n1.balanceFactor = 0;
        }
        if(n2.balanceFactor == -1){
            n.balanceFactor = +1;
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
        if(n2.balanceFactor == +1){
            n.balanceFactor = -1;
        }else{
            n.balanceFactor = 0;
        }
        if(n2.balanceFactor == -1){
            n1.balanceFactor = +1;
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
