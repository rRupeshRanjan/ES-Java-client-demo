package com.javaDemos.esJavaClientWebfluxDemo.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String title;
    private Status status;
    private long createdAt;
    private long dueBy;

    public static Map<String, Object> getTaskMap(Task task, boolean removeNulls) {
        Map<String, Object> map = objectMapper.convertValue(task, Map.class);
        if (removeNulls) {
            map = map.entrySet()
                    .stream()
                    .filter(p -> {
                        if (p.getValue() == null)
                            return false;
                        else if (p.getValue() instanceof Long)
                            return (Long) p.getValue() != 0;
                        else
                            return true;
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        return map;
    }

    public static Task getTaskFromString(String s) {
        try {
            return objectMapper.readValue(s, Task.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
