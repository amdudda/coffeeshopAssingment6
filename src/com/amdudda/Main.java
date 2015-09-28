package com.amdudda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        // DONE: read in data from coffee.txt
        /*
        Each product has a unique name, so we can use that
        as a key for a hashmap linking to an arraylist that
        stores the cost to make and sale price of each product
        index 0 will be the cost to make
        index 1 will be the price it's sold at
        index 2 will be the quantity sold that day for that product.
        */
        HashMap<String, ArrayList<Double>> productInfo = new HashMap<String, ArrayList<Double>>();
        fetchData(productInfo);

        // DONE: get data about quantities sold
        salesData(productInfo);

        // debugging
        for (String key:productInfo.keySet()) {
            System.out.println(key + ": cost = $" + productInfo.get(key).get(0)
                    + " sale = $" + productInfo.get(key).get(1) + " units = "
                    + productInfo.get(key).get(2));
        }
    }

    private static void salesData(HashMap<String, ArrayList<Double>> p_info) {
        // gets user input and appends to the array list for each product
        // initialize scanner for data input
        Scanner s = new Scanner(System.in);
        Double d;  // will hold user input
        for (String key:p_info.keySet()) {
            System.out.println("How many units of " + key + "were sold today?");
            d = s.nextDouble();
            // TODO: error handling & data validation
            p_info.get(key).add(d);
        }

    }

    private static void fetchData(HashMap<String, ArrayList<Double>> p_info) throws IOException {
        // we want to read in data from coffee.txt,
        // so we'll need to set up data streams for that.
        // doesn't return anything because the hashmap is being passed by reference
        String fn = "./data/coffee.txt";
        File f = new File(fn);
        FileReader fr = new FileReader(f);
        BufferedReader bufRead = new BufferedReader(fr);

        // read in the first line of the file
        String line = bufRead.readLine();
        // while there is still data to read, add it to the hashmap
        while (line != null) {
            // index 0 will be the cost to make
            // index 1 will be the price it's sold at
            ArrayList<Double> money = new ArrayList<>();
            // decompose the data - notice it's still string data!
            String prod_name = line.substring(0, line.indexOf(";"));
            String cost_to_make = line.substring(line.indexOf(";")+1, line.lastIndexOf(";"));
            String sale_price = line.substring(line.lastIndexOf(";")+1);
            // add the cost & sale info to the arraylist - make sure to convert to Double type
            money.add(Double.parseDouble(cost_to_make));
            money.add(Double.parseDouble(sale_price));
            p_info.put(prod_name, money);
            // move to the next line
            line = bufRead.readLine();
        } // end while loop

        // close the data streams
        bufRead.close();
        fr.close();
    }  // end fetchData
}
