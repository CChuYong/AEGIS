package chuyong.aegis.impl.device;

public interface ToggleableDevice {
    void turnOn();
    void turnOff();
    ToggleState getState();
    ToggleState toggle();
}
