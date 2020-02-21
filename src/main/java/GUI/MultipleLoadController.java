package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Path;
import java.util.List;


/**
 * Controller class for the MultipleLoad window, where user chooses multiple files.
 */
public class MultipleLoadController {

    @FXML private ListView<String> uploadTable = new ListView<>();
    @FXML private ListView<String> selectedTable = new ListView<>();

    private Path fullPath;

    @FXML
    public void initialize() {}

    /**
     * Returns absolute path of file
     */
    public Path getPath() {
        return fullPath;
    }

    /**
     * Upload multiple files via a file chooser
     */
    @FXML
    void uploadFiles() {
        //initialise fileChooser
        FileChooser fileChooser = new FileChooser();
        //set specific extensions for fileChooser
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //allow multiple selection of files
        List<File> selectFiles = fileChooser.showOpenMultipleDialog(null);

        //add and display the selected files into uploadTable
        if (selectFiles != null) {
            for (int i = 0; i < selectFiles.size(); i++) {
                uploadTable.getItems().add(selectFiles.get(i).getAbsolutePath());
            }
        }
        //allows multiple selection of files in uploadTable [bad coding]
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Drag event. Allow dragging of files into uploadTable
     */
    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
            //testing purposes
            System.out.println("drag works");
        }
    }

    /**
     * Drop event. Allow dropping of files into UploadTable
     */
    @FXML
    private void handleDrop(DragEvent event){
        //receive files when dropped
        List<File> selectedFile = event.getDragboard().getFiles();

        //add and display the selected files into listview
        if (selectedFile != null) {
            for (int i = 0; i < selectedFile.size(); i++) {
                uploadTable.getItems().add(selectedFile.get(i).getAbsolutePath());
            }
        }

        //allows multiple selection of files in uploadTable [bad coding]
        uploadTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //testing purposes
        System.out.println("drop works");
        System.out.println(selectedFile);
    }

    /**
     * Transfer selected files from the uploadTable to selectedTable
     */
    @FXML
    private void selectFiles(){
        //initialise variable
        String choose = "";
        //store selected items into listOfItems
        ObservableList listOfItems = uploadTable.getSelectionModel().getSelectedItems();
        //no idea what this does
        for (Object items : listOfItems){
            choose +=  items;
        }
        //replace selected files from uploadTable to selectedTable
        if(choose != "") {
            selectedTable.getItems().addAll(choose);
            uploadTable.getItems().removeAll(choose);
            System.out.println("file got chosen : " + choose);
            System.out.println("file in original location : " + uploadTable);
        }
        //not working
//        if (!uploadTable.getItems().isEmpty()) {
//            uploadTable.getSelectionModel().select(0);
//        }
    }

    @FXML
    private void unselectFiles(){
        //initialise variable
        String choose = "";
        //store selected items into listOfItems
        ObservableList listOfItems = selectedTable.getSelectionModel().getSelectedItems();
        //no idea what this does
        for (Object items : listOfItems){
            choose +=  items;
        }
        //replace selected files from uploadTable to selectedTable
        if(choose != "") {
            uploadTable.getItems().addAll(choose);
            selectedTable.getItems().removeAll(choose);
            System.out.println("file got chosen : " + choose);
            System.out.println("file in original location : " + selectedTable);
        }
        //not working
//        if (!uploadTable.getItems().isEmpty()) {
//            uploadTable.getSelectionModel().select(0);
//        }
    }
}
