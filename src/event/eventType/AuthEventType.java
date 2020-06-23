package event.eventType;
import bitzero.server.core.IBZEventType;

public enum AuthEventType implements IBZEventType {
    LOGIN_SUCCESS;
    private AuthEventType() {
    }
}
