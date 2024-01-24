package com.project;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainViewController {
    @FXML
    private void switchToCadAcc() throws IOException {
        App.setRoot("CadAcc");
    }

    @FXML
    private void switchToRemAcc() throws IOException {
        App.setRoot("RemAcc");
    }

    @FXML
    private void switchToMerAcc() throws IOException {
        App.setRoot("MerAcc");
    }

    @FXML
    private void switchToExtAcc() throws IOException {
        App.setRoot("ExtAcc");
    }

    @FXML
    private void switchToAddTr() throws IOException {
        App.setRoot("AddTr");
    }

    @FXML
    private void switchToEditLastTr() throws IOException {
        App.setRoot("EditLastTr");
    }

    @FXML
    private void switchToTransferFunds() throws IOException {
        App.setRoot("TransferFunds");
    }

    @FXML
    private void switchToResAccs() throws IOException {
        App.setRoot("ResAccs");
    }

    @FXML
    private void switchToResLastMonth() throws IOException {
        App.setRoot("ResLastMonth");
    }

    @FXML
    private void switchToBal6Months() throws IOException {
        App.setRoot("Bal6Months");
    }

    @FXML
    private void switchToRenPerYear() throws IOException {
        App.setRoot("RenPerYear");
    }
}
