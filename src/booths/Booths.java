package booths;

import java.util.Scanner;

/**
 *
 * @author Saksham Ghimire, Nischal Shrestha
 */
public class Booths {

    //declare global variables;
    static String M; // Multiplier
    static String Q; // Multiplicand
    static String A; // neutral variable A=00000000
    static String Q_1; // Carry over variable
    static String operation; //store operation step details
    static String iteration; // carry iteration step numbers
    static String[] Qarr;
    static String Q_1arr;
    static String[] Aarr;
    static String[] aMinusM;
    static String aPlusM;
    static int i;

    //converting negative decimal number to 8-bit binary
    public static String negDecToBin(int number) {
        String c = Integer.toBinaryString(number);
        String d = c.substring(24, 32);
        return d;
    }

    // converting positive decimal number to 8-bit binary
    public static String posDecToBin(int number) {
        StringBuilder buf1 = new StringBuilder();
        StringBuilder buf2 = new StringBuilder();
        while (number != 0) {
            int digit = number % 2;
            buf1.append(digit);
            number = number / 2;
        }
        String binary = buf1.reverse().toString();
        int length = binary.length();
        if (length < 8) {
            while (8 - length > 0) {
                buf2.append("0");
                length++;
            }
        }
        String bin = buf2.toString() + binary;
        return bin;
    }

    // add two binary string and return a sum string
    public static String binaryAdd(String input0, String input1) {

        // Use as radix 2 because it's binary
        int number0 = Integer.parseInt(input0, 2);
        int number1 = Integer.parseInt(input1, 2);
        int sum = number0 + number1;
        String binarySum = posDecToBin(sum);

        String binarySum1 = "";

        if (binarySum.length() > 8) {
            binarySum1 = binarySum.substring((binarySum.length() - 8), (binarySum.length()));
        } else {
            binarySum1 = binarySum;
        }
        return binarySum1;
    }

    //2's complement
    public static String Twos(String number) {
        String input0 = flip(number);
        String input1 = "1";

        int number0 = Integer.parseInt(input0, 2);
        int number1 = Integer.parseInt(input1, 2);
        int i = number0 + number1;
        String sum = Integer.toBinaryString(i);

        return sum;
    }
    // 1's complement

    public static String flip(String number) {
        String inverted = number;
        String bin = number;
        String flipped = "";
        for (int j = 0; j < bin.length(); j++) {
            if (bin.charAt(j) == '0') {
                flipped += "1";
            } else {
                flipped += "0";
            }
        }
        int k = Integer.parseInt(flipped, 2);
        inverted = Integer.toBinaryString(k);
        return inverted;
    }

    public static String array2String(String a[]) {

        StringBuilder builder = new StringBuilder();
        for (String s : a) {
            builder.append(s);
        }
        return builder.toString();

    }

    public static String[] A_M(String a[], String b) {
        String d = Twos(b);
        String a1 = array2String(a);
        String A_M1 = binaryAdd(a1, d);
        String temp[];
        temp = A_M1.split("");


        return temp;


    }

    public static String[] addAM(String a[], String b) {
        String a1 = array2String(a);
        String AplusM1 = binaryAdd(a1, b);
        String temp[];
        temp = AplusM1.split("");


        return temp;


    }

    public static void determineOperation(String a, String b) {
        //System.out.println(a);
        switch (a) {
            case "0":
                if (b.equals("0")) {

                    rightShift();

                    //do for 00
                }
                if (b.equals("1")) {

                    String[] temp = addAM(Aarr, M);
                    Aarr = temp.clone();
                    operation = "A = A+M";
                    System.out.print(array2String(Aarr) + "    ");
                    System.out.print(array2String(Qarr) + "    ");
                    System.out.print(Q_1 + "    ");
                    System.out.println(operation);

                    rightShift();
                    //do for 01
                }
                break;
            case "1":
                if (b.equals("0")) {

                    aMinusM = A_M(Aarr, M);
                    Aarr = aMinusM.clone();
                    System.out.print(array2String(Aarr) + "    ");
                    System.out.print(array2String(Qarr) + "    ");
                    System.out.print(Q_1 + "    ");
                    operation = "A = A-M";
                    System.out.println(operation);

                    rightShift();

                    //do for 10
                }
                if (b.equals("1")) {

                    rightShift();
                    //do for 11
                }
                break;
            default:
                break;
        }
    }

    public static void rightShift() {

        String[] Aarr1 = Aarr.clone();
        String[] Qarr1 = Qarr.clone();
        String first = Aarr1[1];

        Aarr[1] = first;
        Aarr[2] = first;
        Aarr[3] = Aarr1[2];
        Aarr[4] = Aarr1[3];
        Aarr[5] = Aarr1[4];
        Aarr[6] = Aarr1[5];
        Aarr[7] = Aarr1[6];
        Aarr[8] = Aarr1[7];

        //ASR shift Q with respect to A

        Qarr[1] = Aarr1[8];
        Qarr[2] = Qarr1[1];
        Qarr[3] = Qarr1[2];
        Qarr[4] = Qarr1[3];
        Qarr[5] = Qarr1[4];
        Qarr[6] = Qarr1[5];
        Qarr[7] = Qarr1[6];
        Qarr[8] = Qarr1[7];


        // ASR shift Q_1 with respect to Q
        Q_1 = Qarr1[8];
        System.out.print(array2String(Aarr) + "    ");
        System.out.print(array2String(Qarr) + "    ");
        System.out.print(Q_1 + "    ");
        operation = "Arithmetic Right Shift        " + i;
        System.out.println(operation);


    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Multiplicand");
        int multiplicand = input.nextInt();
        System.out.println("Enter Multiplier");
        int multiplier = input.nextInt();

 if ((multiplier>=-128 && multiplier<=127)&& 
     (multiplicand>=-128 && multiplicand<=127) ){

        if (multiplicand < 0) {
            M = "" + negDecToBin(multiplicand);
        } else if (multiplicand >= 0) {
            M = "" + (posDecToBin(multiplicand));

        }
        if (multiplier < 0) {
            Q = "" + (negDecToBin(multiplier));
        } else if (multiplier >= 0) {
            Q = "" + (posDecToBin(multiplier));

        }

        System.out.println("Multiplier = Q = " + Q);
        System.out.println("Multiplicand = M = " + M);
        System.out.println();



        A = "00000000";

        Qarr = Q.split("");
        //System.out.println(array2String(Qarr));
        Q_1 = "0";

        Aarr = A.split("");

        System.out.println("A               " + "Q     " + " Q-1 " + "  Operation  " + "              Iteration");
        System.out.println("--------------------------------------------------------------------");
        System.out.print(array2String(Aarr) + "    ");
        System.out.print(array2String(Qarr) + "    ");
        System.out.print(Q_1 + "    ");
        operation = "Initial                     " + "none";
        System.out.println(operation);
        System.out.println("--------------------------------------------------------------------");
        for (i = 1; i < 9; i++) {

            String testString1 = Q_1;
            String testString2 = "" + Qarr[8];

            determineOperation(testString2, testString1);
            System.out.println("--------------------------------------------------------------------");

            if (i == 8) {
            System.out.println("Product: (" + array2String(Aarr) + " "
                        + array2String(Qarr) + ") = " + (multiplier * multiplicand));
            }


        }
        }
 else System.out.println("Error: Please input 8-bit compatible numbers");
    }
}


