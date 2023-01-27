package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTestTask2 {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeSuccesfullyCompleted() {
        String city = "Рязань";
        int dayToAdd = 7;
        int defaultDays = 3;
        $("[data-test-id='city'] input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id='data'] input").click();
        if (!generateDate(defaultDays, "MM").equals(generateDate(dayToAdd, "MM"))) {
            $("[data-step='1']").click();
        }

        $$(".calendar__day").findBy(text(generateDate(dayToAdd, "d"))).click();
        $("[data-test-id='name'] input").setValue("Иванов-Иваныч Иван");
        $("[data-test-id='phone'] input").setValue("+79998887766");
        $("[data-test-id ='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(com.codeborne.selenide.Condition.exactText("Встреча успешно забронирована на "
                        + generateDate(dayToAdd, "dd.MM.yyyy")));
    }
}
