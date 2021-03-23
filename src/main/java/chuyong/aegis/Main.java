package chuyong.aegis;

public class Main {
    private static AEGIS aegis;
    public static void main(String[] args){
        aegis = new AEGIS();
        try{
            aegis.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
