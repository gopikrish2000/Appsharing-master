package com.rbricks.appsharing.concept.notesapp.interfaces;


import com.rbricks.appsharing.concept.notesapp.domains.NotesItem;

import java.util.List;

/**
 * Created by gopikrishna on 10/11/16.
 */

public interface MVPNotesInterface {

    void showProgress();

    void hideProgress();

    void showAllNotes(List<NotesItem> notesItems);

    void showAllNotes(List<NotesItem> notesItems, boolean isFromSearchFlow);

    void removeNotesOfPosition(int index);
}
