package com.bridgelabz.fundoonotes.noteservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.notedao.LabelDetailsRepository;
import com.bridgelabz.fundoonotes.notedao.NoteDetailsRepository;
import com.bridgelabz.fundoonotes.notemodels.Labels;
import com.bridgelabz.fundoonotes.notemodels.Note;
import com.bridgelabz.fundoonotes.noteutility.GenerateTokenImlp;


@Service
public class NoteServiceImpl implements NoteServiceInf {


	@Autowired
	GenerateTokenImlp generateToken;

	@Autowired
	NoteDetailsRepository noteDetailsRepository;

	@Autowired
	LabelDetailsRepository labelDetailsRepository;



	public Note createNote(String token,Note note, HttpServletRequest request) {
		int id = generateToken.verifyToken(token);
		note.setUserId(id);
		return  noteDetailsRepository.save(note);	

	}

	public Note updateNote(int noteId, String token, Note note, HttpServletRequest request) {
		int userId = generateToken.verifyToken(token);
		Optional<Note> possiblyUser = noteDetailsRepository.findByUserIdAndNoteId(userId, noteId);
		return possiblyUser.map(Currentnote -> noteDetailsRepository.save(Currentnote
				.setTitle(note.getTitle()).setDescription(note.getDescription())))
				.orElseGet(()->null);
	}

	public boolean deleteNote(int noteId, String token,HttpServletRequest request)
	{
		int userId =generateToken.verifyToken(token);
		Optional<Note> possiblyUser =  noteDetailsRepository.findByUserIdAndNoteId(userId, noteId);
		return possiblyUser.map(Currentnote-> {noteDetailsRepository.delete(Currentnote);
		return true;}).orElseGet(()->false);		
	}

	public List<Note> retriveNote(String token, HttpServletRequest request)
	{
		int userId = generateToken.verifyToken(token);
		List<Note> notesList =   noteDetailsRepository.findAllByUserId(userId);
		if(!notesList.isEmpty())
		{
			return notesList;
		}
		return null;

	}
	////////////////////////////////////////////////////////////////////////
	//labels

	public Labels createLabel(String token, Labels label, HttpServletRequest request) 
	{
		int userId = generateToken.verifyToken(token);
		label.setUserId(userId);
		return labelDetailsRepository.save(label);
	}

	public Labels updateLabel(int labelId, String token, Labels label, HttpServletRequest request) {

		int userId = generateToken.verifyToken(token);
		Optional<Labels> possiblyUser = labelDetailsRepository.findByuserIdAndLabelId(userId, labelId);
		return possiblyUser.map(currentLabel-> labelDetailsRepository.save(currentLabel.setLabelName(label.getLabelName()))
				).orElseGet(()->null);

	}

	public boolean deleteLabel(int labelId, String token, HttpServletRequest request) 
	{	
		int userId=generateToken.verifyToken(token);
		Optional<Labels> possiblyUser= labelDetailsRepository.findByuserIdAndLabelId(userId, labelId);
		return possiblyUser.map(CurrentLabel-> {labelDetailsRepository.delete(CurrentLabel);
		return true;}).orElseGet(()->false);
	}

	public List<Labels> retrieveLabel(String token, HttpServletRequest request) 
	{
		int userId = generateToken.verifyToken(token);
		List<Labels> labelsList =   labelDetailsRepository.findAllByUserId(userId);
		if(!labelsList.isEmpty())
		{
			return labelsList;
		}
		return null;
	}

	public boolean mergeLabelToNote(String token, int noteId, int labelId, HttpServletRequest request) 
	{
		int userId = generateToken.verifyToken(token);
		Optional<Labels> optional1=labelDetailsRepository.findById(labelId);
		Optional<Note> optional2=noteDetailsRepository.findById(noteId);
		if (optional1.isPresent() && optional1.isPresent())
		{
			Note note = optional2.get();
			Labels label = optional1.get();
			if(label.getUserId()==userId && note.getUserId()==userId)
			{
				List<Labels> labels = note.getLabelList();
				labels.add(label);
				if (!labels.isEmpty())
				{
					note.setLabelList(labels);
					noteDetailsRepository.save(note);
				}
				return true;
			}
		}
		return false;
	}


	public boolean deleteLabelToNote(String token, int noteId, int labelId, HttpServletRequest request) {

		int userId = generateToken.verifyToken(token);
		Optional<Labels> optionallabel=labelDetailsRepository.findById(labelId);
		Optional<Note> optionalnote=noteDetailsRepository.findById(noteId);
		if (optionallabel.isPresent() && optionalnote.isPresent()) {   
			Note note = optionalnote.get();
			List<Labels> labels = note.getLabelList();
			if (!labels.isEmpty()) {
				labels = labels.stream().filter(label -> label.getLabelId()!= labelId).collect(Collectors.toList());
				note.setLabelList(labels);
				noteDetailsRepository.delete(note);
				return true;
			} }
		return false;
	}







}
