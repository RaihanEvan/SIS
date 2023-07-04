module SIS {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	requires javafx.graphics;
	
	opens sis_application to javafx.graphics, javafx.fxml,javafx.base;
}
