package com.bnnvara.kiespijn.Dilemma;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DilemmaProvider {

    private static DilemmaProvider sDilemmaProvider;
    private static List<Dilemma> mDilemmaProvider;

    public static DilemmaProvider get(Context context) {
        if (sDilemmaProvider == null) {
            sDilemmaProvider = new DilemmaProvider();
        }
        return sDilemmaProvider;
    }

    public DilemmaProvider() {
        // constructor with no arguments to use for Firebase
    }

    private DilemmaProvider(Context context) {
        mDilemmaProvider = new ArrayList<>();
    }

    public void addDilemma(Dilemma dilemma) {
        mDilemmaProvider.add(dilemma);
    }

    public static List<Dilemma> getTDilemmaProvider() {
        return mDilemmaProvider;
    }

    public void clearResults() {
        mDilemmaProvider.clear();
    }

    public void deleteDilemma(Dilemma dilemma) {
        mDilemmaProvider.remove(dilemma.getDilemmaKey());
    }

    public int getSize() {
        return mDilemmaProvider.size();
    }

    public static Dilemma getDilemma(String dilemmaKey) {

        int i = 0;
        Dilemma dilemma;

        while (i < mDilemmaProvider.size()) {

            if (mDilemmaProvider.get(i).getDilemmaKey().equals(dilemmaKey)) {
                dilemma = mDilemmaProvider.get(i);
                return dilemma;
            }

            i += 1;
        }

        return null;
    }

    public Dilemma getDilemmaFromIndex(int index) {
        return mDilemmaProvider.get(index);
    }

}
