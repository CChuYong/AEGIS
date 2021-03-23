package chuyong.aegis.util.debug;

import chuyong.aegis.user.AEGISUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum DebugType {
    PACKET_CAPTURE, COMMAND_CAPTURE;
    private List<AEGISUser> users = new ArrayList<>();
    public void sendMessage(String message){
        users.forEach(x -> x.sendMessage(message));
    }
}
