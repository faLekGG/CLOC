package com.goloveyko.service;

import java.io.File;

public interface MenuService {

  String printDirectoryTree(File file);

  String printFileInfo(File file);
}
