package ru.netology.testmode.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login].button").click();

        String message = "  Личный кабинет";
        $(".heading").shouldHave(text(message));
        Assertions.assertEquals(message, $(".heading").getText());

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        $("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login].button").click();

        String errorMessage = "Ошибка! Неверно указан логин или пароль";
        $("[data-test-id=error-notification].notification_status_error .notification__content").shouldHave(text(errorMessage));
        Assertions.assertEquals(errorMessage, $("[data-test-id=error-notification].notification_status_error .notification__content").getText());

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");

        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login].button").click();

        String errorMessage = "Ошибка! Пользователь заблокирован";
        $("[data-test-id=error-notification].notification_status_error .notification__content").shouldHave(text(errorMessage));
        Assertions.assertEquals(errorMessage, $("[data-test-id=error-notification].notification_status_error .notification__content").getText());

    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $("[data-test-id=login] input").setValue(wrongLogin);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login].button").click();

        String errorMessage = "Ошибка! Неверно указан логин или пароль";
        $("[data-test-id=error-notification].notification_status_error .notification__content").shouldHave(text(errorMessage));
        Assertions.assertEquals(errorMessage, $("[data-test-id=error-notification].notification_status_error .notification__content").getText());

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPassword);
        $("[data-test-id=action-login].button").click();

        String errorMessage = "Ошибка! Неверно указан логин или пароль";
        $("[data-test-id=error-notification].notification_status_error .notification__content").shouldHave(text(errorMessage));
        Assertions.assertEquals(errorMessage, $("[data-test-id=error-notification].notification_status_error .notification__content").getText());

    }
}
