package com.genuine.lifelog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.NoteResponse;

import jakarta.validation.Valid;

public interface LifeLogService {

	public NoteResponse addNote(NoteRequest note);
	public NoteResponse updateNote(NoteRequest note);
	public NoteResponse trashNote(Long noteId);
	public void deleteNote(Long noteId);
	
	public NoteResponse getNoteById(Long noteId);
	public Page<NoteResponse> getAllNotes(Pageable pageable);
	public Page<NoteResponse> getTrashedNotes(Pageable pageable);
	public Page<NoteResponse> findNotesByTagNames(List<String> tagNames);
	public Page<NoteResponse> getSearchedNotes(String keyword, Pageable pageable);
	
}
