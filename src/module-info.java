module SIS {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	opens sis_application to javafx.graphics, javafx.fxml,javafx.base;
}
