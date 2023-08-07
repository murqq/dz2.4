import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferAmountToCardTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var user = DataHelper.getRegisteredUser();
        var verificationPage = loginPage.validLogin(user);
        var verificationCode = DataHelper.getVerificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferValidAmountFromSecondCardToFirstCard() {

        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amountTransfer = DataHelper.generateValidAmount(secondCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + amountTransfer;
        var expectedBalanceSecondCard = secondCardBalance - amountTransfer;
        var transferPage = dashboardPage.selectCardTotransfer(firstCard);
        dashboardPage = transferPage.validTransfer(String.valueOf(amountTransfer), secondCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);

    }

    @Test
    void transferInvalidAmountFromFirstCardToSecondCard() {

        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amountTransfer = DataHelper.generateInvalidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amountTransfer;
        var expectedBalanceSecondCard = secondCardBalance + amountTransfer;
        var transferPage = dashboardPage.selectCardTotransfer(secondCard);
        transferPage.makeTransfer(String.valueOf(amountTransfer), firstCard);
        transferPage.findError("Сумма перевода превышает баланс карты для списания");
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
}
