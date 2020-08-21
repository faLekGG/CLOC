package com.goloveyko.service.impl;

import com.goloveyko.service.CommentService;

public class CommentServiceImpl implements CommentService {

  private boolean isBlockComment = false;
  private boolean isString = false;

  @Override
  public boolean doesLineHaveComment(char[] line) {
    boolean isLineComment = false;
    for (int i = 0; i < line.length; i++) {
      if (isLineComment && isLineCommentEnded(line, i)) {
        isLineComment = false;
      } else if (isBlockComment && isBlockCommentEnded(line, i)) {
        isBlockComment = false;
      } else if (isString && isStringEnded(line, i)) {
        isString = false;
      } else if (!isBlockComment && !isString && isLineCommentBegan(line, i)) {
        isLineComment = true;
      } else if (!isLineComment && !isString && isBlockCommentBegan(line, i)) {
        isBlockComment = true;
      } else if (!isLineComment && !isBlockComment && isStringBegan(line, i)) {
        isString = true;
      }
    }

    return isLineComment || isBlockComment;
  }

  private static boolean isLineCommentBegan(char[] line, int i) {
    return line[i] == '/' && line.length > i + 1 && line[i + 1] == '/';
  }

  private static boolean isLineCommentEnded(char[] line, int i) {
    return (line[i] == '\n' || line[i] == '\r') && line.length > i + 1 && line[i + 1] == '\n';
  }

  private static boolean isBlockCommentBegan(char[] line, int i) {
    return line[i] == '/' && line.length > i + 1 && line[i + 1] == '*';
  }

  private static boolean isBlockCommentEnded(char[] line, int i) {
    return i - 2 >= 0 && line[i - 2] == '*' && line[i - 1] == '/';
  }

  private static boolean isStringBegan(char[] line, int i) {
    return i - 1 > 0 && line[i-1] != '\\' && line[i] == '"';
  }

  private static boolean isStringEnded(char[] line, int i) {
    return i - 1 > 0 && line[i-1] != '\\' && line[i] == '"';
  }

}