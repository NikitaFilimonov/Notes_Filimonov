package com.example.notes.note;

import com.google.firebase.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NoteMapping {
    public static class Fields {
        public final static String DATE = "date";
        public final static String NAME = "name";
        public final static String TEXT = "text";
        public final static String LIKE = "like";
    }

    public static Note toNote(String id, Map<String, Object> doc) {
        Timestamp timeStamp = (Timestamp) doc.get(Fields.DATE);
        Note answer = new Note((String) doc.get(Fields.NAME),
                (String) doc.get(Fields.TEXT),
                (boolean) doc.get(Fields.LIKE),
                timeStamp.toDate());
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, note.getName());
        answer.put(Fields.TEXT, note.getText());
        answer.put(Fields.LIKE, note.isLike());
        answer.put(Fields.DATE, note.getDateCreated());
        return answer;
    }
}
