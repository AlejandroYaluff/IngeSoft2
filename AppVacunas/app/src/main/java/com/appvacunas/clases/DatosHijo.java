package com.appvacunas.clases;

import android.provider.BaseColumns;
/**
 * Datos de la tabla hijo
 */

// Construccion de la tabla hijo
public class DatosHijo {
    public static abstract class HijoTable implements BaseColumns{
        public static final String TABLE_NAME ="hijo";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO = "apellido";
        public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
        public static final String LUGAR_NACIMIENTO = "lugar_nacimiento";
        public static final String SEXO= "sexo";
        public static final String NACIONALIDAD = "nacionalidad";
        public static final String DIRECCION = "direccion";
        public static final String DEPARTAMENTO = "departamento";
        public static final String MUNICIPIO = "municipio";
        public static final String BARRIO = "barrio";
        public static final String REFERENCIA_DOMICILIO= "referencia_domicilio";
        public static final String RESPONSABLE = "responsable";
        public static final String TELEFONO_CONTACTO = "telefono_contacto";
        public static final String SEGURO_MEDICO = "seguro_medico";
        public static final String ALERGIAS = "alergias";
        public static final String AVATAR = "avatar";
    }
}
