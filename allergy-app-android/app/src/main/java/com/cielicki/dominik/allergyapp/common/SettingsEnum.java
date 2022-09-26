package com.cielicki.dominik.allergyapp.common;

import com.cielicki.dominik.allergyapprestapi.db.Setting;
import com.cielicki.dominik.allergyapprestapi.db.UserSettings;

public enum SettingsEnum {
    START_LOCATION(1L),
    GENERAL_CHAT(2L);

    private Setting setting;

    private SettingsEnum(Long id) {
        setting = new Setting();
        setting.setId(id);
    }

    public Setting getSetting() {
        return setting;
    }

    public static SettingsEnum getSetting(UserSettings userSettings) {
        for (SettingsEnum settingsEnum: values()) {
            if (settingsEnum.setting.getId() == userSettings.getId().getSetting().getId()) {
                return settingsEnum;
            }
        }

        return null;
    }
}
