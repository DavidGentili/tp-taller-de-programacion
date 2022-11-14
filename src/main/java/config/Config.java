package config;

public class Config {

    public static String ARCHIVO_CONFIGURACION = "archivo_configuracion.bin";
    public static String ARCHIVO_GESTOR_EMPRESA = "archivo_empresa.bin";

    public static int NUMERO_MAXIMO_DE_MOZOS = 6;
    public static int NUMERO_MAXIMO_DE_MOZOS_ACTIVOS = 4;
    public static int NUMERO_MAXIMO_DE_MOZOS_DE_FRANCO = NUMERO_MAXIMO_DE_MOZOS - NUMERO_MAXIMO_DE_MOZOS_ACTIVOS;


}
