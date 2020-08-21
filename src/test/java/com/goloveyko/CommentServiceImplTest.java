package com.goloveyko;

import com.goloveyko.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CommentServiceImplTest {

  @InjectMocks
  private CommentServiceImpl commentService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test_doesLineHaveComment_emptyString() {
    String s = "";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_onlyCode() {
    String s = "int y = 0;";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_onlyComment() {
    String s = "//a comment";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentInTheEnd() {
    String s = "int y = 0; //a comment";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentInTheBeginningLf() {
    String s = "//a comment\nint y = 0;";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentInTheEndCrlf() {
    String s = "//a comment\r\nint y = 0;";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentInTheEndCr() {
    String s = "//a comment\rint y = 0;";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentSingleSlash() {
    String s = "/";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_blockComment() {
    String s = "/*comment */";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_blockCommentWithCode() {
    String s = "/*comment */ int y = 0;";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentNestedInBlockComment() {
    String s = "/* //comment */ int y = 0;";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_blockMultiLineComment() {
    String s = "/*\\n\\n*/";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_blockMultiLineCommentWithDifferentEndings() {
    String s = "/*\\r\\n \\n \\r*/";
    boolean expected = true;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_simpleCommentInsideString() {
    String s = "String s = \"//\";";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void test_doesLineHaveComment_blockCommentInsideString() {
    String s = "String s = \"/*\";";
    boolean expected = false;

    boolean actual = commentService.doesLineHaveComment(s.toCharArray());

    Assert.assertEquals(expected, actual);
  }
}
