package com.amdudda;

import java.io.*;
import java.util.*;

public class Coffeeshop {

    public static void main(String[] args) throws IOException {
        /*
        Each product has a unique name, so we can use that
        as a key for a hashmap linking to an arraylist that
        stores the cost to make and sale price of each product
        index 0 will be the cost to make
        index 1 will be the price it's sold at
        index 2 will be the quantity sold that day for that product.
        In a production environment, I'd probably just use a Double[], since the array is a fixed size,
        but I wanted to practice using ArrayList.
        */
        ArrayList<Beverage> productInfo = new ArrayList<Beverage>();

        /* DONE: Exception handling for bad data - we want to wrap the program, because we don't
        want the report to generate if we have bad data in coffee.txt
         */
        try {
            // DONE: read in data from coffee.txt
            fetchData(productInfo);

            // DONE: get data about quantities sold
            salesData(productInfo);

            // DONE: Generate sales report
            createReport(productInfo);
        }
        catch (NumberFormatException nfe) {
            // the majority of errors not handled by other trapping will be number format problems in coffee.txt
            System.out.println("The program expects a number and got something else instead.");
            System.out.println("Check coffee.txt for errors in input or data layout and try running the program again.");
            System.out.println(nfe.toString());
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("Unable to find coffee.txt in data directory.  Please find a copy of the data and try " +
                    "re-running the program.");
            System.out.println(fnfe.toString());
        }
        catch (Exception e) {
            // Just in case it's a different problem, let's make a useful suggestion to users for the likely
            // source of the problem.
            System.out.println("Oops, something went wrong.  It's probably a problem in coffee.txt - look for errors " +
                    "in the data or layout of the pricing information.  Then try rerunning the program.");
            System.out.println(e.toString());
        } // end try-catch block
    } // end main

    private static void createReport(ArrayList<Beverage> p_info) throws IOException {
        // takes data and generates report, stored as "sales-report.txt"
        // set up our data streams
        String fname = "./data/sales-report.txt";
        File f = new File(fname);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bufWrite = new BufferedWriter(fw);

        // DONE: report header
        String reportHead = "Daily Coffeeshop Sales Report\n\n" +
                "==========================================================\n\n";
        bufWrite.write(reportHead);

        // DONE: report body
        createReportBody(p_info,bufWrite);

        // Done: report footer
        createReportFooter(p_info,bufWrite);

        // close our data streams
        bufWrite.close();
        fw.close();
    }  // end createReport

    private static void createReportBody(ArrayList<Beverage> p_info, BufferedWriter bw) throws IOException {
        // generates the body of the daily sales report
        String cur_line;
        double sale_price, cost_to_make;
        int qty_sold;
        for (Beverage bevvy:p_info) {
            // get our values from the Beverage object
            cost_to_make = bevvy.getCost();
            sale_price = bevvy.getPrice();
            qty_sold = bevvy.getQty_sold();
            // use those values to build up a long string storing that product's sales info
            cur_line = bevvy.getName().substring(0,1).toUpperCase() + bevvy.getName().substring(1);
            cur_line += String.format(" sold:\t%.0f", qty_sold);
            cur_line += String.format("\tExpenses $%.2f", qty_sold*cost_to_make);
            cur_line += String.format("\tRevenue $%.2f", qty_sold*sale_price);
            cur_line += String.format("\tProfit $%.2f\n", (qty_sold*sale_price)-(qty_sold*cost_to_make));
            // write that sales info to the report.
            bw.write(cur_line);
        }
    } // end createReportBody

    private static void createReportFooter(ArrayList<Beverage> p_info, BufferedWriter bW) throws IOException {
        // adds up totals and creates final line of report. summing up expenses and revenue
        double qty_sold, cost_to_make, sale_price;
        double expenses_total=0d,revenue_total=0d,net_profit;
        for (Beverage bevvy:p_info) {
            // get our values from the Beverage object
            cost_to_make = bevvy.getCost();
            sale_price = bevvy.getPrice();
            qty_sold = bevvy.getQty_sold();
            expenses_total += qty_sold*cost_to_make;
            revenue_total += qty_sold*sale_price;
        }
        // now calculate net profit, which is revenue minus expenses
        net_profit = revenue_total-expenses_total;

        // concatenate the last line of the report
        String last_line = String.format("Daily totals:\tExpenses $%.2f\tSales $%.2f\tProfit $%.2f",
                expenses_total, revenue_total, net_profit);

        // and write out the final lines of the report
        bW.write("\n==========================================================\n\n");
        bW.write(last_line);
    }  // end createReportFooter

    private static void salesData(ArrayList<Beverage> p_info) {
        // gets user input and appends to the array list for each product
        // initialize scanner for data input
        Scanner s = new Scanner(System.in);
        int d;  // will hold user input; set negative so it hits first while loop
        for (Beverage bevvy : p_info) {
            // DONE: error handling & data validation
            // need to set d to be negative so it loops once
            d = -1; // reset d to be negative so it hits first while loop
            while (d < 0) {
                try {
                    System.out.println("How many units of " + bevvy + " were sold today?");
                    d = s.nextInt();
                    if (d < 0) System.out.println("You entered a negative number. Please reenter.");
                } catch (InputMismatchException ime) {
                    System.out.println("You do not seem to have entered a whole number (no decimals).  Please try again.");
                    s = new Scanner(System.in);
                } catch (Exception e) {
                    System.out.println("Something seems to be wrong with your input.  Please try again.");
                    System.out.println(e.toString());
                    s = new Scanner(System.in);
                }  // end try-catch
            } // end while
            // update the beverage's qty_sold attribute with user's input
            bevvy.setQty_sold(d);
        } // end for

    } // end salesData

    private static void fetchData(ArrayList<Beverage> p_info) throws IOException {
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
            // decompose the data - notice it's still string data!
            String prod_name = line.substring(0, line.indexOf(";"));
            double cost_to_make = Double.parseDouble(line.substring(line.indexOf(";") + 1, line.lastIndexOf(";")));
            double sale_price = Double.parseDouble(line.substring(line.lastIndexOf(";") + 1));

            // create a new beverage and populate with data extracted above.
            Beverage drink = new Beverage(prod_name,cost_to_make,sale_price);

            // move to the next line
            line = bufRead.readLine();
        } // end while loop

        // close the data streams
        bufRead.close();
        fr.close();
    }  // end fetchData

} // end Coffeeshop
