package encryptdecrypt;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.ECField;
import java.util.*;

public class Main {
    static  String alphabet="abcdefghijklmnopqrstuvwxyz";
    static Map<String,String> defaultArgs=Map.of("-mode","enc","-key","0","-data","");


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


    public static String getArg(List<String> args,String arg){
        String result=defaultArgs.get(arg);
        int index=args.indexOf(arg);
        if (index==-1 || index==args.size()-1) return result;
        result=args.get(index+1);
        return result;
    }

    public static String inputData(List<String> args){
        if (args.contains("-data")){
            return getArg(args,"-data");
        }
        else if(args.contains("-in")){
             try {
                return new String(Files.readAllBytes(Paths.get(getArg(args, "-in"))));
            }
            catch (Exception e){
                System.out.println("Error");
            }
        }
        return defaultArgs.get("-data");
    }

    public static void outData(List<String> args,String data){
        if (args.contains("-out")){
            try(FileWriter writer=new FileWriter(getArg(args,"-out"))){
                writer.write(data);
            }
            catch (Exception e){
                System.out.println("Error");
            }
        }
        System.out.println(data);
    }


    public static void main(String[] args) {
        //args=new String[]{"-mode","enc", "-in","road_to_treasure.txt","-out","protected.txt","-key","5"};
        List<String> list= Arrays.asList(args);
        String inData=inputData(list);
        String command=getArg(list,"-mode");
        int offset=Integer.parseInt(getArg(list,"-key"));
        String outData=doOffset(command,inData,offset);
        outData(list,outData);
    }
}
