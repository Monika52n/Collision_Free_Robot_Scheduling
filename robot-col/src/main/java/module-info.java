module com.task.robotcol {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.task.robotcol to javafx.fxml;
    exports com.task.robotcol;
}