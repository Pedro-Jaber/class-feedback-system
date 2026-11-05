package br.com.pedrojaber.domain;

public enum Urgencia {
    BAIXA, // 7-10
    MEDIA, // 4-6
    ALTA; // 0-3

    public static Urgencia getUrgencia(int nota) {
        if (nota >= 0 && nota <= 3)
            return Urgencia.ALTA;
        if (nota > 3 && nota <= 6)
            return Urgencia.MEDIA;
        return Urgencia.BAIXA;
    }

}
