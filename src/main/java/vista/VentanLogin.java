package vista;

import controlador.Commands;
import vista.interfaces.IVLogin;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VentanLogin extends JFrame implements IVLogin {

    private JPanel mainPanel;
    private JTextField fieldUserName;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    private JLabel labelUserName;
    private JLabel labelPassword;
    private JLabel labelMessage;

    public VentanLogin(){
        setTitle("Empresa - Login");
        setContentPane(this.mainPanel);
        this.pack();
        this.setSize( 512,512);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.buttonLogin.setActionCommand(Commands.LOGIN);
    }

    @Override
    public void setActionListenerLogin(ActionListener a) {
        this.buttonLogin.addActionListener(a);
    }

    @Override
    public String getUserName() {
        return this.fieldUserName.getText();
    }

    @Override
    public String getPassword() {
        char[] pass = this.fieldPassword.getPassword();
        return new String(pass);
    }

    @Override
    public void setMesssage(String message){
        this.labelMessage.setText(message);
    }
}
