package data;

import lombok.Value;

import java.util.Random;

public class DataHelper {

    static Random random = new Random();

    private DataHelper() {
    }


    public static RegisteredUser getRegisteredUser() {
        return new RegisteredUser("vasya", "qwerty123");
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static InfoCard getFirstCard() {
        InfoCard InfoCard = new InfoCard("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
        return InfoCard;
    }

    public static InfoCard getSecondCard() {
        InfoCard InfoCard = new InfoCard("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
        return InfoCard;
    }

    public static int generateValidAmount(int balance) {
        int validBalance = random.nextInt(balance) + 1;
        return validBalance;
    }

    public static int generateInvalidAmount(int balance) {
        int invalidBalance = balance + (10_000 + random.nextInt(66_666));
        return invalidBalance;
    }


    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class InfoCard {
        String numberCard;
        String testId;
    }

    @Value
    public static class RegisteredUser {
        String login;
        String password;

    }
}
