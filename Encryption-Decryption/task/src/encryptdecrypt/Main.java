package encryptdecrypt;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    static  String alphabet="abcdefghijklmnopqrstuvwxyz";


    public static String encriptOffset(String text, int offset){
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            chars[index]=(char)(text.charAt(index)+offset);
        }
        return new String(chars);
    }
    public static String decriptOffset(String text, int offset){
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            chars[index]=(char)(text.charAt(index)-offset);
        }
        return new String(chars);
    }
    public static String doOffset(String command,String text,int offset){
        if (command.equals("enc")){
            return  encriptOffset(text,offset);
        }
        else if (command.equals("dec")){
            return  decriptOffset(text,offset);
        }
        else {
            throw new IllegalArgumentException("Неверная комманда");
        }
    }
    public static String getMode(String[] args){
        int index=Arrays.asList(args).indexOf("-mode");
        if (index==-1 || index==args.length-1) return "enc";
        String mode=args[index+1];
        if (mode.startsWith("-")) return "enc";
        return mode;
    }
    public static String getData(String[] args){
        int index=Arrays.asList(args).indexOf("-data");
        if (index==-1 || index==args.length-1) return "";
        String data=args[index+1];
        if (data.startsWith("-")) return "";
        return data;
    }
    public static int getOffset(String[] args){
        int key=0;
        int index=Arrays.asList(args).indexOf("-key");
        if (index==-1 || index==args.length-1) return key;
        try{
             key=Integer.parseInt(args[index+1]);
        }
        catch (Exception e){
        }
        return key;
    }

    public static void main(String[] args) {
        System.out.println(doOffset(getMode(args),getData(args),getOffset(args)));
    }
}
