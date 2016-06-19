package com.schellevis;

import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.util.ArrayList;

public class Main extends JPanel {

    public static void main(String[] args) {
	// write your code here

        ArrayList<Integer> items = ReadData.readCSV();

        int count = 0;
//        for (int item:items) {
//            System.out.println("nr: "+ count + " Value: " + item);
//            count++;
//        }
//        SES ses = new SES(items);
//
//        for (double alpha = 0.0; alpha < 1.00 ; alpha += 0.01) {
//            ses.setSmoothingLevel(alpha);
//            ses.doPrediction(12);
//        }
//
//        DES des = new DES(items);
//        des.doPrediction(12);

        GraphData.createAndShowGui();


    }

}
