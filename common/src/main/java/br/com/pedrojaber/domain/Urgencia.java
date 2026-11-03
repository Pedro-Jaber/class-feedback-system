package br.com.pedrojaber.domain;

public enum Urgencia {
    BAIXA,
    MEDIA,
    ALTA;

    public static Urgencia getUrgencia(int nota) {
        if (nota >= 0 && nota <= 3)
            return Urgencia.ALTA;
        if (nota > 3 && nota <= 6)
            return Urgencia.MEDIA;
        return Urgencia.BAIXA;
    }

}
