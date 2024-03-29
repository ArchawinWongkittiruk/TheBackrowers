package GUI;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
 * The OutputController class displays the output window of the GUI where the user
 * can preview the .txt file and make any changes needed before saving.
 */
public class OutputController {
    @FXML private Button saveButton = new Button();
    @FXML private Label fileSaved = new Label();
    @FXML private TextArea outputText = new TextArea(); //Text preview area.
    private String inputFileName;
    private String uneditedOutputString;

    @FXML
    public void initialize() {}

    /**
     * Sets the File name.
     * @param name File name.
     */
    void setInputFileName(String name) {
        inputFileName = name;
    }

    /**
     * A method for saving the preview page into a .txt file
     * in a directory the user wants.
     */
    @FXML
    private void saveFile() {
        String text = outputText.getText();
        Window stage = saveButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("updated_" + inputFileName);
        FileChooser.ExtensionFilter extFilter;
        if (inputFileName.equals("xml")) {
            extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        } else {
            extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        }
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            saveTextToFile(text, file);
        }
    }

    /**
     * A method for writing the @outputText field into a file
     * which allows the text to be saved as a .txt file in the saveFile()
     * method. It also displays a notification about the file being saved.
     * @param content calculated text values displayed in @outputText field.
     * @param file file to be saved.
     */
    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();

            fileSaved.setText("Text has been saved!");
            //file saved notification disappears after 2 seconds
            PauseTransition visiblePause = new PauseTransition(
                    Duration.seconds(2)
            );
            visiblePause.setOnFinished(
                    event -> fileSaved.setVisible(false)
            );
            visiblePause.play();

        } catch (IOException ex) {
            Logger.getLogger(OutputController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * A method for outputting the @outputText area with the new
     * calculated values.
     * @param outputStringList calculated values to be displayed for the user to preview.
     */
    void setOutputText(List<String> outputStringList) {
        outputText.clear();
        for (String string : outputStringList) {
            outputText.appendText(string + "\n");
        }
        uneditedOutputString = outputText.getText();
    }

    /**
     * A method for clearing the @outputText area where the text preview
     * is displayed.
     */
    @FXML
    private void clearTextField() {
        if (!outputText.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the preview. Are you sure?",
                    ButtonType.YES, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                outputText.clear();
            }
        }
    }

    boolean outputTextHasBeenEdited() {
        return !outputText.getText().equals(uneditedOutputString);
    }
}