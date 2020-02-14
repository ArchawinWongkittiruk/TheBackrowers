package GUI;

import datastructures.*;
import dataprocessors.*;
import parser.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.List;
import java.util.TreeMap;

/**
 * The InputController class allows the user to input the various numbers needed to wrangle the data,
 * along with choosing the reference node passed from the parsed input file from a drop down menu.
 */
public class InputController {
    @FXML private Slider rotationAngleSlider;
    @FXML private TextField rotationAngleField;
    @FXML private ChoiceBox<String> referenceNodeChoiceBox;
    @FXML private TextField scaleFactorX;
    @FXML private TextField scaleFactorY;
    @FXML private TextField finalPositionX;
    @FXML private TextField finalPositionY;
    private TreeMap<String,Node> nodes;

    @FXML
    public void initialize() {}

    /**
     * Passes the value in the rotation angle slider to the rotation angle field.
     * This is called when the slider is dragged or clicked.
     */
    @FXML
    private void sliderToField() {
        rotationAngleField.setText(String.valueOf(new DecimalFormat("#").format(rotationAngleSlider.getValue())));
    }

    /**
     * Passes the value in the rotation angle field to the rotation angle slider, provided it is a number.
     * This is called when the text in the field changes.
     */
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

    /**
     * Takes the keyboard and cursor focus to the next logical text field when the user presses enter.
     * @param event - the source of which is the textField from which the enter key has been pressed
     */
    @FXML
    private void nextField(ActionEvent event) {
        if (event.getSource() == rotationAngleField) {
            scaleFactorX.requestFocus();
        } else if (event.getSource() == scaleFactorX) {
            scaleFactorY.requestFocus();
        } else if (event.getSource() == scaleFactorY) {
            finalPositionX.requestFocus();
        } else if (event.getSource() == finalPositionX) {
            finalPositionY.requestFocus();
        }
    }

    /**
     * Sets the nodes in the reference node choice box according to the parsed file.
     * @param path - the path as set in the LoadController
     */
    void setNodes(Path path) {
        Parser parser = new Parser(path);
        try {
            referenceNodeChoiceBox.getItems().clear();
            nodes = parser.createNodes(parser.getLines());
            referenceNodeChoiceBox.getItems().addAll(nodes.keySet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the input is valid
     * @return the validity of the input for ability to wrangle the data from its values
     */
    boolean inputIsValid() {
        try {
            Float.parseFloat(rotationAngleField.getText());
            Float.parseFloat(scaleFactorX.getText());
            Float.parseFloat(scaleFactorY.getText());
            Float.parseFloat(finalPositionX.getText());
            Float.parseFloat(finalPositionY.getText());
            nodes.get(referenceNodeChoiceBox.getValue());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the output of the wrangled and formatted data changed with the appropriate inputs.
     * @param path - the path as set in the LoadController
     * @return a list of strings being the wrangled data having run through the correct file format creator
     */
    List<String> getOutput(Path path) {
        try {
            Wrangler wrangler = new Wrangler(nodes);
            FileCreator fileCreator = new FileCreator(wrangler.runTransformations(
                    Float.parseFloat(rotationAngleField.getText()),
                    Float.parseFloat(scaleFactorX.getText()),
                    Float.parseFloat(scaleFactorY.getText()),
                    Float.parseFloat(finalPositionX.getText()),
                    Float.parseFloat(finalPositionY.getText()),
                    nodes.get(referenceNodeChoiceBox.getValue())
            ), path);
            return fileCreator.processOutputFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
