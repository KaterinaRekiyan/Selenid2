package ru.netology

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.lang.module.Configuration;
import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.nio.channels.FileChannel.open;

public class CardDeliveryTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DataTimeFormatter.ofPattern));
    }

    @Test
    public void registerDeliveryV1Test() {
        open("http://localhost:9999/");
        String city = "Рязань";
        int dayToAdd = 7;
        int defauitAddedDays = 3;
        $("[data-test-id = 'city] input").setValue(city.substring(0, 2));
        $$(".menu-item__control").findBy(text(city)).click();
        $("[data-test-id = 'data'] input").click();
        if (!generateDate(AddedDays, "MM").equals(generateDate(dayToAdd, "MM"))) {
            $("[data-step = '1']").click();
        }
        $$(".calendar__day").findBy(text(generateDate(dayToAdd, "d"))).click();
        $("[data-test-id = 'name'] input").setValue("Иванов-Иваныч Иван");
        $("[data-test-id = 'phone'] input").setValue("+79998887766");
        $("[data-test-id = 'agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на" + generateDate(dayToAdd, "dd.MM.yyyy")));
    }
}
