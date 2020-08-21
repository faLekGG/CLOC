package com.goloveyko.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface CodeLineCounterService {

  int countNumberOfLines(BufferedReader source) throws IOException;
}
