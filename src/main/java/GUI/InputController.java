package GUI;

import ALG.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class InputController {
    @FXML private Slider rotationAngleSlider;
    @FXML private TextField rotationAngleField;
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField finalPositionX;
    @FXML private TextField finalPositionY;
    private Parser parser;

    @FXML
    public void initialize() {}

    @FXML
    private void sliderToField() {
        rotationAngleField.setText(String.valueOf(
                new DecimalFormat("#").format(rotationAngleSlider.getValue())));
    }

    @FXML
    private void fieldToSlider() {
        boolean isNumber;
        double value = 0;
        try {
            value = Double.parseDouble(rotationAngleField.getText());
            isNumber = true;
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        if (isNumber) rotationAngleSlider.setValue(value);
    }

    void setNodes(String path) {
        parser = new Parser(Paths.get(path));
        try {
            referenceNodeChoiceBox.getItems().clear();
            referenceNodeChoiceBox.getItems().addAll(parser.createNodes(parser.getLines()).keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void inputToOutput(OutputController outputController) {
        outputController.setOutputText(parser);
    }

    @FXML
    private void showNodes() {
        referenceNodeChoiceBox.show();
    }
}
