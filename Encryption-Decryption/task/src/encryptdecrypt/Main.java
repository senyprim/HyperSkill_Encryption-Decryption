package encryptdecrypt;
import java.util.Scanner;
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
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);

        System.out.println(doOffset(scanner.nextLine(),scanner.nextLine(),scanner.nextInt()));

    }
}
