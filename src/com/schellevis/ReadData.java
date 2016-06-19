package com.schellevis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by geddy on 14/06/16.
 */
public class ReadData {

    static public ArrayList<Integer> readCSV() {
        ArrayList<Integer> items = new ArrayList<>();
        try {
            final Scanner data = new Scanner(new FileReader("forecast.csv"));
            int item = 0;
            while (data.hasNext()) {
                final String[] columns = data.nextLine().split(",");
                items.add(item, Integer.parseInt(columns[1]));
                item++;
            }
        } catch (Exception e) {
        }
        return items;
    }
}
