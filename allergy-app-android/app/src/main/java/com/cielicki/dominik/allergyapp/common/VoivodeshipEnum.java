package com.cielicki.dominik.allergyapp.common;

import android.graphics.Color;

import com.cielicki.dominik.allergyapprestapi.db.Voivodeship;

/**
 * Enum przedstawiający województwa i ich kolory na mapie.
 */
public enum VoivodeshipEnum {
    LOKALIZACJA("Lokalizacja", -1, new Voivodeship(-1L)),
    DOLNOSLASKIE("Dolnośląskie", Color.rgb(1, 178, 255), new Voivodeship(1L)),
    KUJAWSKO_POMORSKIE("Kujawsko-Pomorskie", Color.rgb(254, 232, 0), new Voivodeship(2L)),
    LUBELSKIE("Lubelskie", Color.rgb(0, 255, 179), new Voivodeship(3L)),
    LUBUSKIE("Lubuskie", Color.rgb(139, 255, 0), new Voivodeship(4L)),
    LODZKIE("Łódzkie", Color.rgb(255, 0, 146), new Voivodeship(5L)),
    MALOPOLSKIE("Małopolskie", Color.rgb(0, 107, 255), new Voivodeship(6L)),
    MAZOWIECKIE("Mazowieckie", Color.rgb(210, 0, 255), new Voivodeship(7L)),
    OPOLSKIE("Opolskie", Color.rgb(255, 85, 0), new Voivodeship(8L)),
    PODKARPACKIE("Podkarpackie", Color.rgb(116, 0, 255), new Voivodeship(9L)),
    PODLASKIE("Podlaskie", Color.rgb(23, 0, 255), new Voivodeship(10L)),
    POMORSKIE("Pomorskie", Color.rgb(0, 255, 1), new Voivodeship(11L)),
    SLASKIE("Śląskie", Color.rgb(1, 255, 139), new Voivodeship(12L)),
    SWIETOKRZYSKIE("Świętokrzyskie", Color.rgb(0, 0, 0), new Voivodeship(13L)),
    WARMINSKO_MAZURSKIE("Warmińsko-Mazurskie", Color.rgb(0, 240, 255), new Voivodeship(14L)),
    WIELKOPOLSKIE("Wielkopolskie", Color.rgb(255, 148, 0), new Voivodeship(15L)),
    ZACHODNIOPOMORSKIE("Zachodniopomorskie", Color.rgb(254, 0, 0), new Voivodeship(16L));

    private String voivodeshipName;
    private int color;
    public Voivodeship voivodeship;

    VoivodeshipEnum(String voivodeshipName, int color, Voivodeship voivodeship) {
        this.voivodeshipName = voivodeshipName;
        this.color = color;
        this.voivodeship = voivodeship;
        this.voivodeship.setName(voivodeshipName);
    }

    /**
     * Zwraca województwo na podstawie koloru.
     *
     * @param colorInt kolor.
     * @return Zwraca województwo na podstawie koloru.
     */
    public static VoivodeshipEnum getVoivodeshipFromColor(int colorInt) {
        for (VoivodeshipEnum voivodeshipEnum : VoivodeshipEnum.values()) {
            if (voivodeshipEnum.color == colorInt) {
                return voivodeshipEnum;
            }
        }

        return null;
    }

    public static VoivodeshipEnum getVoivodeshipById(Long id) {
        for (VoivodeshipEnum voivodeshipEnum : VoivodeshipEnum.values()) {
            if (voivodeshipEnum.voivodeship.getId().equals(id)) {
                return voivodeshipEnum;
            }
        }

        return null;
    }

    public static VoivodeshipEnum getVoivodeshipByName(String voivodeshipName) {
        for (VoivodeshipEnum voivodeshipEnum : VoivodeshipEnum.values()) {
            if (voivodeshipEnum.voivodeshipName.equals(voivodeshipName)) {
                return voivodeshipEnum;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return this.voivodeshipName;
    }
}
