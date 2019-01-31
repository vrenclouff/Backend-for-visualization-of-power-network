package cz.zcu.kiv.vps.core.database;

import javax.enterprise.util.AnnotationLiteral;

/**
 * Created by Lukas Cerny.
 *
 * Class is used for dynamic injection entity managers by DatabaseQualifier
 */

public class DatabaseLiteral extends AnnotationLiteral<DatabaseQualifier> implements DatabaseQualifier{

    private String value;
    public DatabaseLiteral(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
