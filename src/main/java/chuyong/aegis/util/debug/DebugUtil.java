package chuyong.aegis.util.debug;

import chuyong.aegis.user.AEGISUser;

public class DebugUtil {
    public static void addDebugUser(AEGISUser user, DebugType type){
        type.getUsers().add(user);
    }
    public static boolean removeDebugUser(AEGISUser user, DebugType type){
        return type.getUsers().remove(user);
    }
}
