package com.bridgelabz.fundoonotes.notedao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.notemodels.Labels;

@Repository
public interface LabelDetailsRepository extends JpaRepository<Labels, Integer> {

	List<Labels> findAllByUserId(int userId);

	Optional<Labels> findByuserIdAndLabelId(int userId,int noteId);





}
