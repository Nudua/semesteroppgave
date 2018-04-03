package com.groupname.framework.history;

import com.groupname.framework.history.commands.ListAddCommand;

import java.util.ArrayList;
import java.util.List;


public class CmdMain {

    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        UndoRedo undoRedoManager = new StackBasedUndoRedo();

        undoRedoManager.execute(new ListAddCommand<>(names, "Tor1"));
        undoRedoManager.execute(new ListAddCommand<>(names, "Tor2"));
        undoRedoManager.execute(new ListAddCommand<>(names, "Tor3"));

        while (undoRedoManager.canUndo()) {
            undoRedoManager.undo();
        }


    }


}
