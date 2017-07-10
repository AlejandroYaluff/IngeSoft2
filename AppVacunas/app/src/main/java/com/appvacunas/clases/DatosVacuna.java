package com.appvacunas.clases;

import android.provider.BaseColumns;

/**
 * Datos de la tabla Vacunas
 */

// Construccion de la tabla Vacunas
public class DatosVacuna {
    public static abstract class VacunaTable implements BaseColumns {
        public static final String TABLE_NAME ="Vacunas";

        public static final String ID = "id_vacuna";
        public static final String NOMBRE = "nombre";
        public static final String DOSIS = "dosis";
        public static final String EDAD = "edad";
        public static final String FECHA = "fecha";
        public static final String LOTE = "lote";
        public static final String NOMBRE_MEDICO = "nombre_medico";
        public static final String DESCRIPCION = "descripcion";
        public static final String ID_HIJO = "id_hijo";
        public static final String APLICADA = "aplicada";
    }
}
