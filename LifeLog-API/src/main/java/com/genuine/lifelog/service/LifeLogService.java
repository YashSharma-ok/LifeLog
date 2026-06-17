package com.genuine.lifelog.service;

import java.util.List;


import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.NoteResponse;

public interface LifeLogService {

	public NoteResponse addNote(NoteRequest note);
	public NoteResponse updateNote(NoteRequest note);
	public NoteResponse trashNote(String note);
	public NoteResponse deleteNote(NoteRequest note);
	
	public NoteResponse getNoteById(Long id);
	public List<NoteResponse> getAllNotes(Long page, Long limit);
	public List<NoteResponse> getTrashedNotes(Long page, Long limit);
	public List<NoteResponse> findNotesByTagNames(List<String> tagNames);
	public List<NoteResponse> getSearchedNotes(String keyword, Long page, Long limit);
	
}
