package chuyong.aegis.impl;

public interface PermissionizedUser {
    boolean hasPermission(String permission);
    void addPermission(String permission);
}
