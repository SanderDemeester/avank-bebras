import org.junit.*;
import java.util.*;

import play.db.jpa.GenericModel.JPAQuery;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Test
	public void createAndRetrieveUser(){
		new User("demeester.sander@gmail.com",
				"pw",
		"Sander").save();

		User sander = User.find("byEmail", 
		"demeester.sander@gmail.com").first();

		assertNotNull(sander);
		assertEquals("Sander",((User)sander).fullname);
	}

	@Test
	public void tryConnectAtUser(){
		new User("demeester.sander@gmail.com","pw","Sander").save();

		assertNotNull(User.connect("demeester.sander@gmail.com", "pw"));
		assertNull(User.connect("demeester.sander@gmail.com", "badpw"));
		assertNull(User.connect("k@gmail.com", "pw"));
	}

	@Before
	public void setup(){
		Fixtures.deleteDatabase();
	}

	@Test
	public void createPost(){
		User sander =  new User("demeester.sander@gmail.com","pw","sander");

		//create new post

		new Post(sander,"My First post","Hello world").save();
		assertEquals(1,Post.count());

		List<Post> sanderPosts = Post.find("byAuthor",sander).fetch();
		assertEquals(1, sanderPosts.size());

		Post firstPost = sanderPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(sander, firstPost.author);
		assertEquals("My first post", firstPost.title);
		assertEquals("Hello world", firstPost.content);
		assertNotNull(firstPost.postedAt);
	}
	
	@Test
	public void postComments() {
	    // Create a new user and save it
	    User bob = new User("demeester.sander@gmail.com", "pw", "Sander").save();
	 
	    // Create a new post
	    Post bobPost = new Post(bob, "My first post", "Hello world").save();
	 
	    // Post a first comment
	    new Comment(bobPost, "Jeff", "Nice post").save();
	    new Comment(bobPost, "Alice", "where is bob?").save();
	 
	    // Retrieve all comments
	    List<Comment> bobPostComments = Comment.find("byPost", bobPost).fetch();
	 
	    // Tests
	    assertEquals(2, bobPostComments.size());
	 
	    Comment firstComment = bobPostComments.get(0);
	    assertNotNull(firstComment);
	    assertEquals("Jeff", firstComment.author);
	    assertEquals("Nice post", firstComment.content);
	    assertNotNull(firstComment.postedAt);
	 
	    Comment secondComment = bobPostComments.get(1);
	    assertNotNull(secondComment);
	    assertEquals("Tom", secondComment.author);
	    assertEquals("I knew that !", secondComment.content);
	    assertNotNull(secondComment.postedAt);
	}
	
//	@Test
	public void useTheCommentsRelation(){
		User sander = new User("demeester.sander@gmail.com","pw","Sander").save();
		
		Post sanderPost = new Post(sander,"My first post","Hello world").save();
		
		sanderPost.addComment("Jeff", "Nice post");
		sanderPost.addComment("Alice", "where is bob :(");
		
		
		
		assertEquals(1, User.count());
	    assertEquals(1, Post.count());
	    assertEquals(2, Comment.count());
	    
	    sanderPost = Post.find("byAuthor",sander).first();
	    assertNotNull(sanderPost);
	    
	    assertEquals(2, sanderPost.comments.size());
	    
	    
		
	}
}
