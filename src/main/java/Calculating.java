import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class Calculating {
    static File file;
    static ArrayList<File> arr=new ArrayList<File>();
    static ArrayList<File> unique=new ArrayList<File>();
    public static void main(String[] args) throws IOException {
        Calculating obj = new Calculating();
        Scanner sc = new Scanner(System.in);
        String path = sc.next();
        file = new File(path);
        obj.countJavaFile(file);
        countUniqueFile();
        int count=countBlankLines();
        int count2=countCommentLines();
        int count3=totalLines();
        count3=count3-count2-count;
        System.out.println(arr.size()+"-"+unique.size()+"-"+count+"-"+count2+"-"+count3);

    }

    private static int totalLines() throws IOException {
        int count=0;
        for(File file : unique) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while(s!=null) {
                count++;
                s=br.readLine();
            }
        }
        return count;
    }
    /*eeeee*/
    private static int countCommentLines() throws IOException {
        int count=0;
        for(File file : unique) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while(s!=null) {
                String new_str=s.trim();
                if(new_str.startsWith("//")) count++;
                if(new_str.startsWith("/*")&&new_str.endsWith("*/")) count++;
                else if(new_str.startsWith("/*")) {
                    count++;
                    while(true) {
                        String end = br.readLine();
                        if(end!=null){
                            count++;
                            if(end.trim().endsWith("*/") ) break;
                        }
                    }
                }
                s=br.readLine();
            }
        }
        return count;
    }
    private static int countBlankLines() throws IOException {
        int count=0;
        for(File file : unique) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while(s!=null) {
                 if(s.trim().isEmpty()) {
                      count++;
                 }
                 s=br.readLine();
            }
        }
        return count;
    }

    private static void countUniqueFile() throws IOException {
        outer : for(int i=0;i<arr.size();i++) {
            File file1 = arr.get(i);
            for(int j=0;j<unique.size();j++) {
                File file2 = unique.get(j);
                if(i!=j && FileUtils.contentEquals(file1,file2)) continue outer;
            }
            unique.add(file1);
        }
    }

    private void countJavaFile(File file) {
        if(!file.isDirectory()){
            String name=file.getName();
            if(name.contains(".java")) arr.add(file);

        }else{
            File[] files = file.listFiles();
            int count=0;
            for(File f : files) {
                String name=f.getName();
                File new_file = new File(file,name);
                countJavaFile(new_file);
            }
        }
    }

}
