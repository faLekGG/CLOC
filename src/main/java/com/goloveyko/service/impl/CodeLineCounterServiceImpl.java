package com.goloveyko.service.impl;

import com.goloveyko.service.CodeLineCounterService;
import com.goloveyko.service.CommentService;

import java.io.BufferedReader;
import java.io.IOException;

public class CodeLineCounterServiceImpl implements CodeLineCounterService {

  private final CommentService commentService;

  public CodeLineCounterServiceImpl(CommentService commentService) {
    this.commentService = commentService;
  }

  @Override
  public int countNumberOfLines(BufferedReader source) throws IOException {
    int count = 0;
    String line;

    while ((line = source.readLine()) != null) {
      line = line.trim();
      if (line.isEmpty() || line.isBlank() || commentService.doesLineHaveComment(line.toCharArray())) {
        continue;
      }
      count++;
    }

    return count;
  }
}
