package helpers;

/**
 * Clase con metodos estaticos, encargada de asistir en las cuestiones relacionadas con los operarios
 */
public class OperarioHelpers {

    /**
     * Retorna si una contraseña cumple con los parametros correspondiente
     * no es cadena nula
     * no es cadena vacia
     * tiene una mayuscula
     * tiene un numero
     * @param password contraseña a chequear
     * @return si la contraseña es valida
     */
    public static boolean correctPassword(String password){
        if(password == null || password.isBlank() || password.isEmpty() || password.length() < 6 || password.length() > 12)
            return false;

        char[] characters = password.toCharArray();
        boolean getNumber = false;
        boolean getUpperCase = false;
        int i = 0;

        while(i < characters.length && (!getNumber || !getUpperCase)){
            if(!getNumber && characters[i] >= '0' && characters[i] <= '9')
                getNumber = true;
            if(!getUpperCase && characters[i] >= 'A' && characters[i]<='Z')
                getUpperCase = true;
            i++;
        }
        return getNumber && getUpperCase;
    }
}
