package com.genuine.lifelog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.NoteResponse;

public interface LifeLogService {

	public NoteResponse addNote(NoteRequest note);
	public NoteResponse updateNote(NoteRequest note);
	public NoteResponse trashNote(String note);
	public NoteResponse deleteNote(NoteRequest note);
	
	public NoteResponse getNoteById(Long id);
	public Page<NoteResponse> getAllNotes(Pageable pageable);
	public Page<NoteResponse> getTrashedNotes(Long page, Long limit);
	public Page<NoteResponse> findNotesByTagNames(List<String> tagNames);
	public Page<NoteResponse> getSearchedNotes(String keyword, Long page, Long limit);
	
}
