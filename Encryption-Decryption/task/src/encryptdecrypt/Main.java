package encryptdecrypt;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.ECField;
import java.util.*;

interface Alg{
    String encode(String text,int offset);
    String decode(String text,int offset);
}

class AlgShift implements  Alg{
    static  String alphabetLow="abcdefghijklmnopqrstuvwxyz";
    @Override
    public String encode(String text, int offset) {
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            char ch=text.charAt(index);
            if (Character.isLetter(ch)){
                ch=Character.isLowerCase(ch)
                        ?(char)('a'+(ch-'a'+offset)%26)
                        :(char)('A'+(ch-'A'+offset)%26);
            }
            chars[index]=(char)ch;
        }
        return new String(chars);
    }

    @Override
    public String decode(String text, int offset) {
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            char ch=text.charAt(index);
            if (Character.isLetter(ch)){
                ch=Character.isLowerCase(ch)
                        ?(char)('a'+(ch-'a'+26-offset%26)%26)
                        :(char)('A'+(ch-'A'+26-offset%26)%26);
            }
            chars[index]=(char)ch;
        }
        return new String(chars);
    }
}

class AlgUnicod implements Alg{

    @Override
    public String encode(String text, int offset) {
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            chars[index]=(char)(text.charAt(index)+offset);
        }
        return new String(chars);
    }

    @Override
    public String decode(String text, int offset) {
        char[] chars=new char[text.length()];

        for(int index=0;index<text.length();index++)
        {
            chars[index]=(char)(text.charAt(index)-offset);
        }
        return new String(chars);
    }
}

abstract class AbstractFabrica{
    abstract public int getOffset();
    abstract public String getInputString();
    abstract public void outputString(String string);
    abstract public String getMode();
    abstract public String getAlg();
    abstract public void execute();
    abstract public Alg createAlg();
}
class CommandShell extends AbstractFabrica{
    private String inputString="";
    private int offset=0;
    private Alg alg;
    final static Map<String,String> DEFAULT=Map.of("-mode","enc","-key","0","-data","","-in","","-out","","-alg","shift");
    final private List<String> args;
    public CommandShell(String[] args){
        this.args=Arrays.asList(args);
    }
    private String getArg(String arg){
        String result=DEFAULT.get(arg);
        int index=this.args.indexOf(arg);
        if (index==-1 || index==args.size()-1) return result;
        result=args.get(index+1);
        return result;
    }

    @Override
    public int getOffset() {
        int result=0;
        try{
            result=Integer.parseInt(getArg("-key"));
        }
        catch (Exception e){
        }
        return result;
    }

    @Override
    public String getInputString() {
        if (args.contains("-data")){
            return getArg("-data");
        }
        else if(args.contains("-in")){
            try {
                return new String(Files.readAllBytes(Paths.get(getArg("-in"))));
            }
            catch (Exception e){
                System.out.println("Error");
            }
        }
        return DEFAULT.get("-data");
    }

    @Override
    public void outputString(String string) {
        if (args.contains("-out")){
            try(FileWriter writer=new FileWriter(getArg("-out"))){
                writer.write(string);
            }
            catch (Exception e){
                System.out.println("Error");
            }
        }
        else{
        System.out.println(string);
        }
    }

    @Override
    public String getMode() {
        return getArg("-mode");
    }

    @Override
    public String getAlg() {
        return getArg("-alg");
    }

    @Override
    public void execute() {
        Alg alg=createAlg();
        outputString("enc".equals(getMode())
                ?alg.encode(getInputString(),getOffset())
                :alg.decode(getInputString(),getOffset())
        );

    }

    @Override
    public Alg createAlg() {
        if ("shift".equals(getAlg())){
            return new AlgShift();
        }
        if ("unicode".equals(getAlg())){
            return new AlgUnicod();
        }
        return null;
    }
}

public class Main {

    public static void main(String[] args) {
        //args=new String[]{"-mode","enc", "-in","road_to_treasure.txt","-out","protected.txt","-key","5","-alg","unicode"};
        CommandShell shell=new CommandShell(args);
        shell.execute();
    }
}
