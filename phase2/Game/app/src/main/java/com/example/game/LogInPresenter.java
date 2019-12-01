package com.example.game;

class LogInPresenter {
    private LogInView logInView;
    private LogIn logIn;

    /**
     * Constructs an instance of LogInPresenter.
     *
     * @param logInView - the view/activity that interacts with the user
     * @param logIn - an instance of LogIn
     */
    LogInPresenter(LogInView logInView, LogIn logIn) {
        this.logInView = logInView;
        this.logIn = logIn;
    }

    /**
     * Tries to log the user into the app: raises messages if this is not possible that informs the
     * user why that is the case. For example the user may have left empty fields.
     *
     * @param username - the username the user tried to loginUser with
     * @param password - the password the user tried to loginUser with
     */
    void validateCredentials(String username, String password) {
        if (username.equals("") || password.equals("")) {
            logInView.raiseToast("Fields cannot be empty!");
        } else {
            User user = logIn.loginUser(username, logInView.getApplicationContext());
            if (user != null) {
                if (user.authenticateUser(username, password)) {
                    logInView.jumpToActivity(user.getName(), Classes.gameSelection);
                } else {
                    logInView.raiseToast("Incorrect credentials!");
                }
            } else {
                logInView.raiseNoRegisteredUserAlert(username);
            }
        }
    }
}
