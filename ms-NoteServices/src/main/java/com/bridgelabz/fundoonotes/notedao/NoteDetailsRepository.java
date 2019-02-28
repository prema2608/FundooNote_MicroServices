package com.bridgelabz.fundoonotes.notedao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bridgelabz.fundoonotes.notemodels.Note;

public interface NoteDetailsRepository extends JpaRepository<Note, Integer>
{
	List<Note> findAllByUserId(int userId);

	Optional<Note> findByUserIdAndNoteId(int userId,int noteId);





}