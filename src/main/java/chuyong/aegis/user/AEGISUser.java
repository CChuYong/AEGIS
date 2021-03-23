package chuyong.aegis.user;

import chuyong.aegis.impl.MessageableUser;
import chuyong.aegis.impl.PermissionizedUser;

import java.util.ArrayList;
import java.util.List;

public abstract class AEGISUser implements PermissionizedUser, MessageableUser {
    private List<String> permissions;
    public AEGISUser(){
        permissions = new ArrayList<>();
    }
    public abstract String getName();
    public boolean hasPermission(String perm){
        return permissions.contains(perm);
    }
    @Override
    public void addPermission(String permission) {
        permissions.add(permission);
    }
    public abstract void sendMessage(String message);
}
