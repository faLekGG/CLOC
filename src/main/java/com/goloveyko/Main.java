package com.goloveyko;

import com.goloveyko.service.MenuService;
import com.goloveyko.service.impl.CodeLineCounterServiceImpl;
import com.goloveyko.service.impl.CommentServiceImpl;
import com.goloveyko.service.impl.MenuServiceImpl;

import java.io.File;

public class Main {

  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Path to file or folder is not valid. Please, check your input");
    }
    String path = args[0];
    if (path == null || path.isEmpty()) {
      throw new IllegalArgumentException("Provided path is not valid");
    }
    final File file = new File(path);
    MenuService menuService = new MenuServiceImpl(
        new CodeLineCounterServiceImpl(
            new CommentServiceImpl()
        )
    );
    if (file.isDirectory()) {
      System.out.println(menuService.printDirectoryTree(file));
    } else {
      System.out.println(menuService.printFileInfo(file));
    }
  }
}
