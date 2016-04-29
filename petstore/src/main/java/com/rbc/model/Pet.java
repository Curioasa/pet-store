package com.rbc.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.rbc.utils.PetStatus;

@Entity
public class Pet {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private String category;
	
	@Column(nullable=false)
	private ArrayList<String> pictureUrls;
	
	@Column
	private ArrayList<String> tags;
	
	@Column
    @Enumerated(EnumType.STRING)
	private PetStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<String> getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(ArrayList<String> pictureUrls) {
		this.pictureUrls = pictureUrls;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public PetStatus getStatus() {
		return status;
	}

	public void setStatus(PetStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", category=" + category
				+ ", pictureUrls=" + pictureUrls + ", tags=" + tags
				+ ", status=" + status + "]";
	}

}
