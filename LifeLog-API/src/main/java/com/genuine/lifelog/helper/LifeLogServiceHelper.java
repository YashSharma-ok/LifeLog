package com.genuine.lifelog.helper;

import java.util.ArrayList;
import java.util.List;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.entity.Tag;
import com.genuine.lifelog.utility.LifeLogUtils;

public class LifeLogServiceHelper {

    public static List<Tag> getListOfTagByNames(
            List<String> tags
    ) {

        if (tags == null) {
            return new ArrayList<>();
        }

        return tags.stream()
                .map(tag -> {
                    Tag t = new Tag();
                    t.setName(tag);
                    return t;
                })
                .toList();
    }

    public static List<String> validateNoteForAdd(
            NoteRequest note
    ) {

        List<String> err = new ArrayList<>();

        if (!LifeLogUtils.checkNotBlank(
                note.getContent())) {
            err.add("Content is required");
        }

        if (!LifeLogUtils.checkNotBlank(
                note.getTitle())) {
            err.add("Title is required");
        }

        return err;
    }

    public static List<String> validateNoteForUpdate(
            NoteRequest note
    ) {

        List<String> err = new ArrayList<>();

        if (!LifeLogUtils.checkIdValid(
                note.getId())) {
            err.add("Invalid note Id");
        }

        if (!LifeLogUtils.checkNotBlank(
                note.getContent())) {
            err.add("Content is required");
        }

        if (!LifeLogUtils.checkNotBlank(
                note.getTitle())) {
            err.add("Title is required");
        }

        return err;
    }
}
