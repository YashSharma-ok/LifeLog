package com.genuine.lifelog.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.request.SearchNoteRequest;
import com.genuine.lifelog.dto.response.LifeLogResponse;
import com.genuine.lifelog.dto.response.NoteResponse;
import com.genuine.lifelog.service.LifeLogService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("memo/")
public class LifeLogController {

    private LifeLogService service;

    public LifeLogController(LifeLogService service) {
        this.service = service;
    }

    @GetMapping("notes")
    public ResponseEntity<LifeLogResponse> getNotes(Pageable pageable){
        LifeLogResponse res = new LifeLogResponse();
        Page<NoteResponse> noteResPage = this.service.getAllNotes(pageable);
        res.setStatus("success");
        if (noteResPage == null || noteResPage.getContent().size() == 0)
            res.success("No note is present", noteResPage);
        else
        	res.success("Notes have been successfully fetched!", noteResPage);

        return ResponseEntity.ok(res);
    }
    

    @GetMapping("notes/{noteId}")
    public ResponseEntity<LifeLogResponse> getNote(@Valid @PathVariable Long noteId){
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.getNoteById(noteId);
        res.success("Note has been successfully fetched!", noteRes);

        return ResponseEntity.ok(res);
    }

    @GetMapping("notes/trash")
    public ResponseEntity<LifeLogResponse> getTrashedNotes(Pageable pageable){    	
        LifeLogResponse res = new LifeLogResponse();
        Page<NoteResponse> noteRes = this.service.getTrashedNotes(pageable);
        if (noteRes == null || noteRes.getContent().size() == 0)
        	res.success("No trashed notes", noteRes);
        else
            res.success("Trashed notes have been successfully fetched!", noteRes);        

        return ResponseEntity.ok(res);
    }

    @PostMapping("notes")
    public ResponseEntity<LifeLogResponse> createNote(@Valid @RequestBody NoteRequest note){
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.addNote(note);
        res.success("Note has been successfully added!", noteRes);        

        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("notes/{noteId}")
    public ResponseEntity<LifeLogResponse> updateNote(@Valid @RequestBody NoteRequest note, @Valid @PathVariable Long noteId){
        LifeLogResponse res = new LifeLogResponse();
        note.setId(noteId);
        NoteResponse noteRes = this.service.updateNote(note);
        res.success("Note has been successfully updated!", noteRes);

        return ResponseEntity.ok(res);
    }

    @PutMapping("notes/trash/{noteId}")
    public ResponseEntity<LifeLogResponse> trashNote(@Valid @PathVariable Long noteId){
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.trashNote(noteId);
        res.success("Note has been successfully trashed!", noteRes);

        return ResponseEntity.ok(res);
    }
    
    @DeleteMapping("notes/{noteId}")
    public ResponseEntity<LifeLogResponse> deleteNote(@Valid @PathVariable Long noteId){
        LifeLogResponse res = new LifeLogResponse();
        this.service.deleteNote(noteId);
        res.success("Note has been successfully deleted!");

        return ResponseEntity.ok(res);
    }
    
    @PostMapping("notes/search")
    public ResponseEntity<LifeLogResponse> searchNotes(@RequestBody @NotNull SearchNoteRequest request, Pageable pageable){
    	LifeLogResponse res = new LifeLogResponse();
    	Page<NoteResponse> noteResPage;
//    	if(request != null && (!LifeLogUtils.checkNotBlank(request.getContent()) && !LifeLogUtils.checkNotBlank(request.getTitle()) && (request.getTags() == null || request.getTags().isEmpty()))) {
    	if(request != null && (request.getText() == null || request.getText() == "")) {
    		noteResPage = this.service.getAllNotes(pageable);
    	} else {
    		noteResPage = this.service.getSearchedNotes(request.getText(), pageable);
    	}
    	
        res.setStatus("success");
        if (noteResPage == null || noteResPage.getContent().size() == 0)
            res.success("No note is found", noteResPage);
        else
            res.success("Notes have been successfully fetched!", noteResPage);

        return ResponseEntity.ok(res);

    }
}
