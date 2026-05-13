package io.github.enerccio.llllm.loc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocalizationEN extends LocalizationBase {
    @Override
    protected void loadMessages() {
        setValue(L.BUTTON_EXIT, "Exit");

        setValue(L.LABEL_USERNAME, "Uživatelské jméno");
        setValue(L.LABEL_USER_FULLNAME, "Jméno a příjmení");
        setValue(L.LABEL_EMAIL, "Email");
        setValue(L.LABEL_LOGIN, "Login");
        setValue(L.LABEL_LOGOUT, "Logout");
        setValue(L.LABEL_EXIT_APPLICATION, "Close application");
        setValue(L.LABEL_PASSWORD, "Password");
        setValue(L.LABEL_PASSWORD_AGAIN, "Repeat password");
        setValue(L.LABEL_FORGOT_PASSWORD, "Forgotten password?");
        setValue(L.LABEL_SAVE_LOGIN, "Save login");
        setValue(L.LABEL_AUTHENTICATION_FAILED, "Failed to authenticate");
        setValue(L.LABEL_APPLICATION_ERROR, "Application Error");
        setValue(L.INTERNAL_SERVER_ERROR, "Internal Server Error");
        setValue(L.MSG_VALIDATION_FAILED_CANNONT_SAVE, "Cannot save form.");
        setValue(L.MSG_VALIDATION_FAILED_CANNONT_SAVE_EXT, "Cannot save form. Invalid fields: ");
        setValue(L.BUTTON_LOGIN, "Login");
        setValue(L.MSG_AUTHENTICATION_FAILED_INFO, "Invalid credentials.");
        setValue(L.AI_TYPE_OPEN_AI_COMPATIBLE, "OpenAI compatible");
        setValue(L.AITABLE_COLUMN_NAME, "Name");
        setValue(L.AITABLE_COLUMN_TYPE, "Type");
        setValue(L.WORKSPACE_TABS_CHAT, "Chats");
        setValue(L.WORKSPACE_TABS_CHARACTERS, "Characters");
        setValue(L.WORKSPACE_TABS_LOREBOOK, "Lorebook");
        setValue(L.WORKSPACE_TABS_PERSONA, "Persona");
        setValue(L.WORKSPACE_TABS_AI, "AI Providers");
    }

    @Override
    public Locale getLocale() {
        return Locale.US;
    }

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat( "MM.dd.yyyy");
    }

    @Override
    public DateFormat getHourFormat() {
        return new SimpleDateFormat( "HH:mm:ss");
    }

    @Override
    public DateFormat getDateHourFormat() {
        return new SimpleDateFormat( "MM.dd.yyyy HH:mm:ss");
    }
}
