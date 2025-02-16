package com.alesgaroth.zuv.textrep;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alesgaroth.zuv.design.Algorithm;


class AlgoEditorTest {
    Algorithm algo;
    AlgorithmEditor editor;

  @BeforeEach
  public void before() {
    algo = new Algorithm();
    editor = new AlgorithmEditor(algo);
  }

  @Test
  public void algoEditorCanEdit() {
    editor.createNode("foo");
    editor.createNode("bar");
    editor.connectNodes("foo", "0", "bar", "0");
    editor.deleteNode("bar");
  }

  @Test
  public void editingChangesAlgorithm() {
    editor.createNode("foo");
    editor.createNode("bar");
    Set<String> names = algo.names();
    assertTrue(names.contains("foo"), "" + names);
    assertTrue(names.contains("bar"), "" + names);
  }
}
