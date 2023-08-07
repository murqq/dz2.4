package page;

import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {


    public VerificationPage validLogin(DataHelper.RegisteredUser user) {
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

}
