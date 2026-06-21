package com.genuine.lifelog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.NoteResponse;
import com.genuine.lifelog.entity.Note;
import com.genuine.lifelog.entity.Tag;
import com.genuine.lifelog.exception.ResourceNotFoundException;
import com.genuine.lifelog.exception.ValidationException;
import com.genuine.lifelog.helper.LifeLogServiceHelper;
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

        List<String> errors = LifeLogServiceHelper.validateNoteForAdd(note);

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

        List<String> errors = LifeLogServiceHelper.validateNoteForUpdate(note);

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
    public NoteResponse trashNote(Long noteId) {
        Note entityNote = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new ValidationException(
                                "Unable to trash Note. Please try again!",
                                List.of("Note not found")
                        )
                );

        if(entityNote.getIsTrashed() && entityNote.getIsTrashed().booleanValue()) {
        	throw new ValidationException(
                    "Note is already trashed!", null
            );
        }
        entityNote.setIsTrashed(true);

        Note savedNote = noteRepository.save(entityNote);

        return LifeLogUtils.noteEntityToDto(savedNote);
    }

    @Override
    public NoteResponse deleteNote(NoteRequest note) {
        return null;
    }

    @Override
    public NoteResponse getNoteById(Long noteId) {
    	Optional<Note> note = this.noteRepository.findById(noteId);
    	if(note.isPresent())
    		return LifeLogUtils.noteEntityToDto(note.get());
    	throw new ResourceNotFoundException();
    }

    @Override
    public Page<NoteResponse> getAllNotes(Pageable pageable) {
    	Page<Note> page = noteRepository.findByIsTrashedNot(true, pageable);    	
        return page.map(LifeLogUtils::noteEntityToDto);
    }

    @Override
    public Page<NoteResponse> getTrashedNotes(Pageable pageable) {
    	Page<Note> page = noteRepository.findByIsTrashed(true, pageable);    	
    	return page.map(LifeLogUtils::noteEntityToDto);
    }

    @Override
    public Page<NoteResponse> findNotesByTagNames(List<String> tagNames) {
        return null;
    }

	@Override
	public Page<NoteResponse> getSearchedNotes(String keyword, Pageable pageable) {
		Page<Note> page =  noteRepository.searchByKeyword(keyword, pageable);
		return page.map(LifeLogUtils::noteEntityToDto);
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
                        LifeLogServiceHelper.getListOfTagByNames(newTags)
                )
        );

        return existingTags;
    }
}
