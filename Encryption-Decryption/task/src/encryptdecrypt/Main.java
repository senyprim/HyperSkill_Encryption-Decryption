package encryptdecrypt;
import java.util.Scanner;
public class Main {
    static  String alphabet="abcdefghijklmnopqrstuvwxyz";

    public static String encriptOffset(String text, int offset){
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            char ch=text.charAt(index);
            if (Character.isLetter(ch)){
                ch=(char) ('a'+(ch-'a'+offset)%alphabet.length());
            }
            chars[index]=ch;
        }
        return new String(chars);
    }
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String text=scanner.nextLine();
        int offset=Integer.parseInt(scanner.nextLine());
        System.out.println(encriptOffset(text,offset));
    }
}
