package com.genuine.lifelog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.NoteResponse;
import com.genuine.lifelog.entity.Note;
import com.genuine.lifelog.entity.Tag;
import com.genuine.lifelog.exception.ResourceNotFoundException;
import com.genuine.lifelog.exception.ValidationException;
import com.genuine.lifelog.repository.NoteRepository;
import com.genuine.lifelog.repository.TagRepository;
import com.genuine.lifelog.utility.LifeLogUtils;

@Service
public class LifeLogServiceImpl implements LifeLogService {

    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;

    public LifeLogServiceImpl(
            NoteRepository noteRepository,
            TagRepository tagRepository
    ) {
        this.noteRepository = noteRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public NoteResponse addNote(NoteRequest note) {

        List<String> errors = validateNoteForAdd(note);

        if (!errors.isEmpty()) {
            throw new ValidationException(
                    "Unable to add Note. Please try again!",
                    errors
            );
        }

        List<Tag> tags = processTags(note.getTags());

        Note entityNote = new Note(
                note.getTitle(),
                note.getContent(),
                tags
        );

        entityNote.setIsTrashed(false);

        Note savedNote = noteRepository.save(entityNote);

        return LifeLogUtils.noteEntityToDto(savedNote);
    }

    @Override
    public NoteResponse updateNote(NoteRequest note) {

        List<String> errors = validateNoteForUpdate(note);

        if (!errors.isEmpty()) {
            throw new ValidationException(
                    "Unable to update Note. Please try again!",
                    errors
            );
        }

        List<Tag> tags = processTags(note.getTags());

        Note entityNote = noteRepository.findById(note.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Unable to update Note. Please try again!")
                );

        entityNote.setTitle(note.getTitle());
        entityNote.setContent(note.getContent());
        entityNote.setTags(tags);

        Note savedNote = noteRepository.save(entityNote);

        return LifeLogUtils.noteEntityToDto(savedNote);
    }

    @Override
    public NoteResponse trashNote(String noteId) {

        if (!LifeLogUtils.isNumber(noteId)
                || !LifeLogUtils.checkIdValid(noteId)) {

            throw new ValidationException(
                    "Unable to trash Note. Please try again!",
                    List.of("Invalid note Id")
            );
        }

        Long id = Long.parseLong(noteId);

        Note entityNote = noteRepository.findById(id)
                .orElseThrow(() ->
                        new ValidationException(
                                "Unable to trash Note. Please try again!",
                                List.of("Note not found")
                        )
                );

        entityNote.setIsTrashed(true);

        Note savedNote = noteRepository.save(entityNote);

        return LifeLogUtils.noteEntityToDto(savedNote);
    }

    @Override
    public NoteResponse deleteNote(NoteRequest note) {
        return null;
    }

    @Override
    public NoteResponse getNoteById(Long id) {
        return null;
    }

    @Override
    public List<NoteResponse> getAllNotes(Long page, Long limit) {
        return LifeLogUtils.noteEntityListToDtoList(
                noteRepository.findByIsTrashedNot(true)
        );
    }

    @Override
    public List<NoteResponse> getTrashedNotes(Long page, Long limit) {
        return LifeLogUtils.noteEntityListToDtoList(
                noteRepository.findByIsTrashed(true)
        );
    }

    @Override
    public List<NoteResponse> findNotesByTagNames(List<String> tagNames) {
        return null;
    }

    private List<Tag> processTags(List<String> tags) {

        if (tags == null || tags.isEmpty()) {
            return new ArrayList<>();
        }

        tags = tags.stream()
                .filter(tag -> !tag.isBlank())
                .map(tag -> tag.trim().toUpperCase())
                .toList();

        if (tags.isEmpty()) {
            return new ArrayList<>();
        }

        List<Tag> existingTags =
                tagRepository.findByNameIn(tags);

        List<String> existingTagNames =
                existingTags.stream()
                        .map(Tag::getName)
                        .toList();

        List<String> newTags = tags.stream()
                .filter(tag ->
                        !existingTagNames.contains(tag))
                .toList();

        existingTags.addAll(
                tagRepository.saveAll(
                        getListOfTagByNames(newTags)
                )
        );

        return existingTags;
    }

    private List<Tag> getListOfTagByNames(
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

    private List<String> validateNoteForAdd(
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

    private List<String> validateNoteForUpdate(
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

	@Override
	public List<NoteResponse> getSearchedNotes(String keyword, Long page, Long limit) {
		return LifeLogUtils.noteEntityListToDtoList(
                noteRepository.searchByKeyword(keyword)
        );
	}
}
