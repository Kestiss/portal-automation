package pages.modules

import geb.Module

class LoginModalModule extends Module {

    static content = {
        container(wait: 3, required: false) { $("[class*='Modal_modal-content']") }
        loginButton(waitCondition: { it.displayed }) { container.$("button", text: "Login & Watch Ad") }
        buyCreditsButton(waitCondition: { it.displayed }) { container.$("button", text: "Buy Credits") }
    }

    boolean isPresent() {
        container.displayed
    }
}
