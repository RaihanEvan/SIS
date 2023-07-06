module SIS {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.base;
	
	opens sis_application to javafx.graphics, javafx.fxml,javafx.base;
}
