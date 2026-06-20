package com.genuine.lifelog.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import com.genuine.lifelog.exception.ValidationException;
import com.genuine.lifelog.service.LifeLogService;
import com.genuine.lifelog.utility.LifeLogUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("memo/")
public class LifeLogController {

    private LifeLogService service;

    public LifeLogController(LifeLogService service) {
        this.service = service;
    }

    @GetMapping("notes")
    public ResponseEntity<LifeLogResponse> getNotes(Pageable pageable) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        Page<NoteResponse> noteResPage = this.service.getAllNotes(pageable);
        res.setStatus("success");
        if (noteResPage == null || noteResPage.getContent().size() == 0)
            res.setMessage("No note is present");
        else
            res.setMessage("Notes have been successfully fetched!");
        res.setData(noteResPage.getContent());
        res.setPagenation(noteResPage);

        return ResponseEntity.ok(res);
    }
    

    @GetMapping("notes/{noteId}")
    public ResponseEntity<LifeLogResponse> getNote(@PathVariable @NotBlank String noteId) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        if(!LifeLogUtils.isNumber(noteId)) throw new ValidationException(List.of("Note Id should be a number!"));
        NoteResponse noteRes = this.service.getNoteById(Long.valueOf(noteId));
        res.setStatus("success");
        res.setMessage("Note has been successfully fetched!");
        res.setData(noteRes);
        res.noPagenation();

        return ResponseEntity.ok(res);
    }

    @GetMapping("notes/trash")
    public ResponseEntity<LifeLogResponse> getTrashedNotes(Pageable pageable) throws Exception {    	
        LifeLogResponse res = new LifeLogResponse();
        Page<NoteResponse> noteRes = this.service.getTrashedNotes(pageable);
        res.setStatus("success");
        if (noteRes == null || noteRes.getContent().size() == 0)
            res.setMessage("No trashed notes");
        else
            res.setMessage("Trashed notes have been successfully fetched!");
        res.setData(noteRes.getContent());
        

        return ResponseEntity.ok(res);
    }

    @PostMapping("notes")
    public ResponseEntity<LifeLogResponse> createNote(@Valid @RequestBody NoteRequest note) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.addNote(note);
        res.setStatus("success");
        res.setMessage("Note has been successfully added!");
        res.setData(noteRes);
        res.noPagenation();
        

        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("notes/{noteId}")
    public ResponseEntity<LifeLogResponse> updateNote(@RequestBody NoteRequest note, @PathVariable @NotBlank String noteId) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        note.setId(Long.parseLong(noteId));
        NoteResponse noteRes = this.service.updateNote(note);
        res.setStatus("success");
        res.setMessage("Note has been successfully updated!");
        res.setData(noteRes);
        res.noPagenation();

        return ResponseEntity.ok(res);
    }

    @PutMapping("notes/trash/{noteId}")
    public ResponseEntity<LifeLogResponse> trashNote(@PathVariable @NotBlank String noteId) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.trashNote(noteId);
        res.setStatus("success");
        res.setMessage("Note has been successfully trashed!");
        res.setData(noteRes);
        res.noPagenation();

        return ResponseEntity.ok(res);
    }
    
    @PostMapping("notes/search")
    public ResponseEntity<LifeLogResponse> searchNotes(@RequestBody @NotNull SearchNoteRequest request, Pageable pageable) throws Exception {
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
            res.setMessage("No note is found");
        else
            res.setMessage("Notes have been successfully fetched!");
        res.setData(noteResPage.getContent());
        res.setPagenation(noteResPage);

        return ResponseEntity.ok(res);

    }
}
