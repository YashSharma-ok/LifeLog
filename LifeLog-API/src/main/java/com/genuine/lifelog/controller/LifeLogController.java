package com.genuine.lifelog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genuine.lifelog.dto.request.NoteRequest;
import com.genuine.lifelog.dto.response.LifeLogResponse;
import com.genuine.lifelog.dto.response.NoteResponse;
import com.genuine.lifelog.service.LifeLogService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("memo/")
public class LifeLogController {

    private LifeLogService service;

    public LifeLogController(LifeLogService service) {
        this.service = service;
    }

    @GetMapping("notes")
    public ResponseEntity<LifeLogResponse> getNotes() throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        List<NoteResponse> noteRes = this.service.getAllNotes(null, null);
        res.setStatus("success");
        if (noteRes == null || noteRes.size() == 0)
            res.setMessage("No note is present");
        else
            res.setMessage("Notes have been successfully fetched!");
        res.setData(noteRes);

        return ResponseEntity.ok(res);
    }

    @GetMapping("notes/trash")
    public ResponseEntity<LifeLogResponse> getTrashedNotes() throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        List<NoteResponse> noteRes = this.service.getTrashedNotes(null, null);
        res.setStatus("success");
        if (noteRes == null || noteRes.size() == 0)
            res.setMessage("No trashed notes");
        else
            res.setMessage("Trashed notes have been successfully fetched!");
        res.setData(noteRes);

        return ResponseEntity.ok(res);
    }

    @PostMapping("notes")
    public ResponseEntity<LifeLogResponse> createNote(@Valid @RequestBody NoteRequest note) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.addNote(note);
        res.setStatus("success");
        res.setMessage("Note has been successfully added!");
        res.setData(noteRes);

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

        return ResponseEntity.ok(res);
    }

    @PutMapping("notes/trash/{noteId}")
    public ResponseEntity<LifeLogResponse> trashNote(@PathVariable @NotBlank String noteId) throws Exception {
        LifeLogResponse res = new LifeLogResponse();
        NoteResponse noteRes = this.service.trashNote(noteId);
        res.setStatus("success");
        res.setMessage("Note has been successfully trashed!");
        res.setData(noteRes);

        return ResponseEntity.ok(res);
    }
}
