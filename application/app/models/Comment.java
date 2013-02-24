package models;

import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

public class Comment extends Model{
	
	public String author;
	public Date postedAt;
	
	@Lob
	public String content;
	
	@ManyToOne
	public Post post;
	
	public Comment(Post post, String author, String content){
		this.content = content;
		this.author = author;
		this.author = author;
		this.postedAt = new Date();
	}

}
