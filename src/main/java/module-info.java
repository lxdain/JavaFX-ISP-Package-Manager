module com.lxdain.fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;

    opens com.lxdain.fx to javafx.fxml, org.hibernate.orm.core;

    exports com.lxdain.fx;
}