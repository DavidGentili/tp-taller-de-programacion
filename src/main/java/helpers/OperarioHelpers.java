package helpers;

public class OperarioHelpers {

    public static boolean correctPassword(String password){
        if(password == null || password.isBlank() || password.isEmpty() || password.length() < 6 || password.length() > 12)
            return false;

        char[] characters = password.toCharArray();
        boolean getNumber = false, getUpperCase = false;
        int i = 0;
        while(i < characters.length && !getNumber && !getUpperCase){
            if(!getNumber && characters[i] >= '0' && characters[i] <= '9')
                getNumber = true;
            if(!getUpperCase && characters[i] >= 'A' && characters[i]<='Z')
                getUpperCase = true;
            i++;
        }
        return getNumber | getUpperCase;
    }
}
