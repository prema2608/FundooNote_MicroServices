package com.bridgelabz.fundoonotes.notecontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.notemodels.Labels;
import com.bridgelabz.fundoonotes.notemodels.Note;
import com.bridgelabz.fundoonotes.noteservice.NoteServiceImpl;


@Controller
@RequestMapping("/note ")
public class NoteController {


	@Autowired
	private NoteServiceImpl noteService;

	@PostMapping(value = "/createnote")
	public ResponseEntity<String> createNote( @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request) {

		Note freshNote=noteService.createNote(token, note, request);
		if (freshNote!=null) {
			return new ResponseEntity<String>(" Succesfully Created the Note",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Error in creating Note",HttpStatus.BAD_REQUEST);
		}
	}



	@PostMapping(value = "/updatenote")
	public ResponseEntity<String> updateNote(@RequestParam("noteId") int noteId, @RequestHeader("token") String token,@RequestBody Note note, HttpServletRequest request)
	{
		Note updateNote=noteService.updateNote(noteId,token,note, request);
		if (updateNote!=null) {
			return new ResponseEntity<String>(" Succesfully updated the Note",HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Error in updating Note...!! Give the correct Id",HttpStatus.BAD_REQUEST);
	}


	@DeleteMapping(value="/deletenote")
	public ResponseEntity<String> deleteNote(@RequestParam("noteId") int noteId, @RequestHeader("token") String token,HttpServletRequest request)
	{
		boolean deleteNote =noteService. deleteNote(noteId, token, request);
		if(deleteNote!=false)
		{
			return new ResponseEntity<String>("Successfully deleted the note",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Error in deleting the note",HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value="/retrivenote")
	public ResponseEntity<?> retriveNote(@RequestHeader("token") String token ,HttpServletRequest request)
	{
		List<Note> notesList = noteService.retriveNote(token,request);
		if(!notesList.isEmpty())
		{
			return new ResponseEntity<List<Note>>(notesList,HttpStatus.FOUND);
		}
		else
			return new ResponseEntity<String>("Error in retriveing the notes",HttpStatus.NOT_FOUND);
	}

	//////////////////////////////////////////////////
	//Labels


	@PostMapping(value="/createlabel")
	public ResponseEntity<?> createLabel(@RequestHeader("token") String token , @RequestBody Labels label, HttpServletRequest request )
	{
		Labels CurrentLabel = noteService.createLabel(token, label, request);
		if(CurrentLabel!=null)
		{
			return new ResponseEntity<String>("Succesfully created the Label", HttpStatus.OK);
		}
		else
			return new ResponseEntity<String> ("Error in creating Label",HttpStatus.BAD_REQUEST);
	}


	@PostMapping(value="/updatelabel")
	public ResponseEntity<?> updateLabel(@RequestParam("labelId") int labelId,@RequestHeader("token") String token ,@RequestBody Labels label,HttpServletRequest request )
	{
		Labels updateLabel = noteService.updateLabel(labelId,token,label,request);
		if(updateLabel!=null)
		{
			return new ResponseEntity<String> ("succesfully updated the label",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Error in updating the label",HttpStatus.NOT_FOUND);
	}


	@DeleteMapping(value="/deletelabel")
	public ResponseEntity<?> deleteLabel(@RequestParam("labelId") int labelId,@RequestHeader("token") String token,HttpServletRequest request)
	{
		boolean deleteLabel = noteService.deleteLabel(labelId,token,request);
		if(deleteLabel!=false)
		{
			return new ResponseEntity<String>("Succesfully deleted the label",HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>("Error in deleting the label",HttpStatus.NOT_FOUND);
	}


	@GetMapping(value = "/retrievelabel")
	public ResponseEntity<?> retrieveLabel(@RequestHeader("token") String token, HttpServletRequest request) {
		List<Labels> labelsList = noteService.retrieveLabel(token, request);
		if (!labelsList.isEmpty()) {
			return new ResponseEntity<List<Labels>>(labelsList, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<String>("Error in retrieving the label", 
					HttpStatus.NOT_FOUND);
		}
	}



	@PostMapping(value = "/mergelabelnote")
	public ResponseEntity<String> mergeLabelToNote(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request)
	{
		if (noteService.mergeLabelToNote(token,noteId, labelId,request))
			return new ResponseEntity<String>(" Succesfully Mapped",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error in Mapping",HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = "/deletelabeltonote")
	public ResponseEntity<String> deleteLabelToNote(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request)
	{
		if (noteService.deleteLabelToNote(token,noteId, labelId,request))
			return new ResponseEntity<String>(" Succesfully Mapped",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Error in Deleting",HttpStatus.CONFLICT);
	}




















}











































































































































































































































































































































































//		
//		  @PutMapping(value = "/mapnotelabel")
//		    public ResponseEntity<?> mapNoteLabel(@RequestParam("noteId") int noteId ,@RequestParam("labelId") int labelId ,@RequestHeader("token") String token, HttpServletRequest request) {
//		    
//		    	if (noteService.mapNoteLabel(token,noteId,labelId, request)!=false) {
//		            return new ResponseEntity<String>("Mapped Successfully", HttpStatus.FOUND);
//		        }else {
//		    	
//		            return new ResponseEntity<String>("Dinied In Mapping ",HttpStatus.NOT_FOUND);
//		        }
//			
//			}
//		  
//		  @DeleteMapping(value="/deletenotelabel")
//		    public ResponseEntity<?> deleteNoteLabel(@RequestHeader("token") String token,@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId ,HttpServletRequest request){
//		  
//		    if(noteService.removeNoteLabel(token, noteId, labelId, request))	{
//		    	return new ResponseEntity<String>("NoteLabel Deleted Successfully", HttpStatus.FOUND);
//		    
//	    	}else
//	    	{
//	            return new ResponseEntity<String>("Dinied In Deleting NoteLabel",HttpStatus.NOT_FOUND);
//	        }
//		
//		}
//		



