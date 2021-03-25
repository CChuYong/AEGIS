package chuyong.aegis.cache;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class AEGISConfig {
    public static int NETTY_PORT = 10050;
    public static String MYSQL_ADDRESS = "192.168.0.34";
    public static String MYSQL_PORT = "3306";
    public static String MYSQL_USERNAME = "root";
    public static String MYSQL_PASSWORD = "thddudals7565#";
    public static String MYSQL_DATABASE = "aegis";
    public static void load(){
        File file = new File("aegis.properties");
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String[] s = sc.nextLine().split("=");
                if(s.length<2)
                    continue;
                switch(s[0]){
                    case "port":
                        NETTY_PORT = Integer.parseInt(s[1]);
                        break;
                    case "mysql_addr":
                        MYSQL_ADDRESS = s[1];
                        break;
                    case "mysql_pswd":
                        MYSQL_PASSWORD = s[1];
                        break;
                    case "mysql_data":
                        MYSQL_DATABASE = s[1];
                        break;
                    case "mysql_user":
                        MYSQL_USERNAME = s[1];
                        break;
                    case "mysql_port":
                        MYSQL_PORT = s[1];
                        break;
                    default:
                        continue;
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
