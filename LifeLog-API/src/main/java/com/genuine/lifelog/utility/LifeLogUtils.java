package com.genuine.lifelog.utility;

import java.util.ArrayList;
import java.util.List;

import com.genuine.lifelog.dto.response.NoteResponse;
import com.genuine.lifelog.entity.Note;
import com.genuine.lifelog.entity.Tag;

public class LifeLogUtils {
	
	public static NoteResponse noteEntityToDto(Note note) {
		if(note == null) return null;
		
		NoteResponse res = new NoteResponse(
											note.getId(),
											note.getTitle(),
											note.getContent(),
											(note.getTags() == null || note.getTags().size() == 0) 
													? new ArrayList<String>() 
													: note.getTags().stream().map(Tag::getName).toList()
											);
		return res;	
	}

	public static boolean checkIdValid(Long id) {
		return id != null && id > 0;		
	}
	
	public static boolean checkIdValid(String strId) {
		if(!isNumber(strId)) return false;
		Long id = Long.parseLong(strId);
		return checkIdValid(id);		
	}

	public static boolean checkNotBlank(String content) {
		return content != null && !content.isBlank();
		
	}

	public static boolean isNumber(String noteId) {
	    if (noteId == null || noteId.isEmpty()) {
	        return false;
	    }
	    return noteId.matches("\\d+"); // only digits allowed
	}

	public static List<NoteResponse> noteEntityListToDtoList(List<Note> notes) {
		List<NoteResponse> list = new ArrayList<NoteResponse>();
		if(notes == null) return list;
		
		for(Note note : notes) {
			list.add(noteEntityToDto(note));
		}
		return list;
	}

}
