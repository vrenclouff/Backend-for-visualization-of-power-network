package cz.zcu.kiv.vps.utils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Lukas Cerny.
 */
public class EnumUtils {

    private static final Logger logger = LogManager.getLogger(EnumUtils.class);

    /**
     * Methot converts enum to string represents JSON data.
     * @param enumObject
     * @return JSON
     */
    public static String toJSON(final Class<? extends Enum> enumObject) {
        logger.debug("Converting enum: '"+enumObject+"' to JSON.");
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ArrayNode data = new ArrayNode(factory);
        for(Field item : enumObject.getFields()) {
            try {
                Enum instance = (Enum) item.get(item.getType());
                List<Field> attributes = Stream.of(instance.getClass().getDeclaredFields())
                        .filter(e -> e.getType() != instance.getClass())
                        .collect(Collectors.toList());

                ObjectNode row = new ObjectNode(factory);
                row.put("name", item.getName());
                for (Field attribute : attributes) {
                    if (!attribute.getName().equalsIgnoreCase("$VALUES")) {
                        attribute.setAccessible(true);
                        row.put(attribute.getName(), String.valueOf(attribute.get(instance)));
                    }
                }
                data.add(row);
            } catch (IllegalAccessException e) {
                logger.error("Cannot get value from instance.", e);
            }
        }
        return data.toString();
    }
}
