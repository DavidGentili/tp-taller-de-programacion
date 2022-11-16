package vista.interfaces;

import java.awt.event.ActionListener;

public interface IVLogin {
    public void setActionListenerLogin(ActionListener a);
    public String getUserName();
    public String getPassword();

    public void setMesssage(String message);
}
