package core.utils;

import java.util.Collection;
import java.util.Set;

public class TagUtil {


    private static final String MODULE_PREFIX = "@module_";

    public static String extractModuleName(Collection<String> tagNames) {
        if (tagNames == null) return "UnknownModule";
        for (String tag : tagNames) {
            if (tag != null && tag.startsWith(MODULE_PREFIX)) {
                return tag.substring(MODULE_PREFIX.length());
            }
        }
        return "UnknownModule";
    }


       /* public static String extractModuleName(Set<String> tags) {
            return tags.stream()
                    .filter(tag -> tag.matches("@[A-Za-z0-9_]+"))
                    .map(tag -> tag.replace("@", ""))
                    .findFirst()
                    .orElse("UnknownModule");
        }*/
}
