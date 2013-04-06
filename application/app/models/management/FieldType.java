package models.management;

import java.util.HashMap;
import java.util.Map;

public enum FieldType {
    TEXT,
    CHECKBOX;
    
    private static final Map<Class, FieldType> TYPES = new HashMap<Class, FieldType>();
    static {
        TYPES.put(boolean.class, CHECKBOX);
    }
    
    public static FieldType getType(Class cls) {
        FieldType type = TYPES.get(cls);
        if(type == null) type = TEXT;
        return type;
    }
}
