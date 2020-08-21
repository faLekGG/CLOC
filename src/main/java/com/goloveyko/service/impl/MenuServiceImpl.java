package com.goloveyko.service.impl;

import com.goloveyko.service.CodeLineCounterService;
import com.goloveyko.service.MenuService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuServiceImpl implements MenuService {

  private final CodeLineCounterService codeLineCounterService;

  public MenuServiceImpl(CodeLineCounterService codeLineCounterService) {
    this.codeLineCounterService = codeLineCounterService;
  }

  @Override
  public String printDirectoryTree(File folder) {
    if (folder.isFile()) {
      throw new IllegalArgumentException("folder is not a directory");
    }
    int indent = 0;
    StringBuilder sb = new StringBuilder();
    printDirectoryTree(folder, indent, sb);
    return sb.toString();
  }

  @Override
  public String printFileInfo(File file) {
    if (file == null || file.isDirectory()) {
      throw new IllegalArgumentException("file is not a document");
    }
    return String.format("%s: %d", file.getName(), getFileStats(file));
  }

  private int getFileStats(File file) {
    int numberOfLines = 0;
    if (file != null && file.isFile()) {
      try (BufferedReader br = Files.newBufferedReader(Paths.get(file.getPath()))) {
        numberOfLines = codeLineCounterService.countNumberOfLines(br);
      } catch (IOException e) {
        System.err.format("IOException: %s", e);
      }
    }
    return numberOfLines;
  }

  private void printDirectoryTree(File folder, int indent, StringBuilder sb) {
    if (folder == null) {
      throw new IllegalArgumentException("folder is null");
    }
    if (!folder.isDirectory()) {
      throw new IllegalArgumentException("folder is not a Directory");
    }

    Integer sum = Stream.of(folder.listFiles())
        .filter(File::isFile)
        .map(this::getFileStats)
        .collect(Collectors.toList())
        .stream()
        .reduce(0, Integer::sum);

    sb.append(getIndentString(indent));
    sb.append("+--");
    sb.append(folder.getName());
    sb.append("/");
    sb.append("(");
    sb.append(sum);
    sb.append(")");
    sb.append("\n");

    for (File file : folder.listFiles()) {
      if (file.isDirectory()) {
        printDirectoryTree(file, indent + 1, sb);
      } else {
        printFile(file, indent + 1, sb);
      }
    }
  }

  private void printFile(File file, int indent, StringBuilder sb) {
    sb.append(getIndentString(indent));
    sb.append("+--");
    sb.append(file.getName());
    sb.append(":");
    sb.append(getFileStats(file));
    sb.append("\n");
  }

  private String getIndentString(int indent) {
    StringBuilder sb = new StringBuilder();
    sb.append("|  ".repeat(Math.max(0, indent)));
    return sb.toString();
  }
}
